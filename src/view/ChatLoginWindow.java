package view;

import javafx.geometry.Insets;
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
		
		setMargin(loginUsername, new Insets(5, 10, 5, 10));
		setMargin(loginPassword, new Insets(5, 10, 5, 10));
		setMargin(loginButton, new Insets(5, 10, 30, 10));
		
		add(new Label("Username"), 0, 0);
		add(new Label("Password"), 0, 1);
		add(loginUsername, 1, 0);
		add(loginPassword, 1, 1);
		add(loginButton, 1, 2);
		
		registerUsername = new TextField("");
		registerPassword = new TextField("");
		registerButton = new Button("Register");
		
		setMargin(registerUsername, new Insets(5, 10, 5, 10));
		setMargin(registerPassword, new Insets(5, 10, 5, 10));
		setMargin(registerButton, new Insets(5, 10, 5, 10));
		
		add(new Label("New Username"), 0, 4);
		add(new Label("New Password"), 0, 5);
		add(registerUsername, 1, 4);
		add(registerPassword, 1, 5);
		add(registerButton, 1, 6);
		
		setPadding(new Insets(10, 10, 10, 10));
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
