package model;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import helper.MessageFormatter;
import helper.SenderType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class ChatBase {
	
	protected PrintWriter messageWriter;
	protected BufferedReader messageReader;
	protected MessageFormatter printer;
	protected UUID id;
	protected ObservableMap<String, ChatUser> clients;
	
	public ChatBase(SenderType type) {
		id = UUID.randomUUID();
		printer = new MessageFormatter(id, type);
		clients = FXCollections.observableMap(new HashMap<String, ChatUser>());
		clients.put(UUID.randomUUID().toString(), null);
	}
	
	public BufferedReader getMessageReader() {
		return messageReader;
	}
	
	public UUID getId() {
		return id;
	}
	
	public ObservableMap<String, ChatUser> getClients() {
		return clients;
	}

}
