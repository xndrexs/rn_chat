package model;
import java.util.UUID;

public class ChatMessage {

	private UUID id;
	private String message;
	
	public ChatMessage(UUID id, String message) {
		this.id = id;
		this.message = message;
	}
	
	public ChatMessage() {
		
	}
	
	public UUID getUUID() { return id; };
	public String getMessage() { return message; };
	
	public void fillMessage(UUID id, String message) {
		this.id = id;
		this.message = message;
	}
}
