package view;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ChatConnectWindow extends GridPane {
	
	private TextField adress;
	private TextField port;
	private Button connect;
	
	public ChatConnectWindow() {
		
		adress = new TextField("localhost");
		port = new TextField("55555");
		connect = new Button("Connect");
		setMargin(adress, new Insets(5, 10, 5, 10));
		setMargin(port, new Insets(5, 10, 5, 10));
		setMargin(connect, new Insets(5, 10, 5, 10));
		
		setPadding(new Insets(10, 10, 10, 10));
		
		add(new Label("Server:"), 0, 0);
		add(new Label("Port:"), 0, 1);
		add(adress, 1, 0);
		add(port, 1, 1);
		add(connect, 1, 2);
		setAlignment(Pos.CENTER);
	}
	
	public Button getConnectButton() {
		return connect;
	}
	
	public String getAdressInput() {
		return adress.getText();
	}
	
	public String getPortInput() {
		return port.getText();
	}

}
