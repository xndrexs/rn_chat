package controller;
import helper.MessageFormatter;
import model.ChatClient;
import model.ChatMessage;
import view.ChatMainWindow;

public class ChatController {
	
	private ChatMainWindow main;
	private ChatMessageController messageController;
	private ChatClient client;
	private ChatUserController userController;
	
	public ChatController(ChatMainWindow main, ChatClient client) {
		this.main = main;
		this.client = client;
		this.messageController = new ChatMessageController(main.getChatMessageWindow(), client);		
		this.userController = new ChatUserController(main.getChatUserPane(), client);
		this.userController.setOutputController(messageController);
		setupInfoPane();
	}
	
	private void setupInfoPane() {
		main.getChatInfoPane().addInfo("Username: " + client.getUsername());
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
