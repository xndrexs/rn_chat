package model;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class ChatBase {
	
	protected PrintWriter messageWriter;
	protected BufferedReader messageReader;
	protected MessageFormatter printer;	
	protected UUID id;
	
	public ChatBase(SenderType type) {
		id = UUID.randomUUID();
		printer = new MessageFormatter(id, type);
	}

}
