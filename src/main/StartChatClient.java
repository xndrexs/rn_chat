package main;

import controller.ChatConnectController;
import controller.ChatController;
import helper.StageManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ChatConnectWindow;
import view.ChatMainWindow;

public class StartChatClient extends Application {
	
	StageManager manager;
	
	public static void main(String [] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		manager = new StageManager(stage);
		manager.startConnectProcess();
	}

}
