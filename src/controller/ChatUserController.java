package controller;

import helper.MyMapChanger;
import javafx.collections.ObservableMap;
import model.ChatUser;
import view.ChatUserPane;

public class ChatUserController {

	private ChatUserPane chatUserPane;
	private ObservableMap<String, ChatUser> clients;

	public ChatUserController(ChatUserPane chatUserPane, ObservableMap<String, ChatUser> clients) {
		this.chatUserPane = chatUserPane;
		this.clients = clients;
		setupListenerForUserPane();
	}
	
	private void setupListenerForUserPane() {
		chatUserPane.getListView().getItems().addAll(clients.keySet());
		clients.addListener(new MyMapChanger(chatUserPane.getListView()));
	}
}
