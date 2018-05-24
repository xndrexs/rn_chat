package model;
import java.util.UUID;

public class ChatMessage {

	private UUID id;
	private int port;
	private String address;
	private String message;
	
	public ChatMessage(UUID id, String message, int port, String address) {
		this.port = port;
		this.id = id;
		this.message = message;
		this.address = address;
	}
	
	public ChatMessage() {
		
	}
	
	public UUID getUUID() { return id; };
	public String getMessage() { return message; };
	public int getPort() { return port; };
	public String getAddress() { return address; };
	
	public void fillMessage(UUID id, int port, String message, String address) {
		this.id = id;
		this.port = port;
		this.message = message;
		this.address = address;
	}
}
