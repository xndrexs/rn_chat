package main;

import controller.ChatController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ChatMainWindow;

public class StartChatClient extends Application {
	
	ChatController controller;
	ChatMainWindow main;
	Scene scene;
	
	public static void main(String [] args) {
		launch(args);
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		main = new ChatMainWindow();
		scene = new Scene(main, 800, 800);
		controller = new ChatController(main);
		
		stage.setTitle("Chat");
		stage.setScene(scene);
		stage.show();
	}

}
