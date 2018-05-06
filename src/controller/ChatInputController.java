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
	private ChatOutputController output;
	
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
					// client.sendMessage(message);
					String id = output.getTabPane().getSelectionModel().getSelectedItem().getText();
					ChatUser user = client.getClients().get(id);
					String jsonMessage = MessageHandler.serializeMessageForUDP(user.getID(), user.getPort(), message);
					client.sendUDPMessage(user, jsonMessage);
				}
			}
		};
		sendButton.addEventHandler(eventType, eventHandler);
	}
	
	public String getMessageInput() {
		return message;
	}

	public void setOutputController(ChatOutputController output) {
		this.output = output;
	}
	
}
