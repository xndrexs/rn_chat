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
	private ChatUser activeUser;
	private int serverPort;
	private int clientPort;

	public ChatClient(String adress, int serverPort) {
		super(SenderType.Client);
		
		clientPort = PortFinder.findFreePort();
		messageHandler = new MessageHandler(id, clientPort);

		this.serverPort = serverPort;
		this.adress = adress;
		
		printer.printMessage("Starting Client ... ");
		printer.printMessage("Connecting to Server ... ");
		
		connectToServer();
		startMessageReceiveThread();
		startMessageReceiveThreadForTCP();
	}

	/**
	 * Verbindet sich mit dem Server
	 */
	private void connectToServer() {
		try {
			socket = new Socket (adress, serverPort);
			printer.printMessage("Connected to: " + adress + "/" + serverPort);
			
			messageWriter = new PrintWriter(socket.getOutputStream(), true);
			messageReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			// Schicke ID und Port an Server
			messageWriter.println(messageHandler.serializeMessage("Hello"));
			
		} catch (UnknownHostException e) {
			System.out.println("Unknown Host ...");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sendet eine Nachricht an den verbundenen Socket (TCP)
	 * @param message
	 */
	public void sendMessage(String message) {
		String json = messageHandler.serializeMessage(message);	
		messageWriter.println(json);
		messageWriter.flush();
	}
	
	public void sendUDPMessage(ChatUser user, String message) {
		System.out.println(user.getPort() + user.getID().toString());
		UDPSender sender = new UDPSender("127.0.0.1", user.getPort());
		sender.sendMessage(message);
	}
	
	/**
	 * Startet einen Thread (TCP) für Nachrichten vom Server (aktuell nur, wenn ein User online/offline geht)
	 */
	private void startMessageReceiveThreadForTCP() {
		Thread messageReceiveThreadForUDP = new Thread(new Runnable() {

			@Override
			public void run() {
				while(true) {
					String jsonMessage;
					try {
						jsonMessage = messageReader.readLine();
						ChatMessage chatMessage = messageHandler.deserializeMessage(jsonMessage);
						UUID id = chatMessage.getUUID();
						if (chatMessage.getMessage().equals(MessageType.Connect.getType())) {
							printer.printMessage("User connected: " + id);
							ChatUser newUser = new ChatUser(id, chatMessage.getPort());
							clients.put(id.toString(), newUser);
						}
						if (chatMessage.getMessage().equals(MessageType.Disconnect.getType())) {
							printer.printMessage("User disconnected: " + id);
							clients.remove(id.toString());
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		messageReceiveThreadForUDP.start();
	}
	
	/**
	 * Startet einen Thread f�r Nachrichten, welche �ber UDP an den Client geschickt werden
	 * Muss noch umgebaut werden, weil für jede Nachricht ein Socket erstellt wird
	 */
	private void startMessageReceiveThread() {
		
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
					
					printer.printMessage("Message received (" + remoteAddress + ":" + remotePort + ": " + chatMessage.getMessage());
					controller.notifyMessage(chatMessage);
					clientSocket.close();
				}
			}
		});
		messageReceiverThread.start();
		printer.printMessage("Message Receiver started for UDP ... ");
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
	
	public ChatUser getActiveChatUser() {
		return activeUser;
	}
}
