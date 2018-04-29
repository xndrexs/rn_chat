package view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ChatInfoPane extends VBox {
	
	String id;

	public ChatInfoPane() {
		
	}
	
	public void addInfo(String info) {
		getChildren().add(new Label(info));
	}
	
}
