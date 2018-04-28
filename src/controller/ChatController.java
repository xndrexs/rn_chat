package controller;

import model.ChatClient;
import view.ChatMainWindow;

public class ChatController {
	
	ChatMainWindow main;
	ChatClient client;
	
	public ChatController() {
		
		this.main = new ChatMainWindow();
		this.client = new ChatClient("localhost", 12345);
		
	}
	
	public void startChatProgramm(String args []) {
		main.launch(args);
	}

}
