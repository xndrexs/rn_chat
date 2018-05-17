package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.UUID;
import controller.ChatController;
import helper.MessageHandler;
import helper.MessageType;
import helper.PortFinder;
import helper.SenderType;
import helper.UDPSender;

public class ChatClient extends ChatBase {

	private String userName;
	private String userPassword;
	private Socket socket;
	private String adress;
	private ChatController controller;
	private MessageHandler messageHandler;
	private int serverPort;
	private int clientPort;

	public ChatClient(String adress, int serverPort, ChatController controller) throws IOException {
		super(SenderType.Client);

		clientPort = PortFinder.findFreePort();
		messageHandler = new MessageHandler(id, clientPort, adress);

		this.controller = controller;
		this.serverPort = serverPort;
		this.adress = adress;

		controller.logMessage("Starting Client ... ");
		controller.logMessage("Connecting to Server ... ");

		connectToServer();
		startMessageReceiveThreadForTCP();
		startMessageReceiveThreadUDP();
	}

	/**
	 * Verbindet sich mit dem Server
	 */
	private void connectToServer() throws IOException {
		socket = new Socket(adress, serverPort);
		controller.logMessage("Connected to: " + adress + "/" + serverPort);
		messageWriter = new PrintWriter(socket.getOutputStream(), true);
		messageReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// Schicke ID und Port an Server
		messageWriter.println(messageHandler.serializeMessage("Hello"));
	}

	/**
	 * Sendet eine Nachricht an den verbundenen Socket (TCP)
	 * 
	 * @param message
	 */
	public void sendMessage(String message) {
		String json = messageHandler.serializeMessage(message);
		messageWriter.println(json);
		messageWriter.flush();
	}

	public void sendUDPMessage(ChatUser user, String message) {
		UDPSender sender = new UDPSender("127.0.0.1", user.getPort());
		sender.sendMessage(message);
	}

	/**
	 * Startet einen Thread (TCP) für Nachrichten vom Server
	 */
	private void startMessageReceiveThreadForTCP() {
		Thread messageReceiveThreadForUDP = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					String jsonMessage;
					try {
						jsonMessage = messageReader.readLine();
						ChatMessage chatMessage = messageHandler.deserializeMessage(jsonMessage);
						UUID id = chatMessage.getUUID();
						if (chatMessage.getMessage().equals(MessageType.Connect.getType())) {
							controller.logMessage("User connected: " + id);
							ChatUser newUser = new ChatUser(id, chatMessage.getPort(), chatMessage.getAddress());
							clients.put(id.toString(), newUser);
						}
						if (chatMessage.getMessage().equals(MessageType.Disconnect.getType())) {
							controller.logMessage("User disconnected: " + id);
							clients.remove(id.toString());
						}
					} catch (IOException e) {
						System.out.println("Error starting TCP Thread");
						e.printStackTrace();
					}
				}
			}
		});
		messageReceiveThreadForUDP.start();
		controller.logMessage("Message Receiver started for TCP ... ");
	}

	/**
	 * Startet einen Thread für Nachrichten, welche über UDP an den Client geschickt
	 * werden Muss noch umgebaut werden, weil für jede Nachricht ein Socket erstellt
	 * wird
	 */
	private void startMessageReceiveThreadUDP() {

		Thread messageReceiverThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {

					DatagramSocket clientSocket = null;
					try {
						clientSocket = new DatagramSocket(clientPort);
					} catch (SocketException e) {
						e.printStackTrace();
					}

					byte[] receiveData = new byte[1024];
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

					try {
						clientSocket.receive(receivePacket);
					} catch (IOException e) {
						e.printStackTrace();
					}
					int remotePort = clientSocket.getPort();
					String remoteAddress = clientSocket.getLocalAddress().toString();
					String message = new String(receivePacket.getData());
					ChatMessage chatMessage = messageHandler.deserializeMessage(message);

					printer.printMessage(
							"Message received (" + remoteAddress + ":" + remotePort + ": " + chatMessage.getMessage());
					controller.notifyMessage(chatMessage);
					clientSocket.close();
				}
			}
		});
		messageReceiverThread.start();
		controller.logMessage("Message Receiver started for UDP ... ");
	}

	public SocketAddress getLocalAddress() {
		return socket.getLocalSocketAddress();
	}

	public SocketAddress getServerAddress() {
		return socket.getRemoteSocketAddress();
	}

	public void setController(ChatController controller) {
		this.controller = controller;
	}
}
