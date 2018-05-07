package view;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ChatMessageTab extends Tab {
	
	BorderPane pane;
	ChatInputPane input;
	VBox messageBox;

	public ChatMessageTab(String name) {
		super(name);
		input = new ChatInputPane();
		messageBox = new VBox();
		pane = new BorderPane();
		
		pane.setCenter(messageBox);
		pane.setBottom(input);
		
		setContent(pane);
	}
	
	public void displayMessage(String message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				messageBox.getChildren().add(new Label(message));
			}
		});
	}
	
	public ChatInputPane getChatInputPane() {
		return input;
	}
	
	public String getTitle() {
		return getText();
	}
	
	public void setTitle(String title) {
		super.setText(title);
	}
	
	public ChatInputPane getInput() {
		return input;
	}
}