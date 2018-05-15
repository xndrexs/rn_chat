package view;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ChatLog extends VBox {
	
	ScrollPane logPane;
	VBox logMessages;
	
	public ChatLog() {
		logMessages = new VBox();
		logPane = new ScrollPane();
		logPane.setContent(logMessages);
		logPane.setPrefViewportHeight(50);
		getChildren().addAll(new Label("Log"), logPane);
	}
	
	public void displayLogMessage(String message) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				logMessages.getChildren().add(0, new Label(message));
			}
		});
	}
}
