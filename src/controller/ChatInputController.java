package controller;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import model.ChatClient;
import view.ChatInputPane;

public class ChatInputController {

	ChatInputPane input;
	TextArea textArea;
	Button sendButton;
	String message;
	ChatClient client;
	
	public ChatInputController(ChatInputPane input, ChatClient client) {
		
		this.input = input;	
		this.textArea = input.getTextArea();
		this.sendButton = input.getSendButton();
		this.client = client;
		
		setupButton();
		
		
	}
	
	private void setupButton() {
		EventType<MouseEvent> eventType = MouseEvent.MOUSE_CLICKED;
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				message = textArea.getText();
				if (message != null) {
					textArea.clear();
					client.sendMessage(message);
				}
			}
		};
		sendButton.addEventHandler(eventType, eventHandler);
	}
	
	public String getMessageInput() {
		return message;
	}
	
}
