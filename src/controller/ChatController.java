package controller;

import model.ChatClient;
import model.ChatMessage;
import model.ChatServer;
import view.ChatMainWindow;

public class ChatController {
	
	ChatMainWindow main;
	ChatInputController input;
	ChatOutputController output;
	ChatClient client;
	ChatUserController userController;
	
	
	public ChatController(ChatMainWindow main) {
		
		int port = ChatServer.SERVER_PORT;
		this.main = main;
		this.client = new ChatClient("localhost", port);
		client.setController(this);
		this.input = new ChatInputController(main.getChatInputPane(), client);
		this.output = new ChatOutputController(main.getChatMessageWindow());
		this.userController = new ChatUserController(main.getChatUserPane(), client.getClients());
		
		setupInfoPane();
	}
	
	private void setupInfoPane() {
		main.getChatInfoPane().addInfo("ClientID: " + client.getId().toString());
		main.getChatInfoPane().addInfo("ClientAddress: " + client.getLocalAddress().toString());
		main.getChatInfoPane().addInfo("ServerAddress: " + client.getServerAddress().toString());
	}
	
	public void notifyMessage(ChatMessage message) {
		output.displayMessage(message);
	}
}
