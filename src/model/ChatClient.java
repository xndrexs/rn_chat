package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import org.json.*;

public class ChatClient extends ChatBase {
		
	private String userName;
	private String userPassword;
	private Socket socket;	
	private Scanner read;
	private String adress;
	private int port;

	public ChatClient(String adress, int port) {
		super(SenderType.Client);
		
		read = new Scanner(System.in);

		this.port = port;
		this.adress = adress;
		
		printer.printMessage("Starting Client ... ");
		printer.printMessage("Connecting to Server ... ");
		
		connectToServer();
		startMessageReceiveThread();
		//startMessageThread();
	}
	
	// Noch kein Thread...
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

	private void connectToServer() {
		try {
			socket = new Socket (adress, port);
			printer.printMessage("Connected to: " + adress + "/" + port);
			
			messageWriter = new PrintWriter(socket.getOutputStream(), true);
			messageReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			// Schicke ID an Server
			messageWriter.println(id.toString());
			
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
		
		String json = serializeMessage(message);
		
		// printer.printMessage("Message to send: " + json);
	
		messageWriter.println(json);
		messageWriter.flush();
	}
	
	private String serializeMessage(String message) {
		JSONObject json = new JSONObject();
		try {
			json.put("id", id);
			json.put("message", message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
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
						clientSocket = new DatagramSocket(7777);
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
					
					printer.printMessage("Message received (" + remoteAddress + ":" + remotePort + ": " + message);
					clientSocket.close();
				}
			}
		});
		messageReceiverThread.start();
		printer.printMessage("Message Receiver started for UDP ... ");
	}
	
	private void startUDPConnection(String ipAddress, int port) {
		DatagramSocket udpSocket;
		InetAddress remoteAddress;
		

		try {
			remoteAddress = InetAddress.getByName(ipAddress);
			udpSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		byte[] sendData;
		byte[] receiveData = new byte[1024];
		
		String message = read.nextLine();
		sendData = message.getBytes();
		
		
	}
}
