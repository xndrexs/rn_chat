
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import org.json.*;

public class ChatClient extends ChatBase {
		
	private String userName;
	private String userPassword;
	private Socket socket;
	
	private String adress;
	private int port;

	public ChatClient(String adress, int port) {
		super(SenderType.Client);
		printer.printMessage("Starting Client ... ");
		
		this.port = port;
		this.adress = adress;
		
		printer.printMessage("Connecting to Server ... ");
		connectToServer();
		startMessageThread();
	}
	
	// Noch kein Thread...
	private void startMessageThread() {
		while(true) {
			printer.printMessage("Send message: ");
			Scanner read = new Scanner(System.in);
			sendMessage(read.nextLine());
		}
	}

	private void connectToServer() {
		try {
			socket = new Socket (adress, port);
			printer.printMessage("Connected to: " + adress + "/" + port);
			
			messageWriter = new PrintWriter(socket.getOutputStream(), true);
			messageReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			// Schicke ID an Server
			messageWriter.println(id.toString());
			
		} catch (UnknownHostException e) {
			System.out.println("Unknown Host ...");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sendMessage(String message) {
		
		String json = serializeMessage(message);
		
		printer.printMessage("Message to send: " + json);
	
		messageWriter.println(json);
		messageWriter.flush();
	}
	
	private String serializeMessage(String message) {
		JSONObject json = new JSONObject();
		try {
			json.put("id", id);
			json.put("message", message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
}
