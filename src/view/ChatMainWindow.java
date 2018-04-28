package view;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ChatMainWindow extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		ChatBorderPane pane = new ChatBorderPane();
		Scene scene = new Scene(pane, 800, 800);
		stage.setTitle("Chat");
		stage.setScene(scene);
		stage.show();		
	}
}
