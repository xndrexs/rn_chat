package view;

import javafx.scene.layout.BorderPane;

public class ChatBorderPane extends BorderPane{

	public ChatBorderPane() {
		
		ChatInputPane chatInput = new ChatInputPane();
		this.setBottom(chatInput);
		ChatMessageWindow chatMessageWindow = new ChatMessageWindow();
		this.setCenter(chatMessageWindow);
	}
	
}
