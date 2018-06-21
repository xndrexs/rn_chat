package controller;

import java.io.IOException;

import helper.MessageType;
import helper.StageManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import model.ChatClient;
import model.ChatServer;
import view.ChatLoginWindow;

public class ChatLoginController {
	
	ChatLoginWindow login;
	String username;
	String password;
	ChatClient client;
	StageManager manager;

	public ChatLoginController(ChatLoginWindow login, StageManager manager, ChatClient client) {
		this.login = login;
		this.client = client;
		
		EventType<MouseEvent> loginEventType = MouseEvent.MOUSE_CLICKED;
		EventHandler<Event> loginEventHandler = new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				client.sendMessageForLogin(login.getLoginUsernameInput(), login.getLoginPasswordInput(), MessageType.Login);
			}
		};
		login.getLoginButton().addEventHandler(loginEventType, loginEventHandler);
		
		EventType<MouseEvent> registerEventType = MouseEvent.MOUSE_CLICKED;
		EventHandler<Event> registerEventHandler = new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				client.sendMessageForLogin(login.getRegisterUsernameInput(), login.getRegisterPasswordInput(), MessageType.Register);
			}
		};
		login.getRegisterButton().addEventHandler(registerEventType, registerEventHandler);
		
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

}
