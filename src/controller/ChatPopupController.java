package controller;

import java.io.IOException;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import model.ChatClient;
import model.ChatUser;
import view.ChatPopupWindow;

public class ChatPopupController {

	ChatUser user;
	ChatClient client;
	ChatPopupWindow popupWindow;
	Boolean accepted;
	
	public ChatPopupController(ChatPopupWindow popupWindow, ChatUser user, ChatClient client) {
		this.user = user;
		this.client = client;
		this.popupWindow = popupWindow;
		
		EventType<MouseEvent> acceptEventType = MouseEvent.MOUSE_CLICKED;
		EventHandler<Event> acceptEventHandler = new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				accepted = true;
			}
		};
		popupWindow.getAcceptButton().addEventHandler(acceptEventType, acceptEventHandler);
		
		EventType<MouseEvent> declineEventType = MouseEvent.MOUSE_CLICKED;
		EventHandler<Event> declineEventHandler = new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				accepted = false;
			}
		};
		popupWindow.getDeclineButton().addEventHandler(declineEventType, declineEventHandler);
	}
	
	public Boolean getAcceptedButton() {
		return accepted;
	}
}
