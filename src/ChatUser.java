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
	
	public ChatUser(UUID id, Socket socket, BufferedReader messageReader) {
		this.id = id;
		this.socket = socket;
		this.messageReader = messageReader;
		
		try {
			messageWriter = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void sendMessage(String message) {
		messageWriter.println(message);
	}
	
	public BufferedReader getMessageReader() {
		return messageReader;
	}

}
