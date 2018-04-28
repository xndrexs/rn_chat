package view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ChatMessageWindow extends VBox {
	
	public ChatMessageWindow() {
		Label chatMessage = new Label("Text Message");
		getChildren().add(chatMessage);
	}

}
