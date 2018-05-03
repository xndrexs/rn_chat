package view;

import javafx.scene.layout.BorderPane;

public class ChatMainWindow extends BorderPane{

	ChatInputPane chatInput;
	ChatMessageWindow chatMessageWindow;
	ChatInfoPane chatInfo;
	ChatUserPane chatUserPane;
	
	public ChatMainWindow() {
		
		chatInput = new ChatInputPane();
		this.setBottom(chatInput);
		chatMessageWindow = new ChatMessageWindow();
		this.setCenter(chatMessageWindow);
		chatInfo = new ChatInfoPane();
		this.setTop(chatInfo);
		chatUserPane = new ChatUserPane();
		this.setRight(chatUserPane);
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
	
	public ChatUserPane getChatUserPane() {
		return chatUserPane;
	}
	
}
