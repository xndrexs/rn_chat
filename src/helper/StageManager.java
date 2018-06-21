package helper;

import controller.ChatConnectController;
import controller.ChatController;
import controller.ChatLoginController;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
	
	ChatLoginWindow loginWindow;
	ChatLoginController loginController;
	
	public StageManager(Stage stage) {
		this.stage = stage;
	}
	
	public void startConnectProcess() {
		connectWindow = new ChatConnectWindow();
		connectController = new ChatConnectController(connectWindow, this);
		scene = new Scene(connectWindow, 800, 800);
		//main = new ChatMainWindow();
		//controller = new ChatController(main);
		stage.setTitle("Connect");
		stage.setScene(scene);
		stage.show();
	}

	public void startLoginProcess() {
		loginWindow = new ChatLoginWindow();
		loginController = new ChatLoginController(loginWindow);
		scene = new Scene(loginWindow, 800, 800);
		
		stage.setTitle("Login");
		stage.setScene(scene);
	}
	
}
