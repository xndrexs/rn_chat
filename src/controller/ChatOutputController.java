package controller;

import model.ChatMessage;
import view.ChatMessageWindow;

public class ChatOutputController {
	
	ChatMessageWindow chatMessageWindow;

	public ChatOutputController(ChatMessageWindow chatMessageWindow) {
		this.chatMessageWindow = chatMessageWindow;
	}
	
	public void displayMessage(ChatMessage message) {
		chatMessageWindow.displayMessage(message);
	}
	
}
