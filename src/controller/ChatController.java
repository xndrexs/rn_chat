package controller;

import model.ChatClient;
import model.ChatServer;
import view.ChatMainWindow;

public class ChatController {
	
	ChatMainWindow main;
	ChatInputController input;
	ChatClient client;
	ChatServer server;
	
	
	public ChatController(ChatMainWindow main) {
		
		this.main = main;
		
		int port = 12345;
		this.server = new ChatServer(port);
		this.client = new ChatClient("localhost", port);
		
		input = new ChatInputController(main.getChatInputPane(), client);
		
	}
}
