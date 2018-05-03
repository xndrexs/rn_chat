package model;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import helper.MessageFormatter;
import helper.MessageHandler;
import helper.SenderType;

public class ChatBase {
	
	protected PrintWriter messageWriter;
	protected BufferedReader messageReader;
	protected MessageFormatter printer;
	protected UUID id;
	protected Map<UUID, ChatUser> clients;
	
	public ChatBase(SenderType type) {
		id = UUID.randomUUID();
		printer = new MessageFormatter(id, type);
		clients = new HashMap<UUID, ChatUser>();
	}
	
	public BufferedReader getMessageReader() {
		return messageReader;
	}
	
	public UUID getId() {
		return id;
	}
	
	public Map<UUID, ChatUser> getClients() {
		return clients;
	}

}
