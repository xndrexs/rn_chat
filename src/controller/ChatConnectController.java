package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import view.ChatConnectWindow;

public class ChatConnectController {
	
	ChatConnectWindow connect;
	
	public ChatConnectController(ChatConnectWindow connect) {
		this.connect = connect;
		

		EventType<MouseEvent> eventType = MouseEvent.MOUSE_CLICKED;
		EventHandler<Event> eventHandler = new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				String adress = connect.getAdressInput();
				String port = connect.getPortInput();
			}
		};
		connect.getConnectButton().addEventHandler(eventType, eventHandler);
	}
}
