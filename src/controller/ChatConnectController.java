package controller;

import java.io.IOException;

import helper.StageManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import model.ChatClient;
import model.ChatServer;
import view.ChatConnectWindow;

public class ChatConnectController {
	
	ChatConnectWindow connect;
	String address;
	String port;
	ChatClient client;
	
	public ChatConnectController(ChatConnectWindow connect, StageManager manager) {
		
		this.connect = connect;
		
		EventType<MouseEvent> eventType = MouseEvent.MOUSE_CLICKED;
		EventHandler<Event> eventHandler = new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				address = connect.getAdressInput();
				port = connect.getPortInput();
				
				int port = ChatServer.SERVER_PORT;
				
				try {
					client = new ChatClient(address, port);
					manager.startLoginProcess();
				} catch (IOException e) {
					System.out.println("Unknown Host ...");
					e.printStackTrace();
				}
			}
		};
		connect.getConnectButton().addEventHandler(eventType, eventHandler);
	}
	
	public String getAddress() { return address; }
	public String getPort() { return port; }
}
