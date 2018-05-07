package controller;

import java.util.Date;

import helper.MessageFormatter;
import model.ChatClient;
import model.ChatMessage;
import model.ChatUser;
import view.ChatMessageTab;

public class ChatIOController {
	
	private ChatUser user;
	private ChatMessageTab tab;
	private ChatInputController input;
	private ChatClient client;
	private MessageFormatter formatter;
	
	public ChatIOController(ChatUser user, ChatClient client) {
		this.user = user;
		this.client = client;
		this.tab = new ChatMessageTab(user.getID().toString());
		this.input = new ChatInputController(tab.getChatInputPane(), client, user);
	}
	
	public void handleMessage(ChatMessage chatMessage) {
		String message = (new Date().getTime() + " | " + user.getID() + ": " + chatMessage.getMessage());
		tab.displayMessage(message);
	}
	
	public ChatMessageTab getTab() {
		return tab;
	}

}
