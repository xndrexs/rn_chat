package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import org.json.*;

import controller.ChatController;
import helper.MessageHandler;
import helper.PortFinder;
import helper.SenderType;

public class ChatClient extends ChatBase {
		
	private String userName;
	private String userPassword;
	private Socket socket;	
	private Scanner read;
	private String adress;
	private ChatController controller;
	private MessageHandler messageHandler;
	private int serverPort;
	private int clientPort;

	public ChatClient(String adress, int serverPort) {
		super(SenderType.Client);
		
		read = new Scanner(System.in);
		clientPort = PortFinder.findFreePort();
		messageHandler = new MessageHandler(id, clientPort);

		this.serverPort = serverPort;
		this.adress = adress;
		
		printer.printMessage("Starting Client ... ");
		printer.printMessage("Connecting to Server ... ");
		
		connectToServer();
		startMessageReceiveThread();
		//startMessageThread();
	}
	
	// Noch kein Thread... Aktuell für TCP an Server
	private void startMessageThread() {
		while(true) {
			printer.printMessage("Send message: ");
			String message = read.nextLine();
			sendMessage(message);
			if (message.equals("Bye")) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
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
	 * Sendet eine Nachricht an den verbundenen Socket
	 * @param message
	 */
	public void sendMessage(String message) {
		
		String json = messageHandler.serializeMessage(message);
		
		// printer.printMessage("Message to send: " + json);
	
		messageWriter.println(json);
		messageWriter.flush();
	}
	
	/**
	 * Startet einen Thread für Nachrichten, welche über UDP an den Client geschickt werden
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
}
