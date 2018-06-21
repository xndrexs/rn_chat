package model;
import java.util.UUID;

public class ChatMessage {

	private UUID id;
	private int port;
	private String address;
	private String message;
	private String username;
	private String password;
	
	public ChatMessage(UUID id, String message, int port, String address) {
		this.port = port;
		this.id = id;
		this.message = message;
		this.address = address;
	}
	
	public ChatMessage() {
		
	}
	
	public void setLoginData(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public UUID getUUID() { return id; };
	public String getMessage() { return message; };
	public int getPort() { return port; };
	public String getAddress() { return address; };
	public String getUsername() { return username; };
	public String getPassword() { return password; };
	
	public void fillMessage(UUID id, int port, String message, String address) {
		this.id = id;
		this.port = port;
		this.message = message;
		this.address = address;
	}
}
