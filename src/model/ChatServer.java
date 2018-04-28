package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import helper.PortFinder;
import helper.SenderType;

public class ChatServer extends ChatBase {

	public final static int SERVER_PORT = PortFinder.findFreePort();
	private int port;
	private ServerSocket server;

	private Map<UUID, ChatUser> clients;

	public ChatServer() {
		super(SenderType.Server);
		clients = new HashMap<UUID, ChatUser>();
		this.port = SERVER_PORT;
	}

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

						// Neuen Reader für den verbundenen Client schicken (zum Empfangen von
						// Nachrichten)
						BufferedReader clientMessageReader = new BufferedReader(
								new InputStreamReader(clientSocket.getInputStream()));

						// Der Client schickt als erste Nachricht seine ID
						UUID clientId = UUID.fromString(clientMessageReader.readLine());

						// Neuen User aus den Daten erstellen
						ChatUser chatUser = new ChatUser(clientId, clientSocket, clientMessageReader);

						clients.put(clientId, chatUser);
						printer.printMessage("Client connected: " + clientId.toString() + " (from: "
								+ clientSocket.getRemoteSocketAddress() + ")");

						// Starte neuen Thread für diesen User (Zum Senden und Empfangen von
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
	 * @param chatUser
	 */
	private void waitForMessages(ChatUser chatUser) {
		Thread messageReceiverThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (chatUser.isOnline()) {
					try {
						String jsonMessage = chatUser.getMessageReader().readLine();
						ChatMessage chatMessage = deserializeMessage(jsonMessage);
						if (chatMessage != null) {
							if (!chatMessage.getMessage().equals("Bye")) {
								printer.printMessage("Message received (" + chatUser.getID() + ")" + ": "
										+ chatMessage.getMessage());
							} else {
								printer.printMessage("Client disconnected: " + chatUser.getID() + " ... Bye Bye!");
								chatUser.goOffline();
							}
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		messageReceiverThread.start();
		printer.printMessage("Waiting for Messages ... ");
	}

	/**
	 * Deserialisiert eine JSON-Nachricht in ID des Senders und der Nachricht
	 * 
	 * @param jsonMessage
	 *            Übergabe des JSON als String
	 * @return Gibt ein ChatMessage Objekt mit ID des Senders als UUID und der
	 *         Nachricht als String zurück
	 */
	private ChatMessage deserializeMessage(String jsonMessage) {
		JSONObject json = null;
		UUID clientId = null;
		String message = null;
		ChatMessage chatMessage = new ChatMessage();
		try {
			json = new JSONObject(jsonMessage);
			clientId = UUID.fromString(json.getString("id"));
			message = json.getString("message");
			chatMessage.fillMessage(clientId, message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return chatMessage;
	}

}
