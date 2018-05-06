package controller;

import model.ChatClient;
import model.ChatMessage;
import model.ChatServer;
import view.ChatMainWindow;

public class ChatController {
	
	private ChatMainWindow main;
	private ChatInputController input;
	private ChatOutputController output;
	private ChatClient client;
	private ChatUserController userController;
	
	
	public ChatController(ChatMainWindow main) {
		
		int port = ChatServer.SERVER_PORT;
		this.main = main;
		
		this.client = new ChatClient("localhost", port);
		this.client.setController(this);
		
		this.output = new ChatOutputController(main.getChatMessageWindow());
		this.input = new ChatInputController(main.getChatInputPane(), client);
		this.input.setOutputController(output);
		
		this.userController = new ChatUserController(main.getChatUserPane(), client.getClients());
		this.userController.setOutputController(output);
		
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
