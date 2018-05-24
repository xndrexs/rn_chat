package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ChatConnectWindow extends GridPane {
	
	private TextField adress;
	private TextField port;
	private Button connect;
	
	public ChatConnectWindow() {
		
		adress = new TextField("localhost");
		port = new TextField("55555");
		connect = new Button("Connect");
		
		add(new Label("Server"), 0, 0);
		add(new Label("Port"), 0, 1);
		add(adress, 1, 0);
		add(port, 1, 1);
		add(connect, 1, 2);
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
