package controller;

import java.util.Date;

import helper.MessageFormatter;
import helper.MessageHandler;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.ChatClient;
import model.ChatMessage;
import model.ChatUser;
import view.ChatInputPane;
import view.ChatMessageTab;

public class ChatIOController {

	private ChatUser user;
	private ChatMessageTab tab;
	private ChatClient client;

	private ChatInputPane input;
	private TextArea textArea;
	private Button sendButton;
	private MessageHandler messageHandler;

	public ChatIOController(ChatUser user, ChatClient client) {
		this.user = user;
		this.client = client;
		this.tab = new ChatMessageTab(user.getID().toString());
		this.input = tab.getChatInputPane();
		;
		this.textArea = input.getTextArea();
		this.sendButton = input.getSendButton();
		this.messageHandler = new MessageHandler(client.getId(), user.getPort(), user.getAddress());
		this.user = user;
		setupButton();
		
		EventHandler<Event> tabClosed = new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				sendMessage("User closed your MessageTab");
				
			}
		};
		tab.setOnClosed(tabClosed);
	}

	public void handleMessage(ChatMessage chatMessage) {
		String message = (new Date().toString() + " | " + user.getUsername() + ": " + chatMessage.getMessage());
		tab.displayMessage(message);
	}

	public void displayOwnMessage(String chatMessage) {
		String message = (new Date().toString() + " | " + client.getUsername() + ": " + chatMessage);
		tab.displayMessage(message);
	}

	public ChatMessageTab getTab() {
		return tab;
	}

	private void setupButton() {
		EventType<MouseEvent> eventType = MouseEvent.MOUSE_CLICKED;
		EventHandler<Event> eventHandler = new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				String message = textArea.getText();
				if (message != null) {
					sendMessage(message);
					displayOwnMessage(message);
				}
				textArea.clear();
			}
		};
		sendButton.addEventHandler(eventType, eventHandler);
	}
	
	private void sendMessage(String message) {
		client.sendUDPMessage(user, messageHandler.serializeMessage(message));
	}
}
