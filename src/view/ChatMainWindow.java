package view;

import javafx.scene.layout.BorderPane;

public class ChatMainWindow extends BorderPane{

	ChatInputPane chatInput;
	ChatMessageWindow chatMessageWindow;
	
	public ChatMainWindow() {
		
		chatInput = new ChatInputPane();
		this.setBottom(chatInput);
		chatMessageWindow = new ChatMessageWindow();
		this.setCenter(chatMessageWindow);
		
	}
	
	public ChatInputPane getChatInputPane() {
		return chatInput;
	}
	
}
