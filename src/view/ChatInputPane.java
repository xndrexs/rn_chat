package view;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class ChatInputPane extends HBox {
	
	TextArea input;
	Button send;
	
	public ChatInputPane() {
		
		// Eingabe des Textes
		input = new TextArea("Type your messages...");
		input.setPrefRowCount(3);
				
		// Abschicken der Nachricht
		send = new Button("Send");
		
		getChildren().addAll(input, send);
	}
	
	public Button getSendButton() {
		return send;
	}
	
	public TextArea getTextArea() {
		return input;
	}
	
}
