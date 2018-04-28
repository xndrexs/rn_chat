package controller;

import model.ChatClient;
import model.ChatServer;
import view.ChatMainWindow;

public class ChatController {
	
	ChatMainWindow main;
	ChatInputController input;
	ChatClient client;
	
	
	public ChatController(ChatMainWindow main) {
		
		int port = ChatServer.SERVER_PORT;
		this.main = main;
		this.client = new ChatClient("localhost", port);
		this.input = new ChatInputController(main.getChatInputPane(), client);
		
	}
}
