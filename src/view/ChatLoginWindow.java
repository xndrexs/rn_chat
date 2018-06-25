package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ChatLoginWindow extends GridPane {
	
	private TextField loginUsername;
	private TextField loginPassword;
	private Button loginButton;
	
	private TextField registerUsername;
	private TextField registerPassword;
	private Button registerButton;
	
	public ChatLoginWindow() {
		
		loginUsername = new TextField("");
		loginPassword = new TextField("");
		loginButton = new Button("Login");
		
		add(new Label("Username"), 0, 0);
		add(new Label("Password"), 0, 1);
		add(loginUsername, 1, 0);
		add(loginPassword, 1, 1);
		add(loginButton, 1, 2);
		
		registerUsername = new TextField("");
		registerPassword = new TextField("");
		registerButton = new Button("Register");
		
		add(new Label("New Username"), 0, 4);
		add(new Label("New Password"), 0, 5);
		add(registerUsername, 1, 4);
		add(registerPassword, 1, 5);
		add(registerButton, 1, 6);
		
		setAlignment(Pos.CENTER);
	}
	
	public Button getLoginButton() {
		return loginButton;
	}
	
	public String getLoginUsernameInput() {
		return loginUsername.getText();
	}
	
	public String getLoginPasswordInput() {
		return loginPassword.getText();
	}
	
	public Button getRegisterButton() {
		return registerButton;
	}
	
	public String getRegisterUsernameInput() {
		return registerUsername.getText();
	}
	
	public String getRegisterPasswordInput() {
		return registerPassword.getText();
	}

}
