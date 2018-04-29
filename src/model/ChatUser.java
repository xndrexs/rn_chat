package model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	
	private boolean online;
	
	public ChatUser(UUID id, Socket socket, BufferedReader messageReader, int port) {
		this.id = id;
		this.socket = socket;
		this.messageReader = messageReader;
		this.online = true;
		this.port = port;
		
		try {
			messageWriter = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendMessage(String message) {
		messageWriter.println(message);
	}
	
	public BufferedReader getMessageReader() {
		return messageReader;
	}
	
	public UUID getID() {
		return id;
	}
	
	public boolean isOnline() {
		return online;
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

}
