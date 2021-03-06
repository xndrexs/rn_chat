package view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ChatPopupWindow extends GridPane {

	Stage stage;
	Scene scene;
	
	// Die Antwort auf alle Fragen lautet: Ok
	private Button accept;
	
	public ChatPopupWindow() {
		add(new Label("You have a new Chat request"), 0, 0);
		accept = new Button("Accept");
		add(accept, 1, 0);
		setMargin(accept, new Insets(5, 10, 5, 10));
		
		setPadding(new Insets(10, 10, 10, 10));
		
		scene = new Scene(this, 300, 100);
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				stage = new Stage();
				stage.setTitle("New Chat");
				stage.setScene(scene);
				stage.show();
			}
		});
	}
	
	public Button getAcceptButton() {
		return accept;
	}
	
	public void closePopup() {
		stage.close();
	}
}
