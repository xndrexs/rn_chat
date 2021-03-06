package model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class ChatUser {
	
	private String userName;
	private String userPassword;
	private UUID id;
	
	private Socket socket;
	private PrintWriter messageWriter;
	private BufferedReader messageReader;
	private int port;
	private String address;
	
	private boolean online;
	
	public ChatUser(UUID id, Socket socket, BufferedReader messageReader, int port, String address) {
		this.id = id;
		this.socket = socket;
		this.messageReader = messageReader;
		this.online = true;
		this.port = port;
		this.address = address;
		
		try {
			messageWriter = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ChatUser(UUID id, String username, int port, String address) {
		this.id = id;
		this.userName = username;
		this.port = port;
		this.address = address;
	}
	
	public void sendMessage(String message) {
		messageWriter.println(message);
	}
	
	public BufferedReader getMessageReader() {
		return messageReader;
	}
	
	public PrintWriter getMessageWriter() {
		return messageWriter;
	}
	
	public UUID getID() {
		return id;
	}
	
	public boolean isOnline() {
		return online;
	}
	
	public String getUsername() {
		return userName;
	}
	
	public String getPassword() {
		return userPassword;
	}
	
	public void goOffline() {
		online = false;
	}
	
	public void update(String jsonMessage) {
		messageWriter.println(jsonMessage);
	}
	
	public int getPort() {
		return port;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void disconnect() {
		try {
			socket.close();
			online = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setUsername(String username) {
		this.userName = username;
	}

	public void setPassword(String password) {
		this.userPassword = password;
	}

}
