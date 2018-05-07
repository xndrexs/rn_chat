package controller;

import helper.MessageHandler;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import model.ChatClient;
import model.ChatUser;
import view.ChatInputPane;

public class ChatInputController {

	private ChatInputPane input;
	private TextArea textArea;
	private Button sendButton;
	private String message;
	private ChatClient client;
	private ChatMessageController output;
	private ChatUser user;
	private MessageHandler messageHandler;
	
	public ChatInputController(ChatInputPane input, ChatClient client, ChatUser user) {
		this.input = input;
		this.textArea = input.getTextArea();
		this.sendButton = input.getSendButton();
		this.client = client;
		this.messageHandler = new MessageHandler(client.getId(), user.getPort());
		this.user = user;
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
					client.sendUDPMessage(user, messageHandler.serializeMessage(message));
					System.out.println(messageHandler.serializeMessage(message));
				}
			}
		};
		sendButton.addEventHandler(eventType, eventHandler);
	}
	
	public String getMessageInput() {
		return message;
	}

	public void setOutputController(ChatMessageController output) {
		this.output = output;
	}
}
