package helper;

import controller.ChatConnectController;
import controller.ChatController;
import controller.ChatLoginController;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ChatClient;
import view.ChatConnectWindow;
import view.ChatLoginWindow;
import view.ChatMainWindow;

public class StageManager {

	Stage stage;
	ChatController controller;
	ChatMainWindow main;
	ChatConnectController connectController;
	ChatConnectWindow connectWindow;
	Scene scene;
	ChatClient client;
	
	ChatLoginWindow loginWindow;
	ChatLoginController loginController;
	
	public StageManager(Stage stage) {
		this.stage = stage;
	}
	
	public void startConnectProcess() {
		connectWindow = new ChatConnectWindow();
		connectController = new ChatConnectController(connectWindow, this);
		scene = new Scene(connectWindow, 300, 300);
		stage.setTitle("Connect");
		stage.setScene(scene);
		stage.show();
	}

	public void startLoginProcess() {
		this.client = connectController.getChatClient();
		loginWindow = new ChatLoginWindow();
		loginController = new ChatLoginController(loginWindow, this, client);
		scene = new Scene(loginWindow, 500, 500);
		
		stage.setTitle("Login");
		stage.setScene(scene);
	}

	public void startChatProcess() {
		main = new ChatMainWindow();
		controller = new ChatController(main, client);
		client.setController(controller);
		scene = new Scene(main, 800, 800);
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				stage.setTitle("Chat");
				stage.setScene(scene);
			}
		});

	}
	
}
