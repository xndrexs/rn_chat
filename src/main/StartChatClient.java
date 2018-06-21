package main;

import helper.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;

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
