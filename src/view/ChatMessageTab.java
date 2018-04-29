package view;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class ChatMessageTab extends Tab {
	
	VBox messageBox;

	public ChatMessageTab(String name) {
		super(name);
		messageBox = new VBox();
		setContent(messageBox);
	}
	
	public void displayMessage(String message) {
		messageBox.getChildren().add(new Label(message));
	}
	
}
