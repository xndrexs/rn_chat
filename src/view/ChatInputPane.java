package view;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class ChatInputPane extends HBox {

	public ChatInputPane() {
		
		// Eingabe des Textes
		TextArea input = new TextArea("Type your message here ... ");
		input.setPrefRowCount(3);
		
		// Abschicken der Nachricht
		Button send = new Button("Send");
		
		getChildren().addAll(input, send);
	}
	
}
