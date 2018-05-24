package controller;

import java.io.IOException;

import helper.MessageFormatter;
import model.ChatClient;
import model.ChatMessage;
import model.ChatServer;
import view.ChatMainWindow;

public class ChatController {
	
	private ChatMainWindow main;
	private ChatMessageController messageController;
	private ChatClient client;
	private ChatUserController userController;
	
	public ChatController(ChatMainWindow main) {
		
		int port = ChatServer.SERVER_PORT;
		this.main = main;
		
		try {
			this.client = new ChatClient("172.26.35.117", port, this);
			this.messageController = new ChatMessageController(main.getChatMessageWindow(), client);		
			this.userController = new ChatUserController(main.getChatUserPane(), client);
			this.userController.setOutputController(messageController);
			setupInfoPane();
		} catch (IOException e) {
			System.out.println("Unknown Host ...");
			logMessage("Unknown Host ... ");
			e.printStackTrace();
		}
	}
	
	private void setupInfoPane() {
		main.getChatInfoPane().addInfo("ClientID: " + client.getId().toString());
		main.getChatInfoPane().addInfo("ClientAddress: " + client.getLocalAddress().toString());
		main.getChatInfoPane().addInfo("ServerAddress: " + client.getServerAddress().toString());
	}
	
	public void notifyMessage(ChatMessage message) {
		messageController.handleMessage(message);
	}
	
	public void logMessage(String message) {
		String modifiedMessage = MessageFormatter.getPrint(message);
		main.getChatLog().displayLogMessage(modifiedMessage);
	}
}
