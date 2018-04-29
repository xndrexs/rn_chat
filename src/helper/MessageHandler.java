package helper;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import model.ChatMessage;
import model.ChatUser;

public class MessageHandler {
	
	UUID id;
	int port;
	
	public MessageHandler(UUID id, int port) {
		this.id = id;
		this.port = port;
	}

	public ChatMessage deserializeMessage(String jsonMessage) {
		ChatMessage chatMessage = new ChatMessage();
		try {
			JSONObject json = new JSONObject(jsonMessage);
			UUID clientId = UUID.fromString(json.getString("id"));
			String message = json.getString("message");
			int port = json.getInt("port");
			chatMessage.fillMessage(clientId, port, message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return chatMessage;
	}
	
	/**
	 * Deserialisiert eine JSON-Nachricht in ID des Senders und der Nachricht
	 * 
	 * @param jsonMessage
	 *            Übergabe des JSON als String
	 * @return Gibt ein ChatMessage Objekt mit ID des Senders als UUID und der
	 *         Nachricht als String zurück
	 */
	public String serializeMessage(String message) {
		JSONObject json = new JSONObject();
		try {
			json.put("id", id);
			json.put("port", port);
			json.put("message", message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
	public static String serializeMessageForUpdateClient(ChatUser chatUser) {
		
		JSONObject json = new JSONObject();
		try {
			json.put("id", chatUser.getID());
			json.put("port",chatUser.getPort());
			json.put("message", "User connected");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
}
