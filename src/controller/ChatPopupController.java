package controller;

import java.util.Map;

import helper.MessageHandler;
import helper.UDPSender;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import model.ChatClient;
import model.ChatMessage;
import model.ChatUser;
import view.ChatMessageWindow;
import view.ChatPopupWindow;

public class ChatPopupController {

	ChatUser user;
	ChatClient client;
	Map<String, ChatIOController> controllers;
	String id;
	ChatMessageWindow chatMessageWindow;
	ChatPopupWindow popupWindow;
	ChatMessage message;
	
	public ChatPopupController(ChatPopupWindow popupWindow, ChatUser user, ChatClient client, Map<String, ChatIOController> controllers, String id, ChatMessageWindow chatMessageWindow, ChatMessage message) {
		this.user = user;
		this.client = client;
		this.controllers = controllers;
		this.id = id;
		this.chatMessageWindow = chatMessageWindow;
		this.message = message;
		this.popupWindow = popupWindow;
		
		EventType<MouseEvent> acceptEventType = MouseEvent.MOUSE_CLICKED;
		EventHandler<Event> acceptEventHandler = new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
					popupWindow.closePopup();
					ChatIOController controller = new ChatIOController(user, client);
					controllers.put(id, controller);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							chatMessageWindow.getTabs().add(controller.getTab());
						}
					});
					controller.handleMessage(message);
			}
		};
		popupWindow.getAcceptButton().addEventHandler(acceptEventType, acceptEventHandler);
//		
//		EventType<MouseEvent> declineEventType = MouseEvent.MOUSE_CLICKED;
//		EventHandler<Event> declineEventHandler = new EventHandler<Event>() {
//			@Override
//			public void handle(Event arg0) {
//				MessageHandler message = new MessageHandler(user.getID(), user.getPort(), user.getAddress());
//				UDPSender sender = new UDPSender(user.getAddress(), user.getPort());
//				sender.sendMessage(message.serializeMessage("The user declined your request."));
//				popupWindow.closePopup();
//			}
//		};
//		popupWindow.getDeclineButton().addEventHandler(declineEventType, declineEventHandler);
	}
}
