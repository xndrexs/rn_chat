package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import helper.MessageHandler;
import helper.MessageType;
import helper.SenderType;

public class ChatServer extends ChatBase {

	public final static int SERVER_PORT = 55555;
	private int port;
	private ServerSocket server;
	private MessageHandler messageHandler;
	private Map<String, String> users;

	public ChatServer() {
		super(SenderType.Server);
		this.users = new HashMap<String, String>();
		this.port = SERVER_PORT;
		this.messageHandler = new MessageHandler(id, port, "localhost");
	}

	/**
	 * Startet den Server und wartet auf eingehende Verbindungen
	 */
	public void start() {
		printer.printMessage("Starting Server ... ");
		try {
			server = new ServerSocket(port);
			printer.printMessage("Server started on Port: " + server.getLocalPort());
			waitForConnections();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Startet einen neuen Thread, in welchem auf eigehende Verbindungen gewartet
	 * wird
	 */
	private void waitForConnections() {
		Thread waitForConnectionsThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						// Hier wartet der Server auf eine eingehende Verbindung
						Socket clientSocket = server.accept();
						printer.printMessage("Connection incoming...");

						// Neuen Reader f�r den verbundenen Client schicken (zum Empfangen von
						// Nachrichten)
						BufferedReader clientMessageReader = new BufferedReader(
								new InputStreamReader(clientSocket.getInputStream()));

						// Der Client schickt als erste Nachricht seine ID und Port
						ChatMessage chatMessage = messageHandler.deserializeMessage(clientMessageReader.readLine());
						if (chatMessage == null) {
							break;
						}
						UUID clientId = chatMessage.getUUID();
						int port = chatMessage.getPort();
						String address = chatMessage.getAddress();

						// Neuen User aus den Daten erstellen
						ChatUser chatUser = new ChatUser(clientId, clientSocket, clientMessageReader, port,
								clientSocket.getInetAddress().toString());

						clients.put(clientId.toString(), chatUser);

						printer.printMessage("Client connected: " + clientId.toString() + " (from: "
								+ clientSocket.getInetAddress().toString() + ")");

						// Starte neuen Thread f�r diesen User (Zum Senden und Empfangen von
						// Nachrichten)
						waitForMessages(chatUser);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		waitForConnectionsThread.start();
		printer.printMessage("Waiting for Connections ...");
	}

	/**
	 * Nimmt Nachrichten entgegen in einem eigenen Thread
	 * 
	 * @param chatUser
	 */
	private void waitForMessages(ChatUser chatUser) {
		Thread messageReceiverThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (chatUser.isOnline()) {
					try {
						String jsonMessage = chatUser.getMessageReader().readLine();
						if (jsonMessage != null) {
							if (jsonMessage.equals("ABMELDEN")) {
								break;
							}
							ChatMessage chatMessage = messageHandler.deserializeMessage(jsonMessage);
							printer.printMessage("Message received (" + chatUser.getID() + ")" + ": " + chatMessage.getMessage());
							// LOGIN
							if (chatMessage.getMessage().equals(MessageType.Login.getType())) {
								checkLogin(chatMessage);
							}
							// REGISTER
							if (chatMessage.getMessage().equals(MessageType.Register.getType())) {
								registerNewUser(chatMessage);
							}

						} else {
							chatUser.disconnect();
							clients.remove(chatUser.getID().toString());
							printer.printMessage("Client disconnected: " + chatUser.getID() + " ... Bye Bye!");
							updateClients(chatUser, MessageType.Disconnect);
						}
					} catch (IOException e1) {
						chatUser.disconnect();
						clients.remove(chatUser.getID().toString());
						printer.printMessage("Client disconnected: " + chatUser.getID() + " ... Bye Bye!");
						updateClients(chatUser, MessageType.Disconnect);
					} finally {

					}
				}
			}
		});
		messageReceiverThread.start();
		printer.printMessage("Waiting for Messages from " + chatUser.getID() + " ...");
	}

	private void updateClients(ChatUser chatUser, MessageType type) {
		for (ChatUser currentUser : clients.values()) {
			if (!currentUser.getID().equals(chatUser.getID())) {
				currentUser.update(MessageHandler.serializeMessageForUpdateClient(chatUser, type));
				chatUser.update(MessageHandler.serializeMessageForUpdateClient(currentUser, type));
			}
		}
	}

	private void checkLogin(ChatMessage chatMessage) {
		String id = chatMessage.getUUID().toString();
		ChatUser user = getClients().get(id);
		String username = chatMessage.getUsername();
		String password = chatMessage.getPassword();
		if(users.containsKey(username)) {
			if(users.get(username).equals(password)) {
				user.setUsername(username);
				user.setPassword(password);
				sendLoginResponse(user, "Success");
				updateClients(user, MessageType.Connect);
			} else {
				sendLoginResponse(user, "Failed");
			}
		} else {
			sendLoginResponse(user, "Failed");
		}
	}
	
	private void sendLoginResponse(ChatUser user, String result) {
		String message = messageHandler.serializeMessageforLoginResponse(user.getUsername(), result);
		user.getMessageWriter().println(message);
	}

	private void registerNewUser(ChatMessage chatMessage) {
		String id = chatMessage.getUUID().toString();
		ChatUser user = getClients().get(id);
		String username = chatMessage.getUsername();
		String password = chatMessage.getPassword();
		Boolean exists = false;
		Iterator<Entry<String, ChatUser>> it = getClients().entrySet().iterator();
		while(it.hasNext()) {
			ChatUser nextUser = it.next().getValue();
			if(username.equals(nextUser.getUsername())) {
				exists = true;
			}
		}
		if (!exists) {
			users.put(username, password);
			user.setUsername(username);
			user.setPassword(password);
			sendLoginResponse(user, "Success");
			updateClients(user, MessageType.Connect);
		} else {
			sendLoginResponse(user, "Failed");
		}
	}
}
