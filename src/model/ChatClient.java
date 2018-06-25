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
import helper.StageManager;
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
	private StageManager manager;
	
	public ChatClient(String adress, int serverPort, StageManager manager) throws IOException {
		super(SenderType.Client);
		
		this.manager = manager;

		clientPort = PortFinder.findFreePort();
		messageHandler = new MessageHandler(id, clientPort, adress);

		// this.controller = controller;
		this.serverPort = serverPort;
		this.adress = adress;

		//controller.logMessage("Starting Client ... ");
		//controller.logMessage("Connecting to Server ... ");

		connectToServer();
		startMessageReceiveThreadForTCP();
		startMessageReceiveThreadUDP();
	}

	/**
	 * Verbindet sich mit dem Server
	 */
	private void connectToServer() throws IOException {
		socket = new Socket(adress, serverPort);
		// controller.logMessage("Connected to: " + adress + "/" + serverPort);
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
	
	public void sendMessageForLogin(String username, String password, MessageType type) {
		String json = messageHandler.serializeMessageforLogin(username, password, type);
		messageWriter.println(json);
		messageWriter.flush();
	}

	public void sendUDPMessage(ChatUser user, String message) {
		UDPSender sender = new UDPSender(user.getAddress(), user.getPort());
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
						if (jsonMessage != null) {
							ChatMessage chatMessage = messageHandler.deserializeMessage(jsonMessage);
							UUID id = chatMessage.getUUID();
							if (chatMessage.getMessage().equals(MessageType.Connect.getType())) {
								controller.logMessage("User connected: " + id + " from " + chatMessage.getAddress());
								ChatUser newUser = new ChatUser(id, chatMessage.getUsername(), chatMessage.getPort(), chatMessage.getAddress());
								newUser.setUsername(chatMessage.getUsername());
								clients.put(id.toString(), newUser);
							}
							if (chatMessage.getMessage().equals(MessageType.Disconnect.getType())) {
								controller.logMessage("User disconnected: " + id);
								clients.remove(id.toString());
							}
							if (chatMessage.getMessage().equals("Success")) {
								System.out.println("SUCCESS");
								userName = chatMessage.getUsername();
								manager.startChatProcess();
							}
							if (chatMessage.getMessage().equals("Failed")) {
								System.out.println("FAILED");
							}
						}
					} catch (IOException e) {
						System.out.println("Error starting TCP Thread");
						e.printStackTrace();
						break;
					}
				}
			}
		});
		messageReceiveThreadForUDP.start();
		// controller.logMessage("Message Receiver started for TCP ... ");
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
		//controller.logMessage("Message Receiver started for UDP ... ");
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
	
	public String getUsername() {
		return userName;
	}
}
