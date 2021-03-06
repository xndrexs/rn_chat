package controller;

import java.util.HashMap;
import java.util.Map;

import model.ChatClient;
import model.ChatMessage;
import model.ChatUser;
import view.ChatMessageWindow;
import view.ChatPopupWindow;

public class ChatMessageController {
	
	private ChatPopupController chatPopupController;
	private ChatPopupWindow chatPopupWindow;
	private ChatMessageWindow chatMessageWindow;
	private ChatClient client;
	private Map<String, ChatIOController> controllers;

	public ChatMessageController(ChatMessageWindow chatMessageWindow, ChatClient client) {
		this.chatMessageWindow = chatMessageWindow;
		this.client = client;
		this.controllers = new HashMap<String, ChatIOController>();
	}
	
	public void startNewChat(ChatUser user) {
		String id = user.getID().toString();
		if (!controllers.containsKey(id)) {
			ChatIOController controller = new ChatIOController(user, client);
			controllers.put(id, controller);
			chatMessageWindow.getTabs().add(controller.getTab());
		}
	}
	
	public ChatMessageWindow getTabPane() {
		return chatMessageWindow;
	}
	
	public void handleMessage(ChatMessage message) {
		String id = message.getUUID().toString();
		if (controllers.containsKey(id)) {
			ChatIOController controller = controllers.get(id);
			controller.handleMessage(message);
		} else {
			ChatUser user = client.getClients().get(id);
			chatPopupWindow = new ChatPopupWindow();
			chatPopupController = new ChatPopupController(chatPopupWindow, user, client, controllers, id, chatMessageWindow, message);
		}
	}
}
