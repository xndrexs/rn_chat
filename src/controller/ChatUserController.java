package controller;

import java.util.Map;
import java.util.UUID;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ChatUser;
import view.ChatUserPane;

public class ChatUserController {

	ChatUserPane chatUserPane;
	ObservableList<String> clients;
	
	public ChatUserController(ChatUserPane chatUserPane, Map<UUID, ChatUser> clients) {
		this.chatUserPane = chatUserPane;
		this.clients = FXCollections.observableArrayList("TEST1");
		chatUserPane.getListView().setItems(this.clients);
	}
}
