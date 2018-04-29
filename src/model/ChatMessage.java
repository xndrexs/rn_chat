package model;
import java.util.UUID;

public class ChatMessage {

	private UUID id;
	private int port;
	private String message;
	
	public ChatMessage(UUID id, String message, int port) {
		this.port = port;
		this.id = id;
		this.message = message;
	}
	
	public ChatMessage() {
		
	}
	
	public UUID getUUID() { return id; };
	public String getMessage() { return message; };
	public int getPort() { return port; };
	
	public void fillMessage(UUID id, int port, String message) {
		this.id = id;
		this.port = port;
		this.message = message;
	}
}
