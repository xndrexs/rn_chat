package view;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class ChatInputPane extends HBox {
	
	TextArea input;
	Button send;
	
	public ChatInputPane() {
		input = new TextArea();
		input.setPrefRowCount(3);
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
