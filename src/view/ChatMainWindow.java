package view;

import javafx.scene.layout.BorderPane;

public class ChatMainWindow extends BorderPane{

	ChatMessageWindow chatMessageWindow;
	ChatInfoPane chatInfo;
	ChatUserPane chatUserPane;
	ChatLog chatLog;
	ChatConnectWindow connect;
	
	public ChatMainWindow() {
		connect = new ChatConnectWindow();
		this.setCenter(connect);
		
		chatInfo = new ChatInfoPane();
		this.setTop(chatInfo);
		
		chatLog = new ChatLog();
		this.setBottom(chatLog);
	}
	
	public void setupChatWindow() {
		chatUserPane = new ChatUserPane();
		this.setRight(chatUserPane);
		
		chatMessageWindow = new ChatMessageWindow();
		this.setCenter(chatMessageWindow);
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
