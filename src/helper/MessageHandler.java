package helper;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import model.ChatMessage;
import model.ChatUser;

public class MessageHandler {
	
	UUID id;
	int port;
	String address;
	
	public MessageHandler(UUID id, int port, String address) {
		this.id = id;
		this.address = address;
		this.port = port;
	}

	public ChatMessage deserializeMessage(String jsonMessage) {
		ChatMessage chatMessage = new ChatMessage();
		try {
			JSONObject json = new JSONObject(jsonMessage);
			UUID clientId = UUID.fromString(json.getString("id"));
			String message = json.getString("message");
			int port = json.getInt("port");
			String address = json.getString("address");
			chatMessage.fillMessage(clientId, port, message, address);
			
			if (chatMessage.getMessage().equals(MessageType.Connect.getType())) {
				chatMessage.setLoginData(json.getString("username"), json.getString("password"));
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return chatMessage;
	}
	
	/**
	 * Deserialisiert eine JSON-Nachricht in ID des Senders und der Nachricht
	 * 
	 * @param jsonMessage
	 *            �bergabe des JSON als String
	 * @return Gibt ein ChatMessage Objekt mit ID des Senders als UUID und der
	 *         Nachricht als String zur�ck
	 */
	public String serializeMessage(String message) {
		JSONObject json = new JSONObject();
		try {
			json.put("id", id);
			json.put("port", port);
			json.put("address", address);
			json.put("message", message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
	public String serializeMessageForLogin(String username, String password) {
		JSONObject json = new JSONObject();
		try {
			json.put("id", id);
			json.put("port", port);
			json.put("address", address);
			json.put("username", username);
			json.put("password", password);
			json.put("message", MessageType.Connect);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
	public static String serializeMessageForUpdateClient(ChatUser chatUser, MessageType type) {
		
		JSONObject json = new JSONObject();
		try {
			json.put("id", chatUser.getID());
			json.put("port",chatUser.getPort());
			json.put("address", chatUser.getAddress());
			json.put("message", type.getType());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}	
}
