package view;

import javafx.scene.layout.BorderPane;

public class ChatMainWindow extends BorderPane{

	ChatInputPane chatInput;
	ChatMessageWindow chatMessageWindow;
	ChatInfoPane chatInfo;
	
	public ChatMainWindow() {
		
		chatInput = new ChatInputPane();
		this.setBottom(chatInput);
		chatMessageWindow = new ChatMessageWindow();
		this.setCenter(chatMessageWindow);
		chatInfo = new ChatInfoPane();
		this.setTop(chatInfo);
		
	}
	
	public ChatInputPane getChatInputPane() {
		return chatInput;
	}
	
	public ChatInfoPane getChatInfoPane() {
		return chatInfo;
	}
	
	public ChatMessageWindow getChatMessageWindow() {
		return chatMessageWindow;
	}
	
}
