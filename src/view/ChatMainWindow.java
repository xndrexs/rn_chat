package view;

import javafx.scene.layout.BorderPane;

public class ChatMainWindow extends BorderPane{

	ChatMessageWindow chatMessageWindow;
	ChatInfoPane chatInfo;
	ChatUserPane chatUserPane;
	ChatLog chatLog;
	
	public ChatMainWindow() {
		
		chatMessageWindow = new ChatMessageWindow();
		this.setCenter(chatMessageWindow);
		
		chatInfo = new ChatInfoPane();
		this.setTop(chatInfo);
		
		chatUserPane = new ChatUserPane();
		this.setRight(chatUserPane);
		
		chatLog = new ChatLog();
		this.setLeft(chatLog);
		
	}
	
	public ChatMessageWindow getChatMessageWindow() {
		return chatMessageWindow;
	}

	public ChatUserPane getChatUserPane() {
		return chatUserPane;
	}
	
	public ChatInfoPane getChatInfoPane() {
		return chatInfo;
	}	
	
	public ChatLog getChatLog() {
		return chatLog;
	}
}
