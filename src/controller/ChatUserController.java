package controller;

import helper.MyMapChanger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableMap;
import javafx.scene.control.ListView;
import model.ChatClient;
import model.ChatUser;
import view.ChatUserPane;

public class ChatUserController {

	private ChatUserPane chatUserPane;
	private ChatMessageController messageController;
	private ListView<String> userList;
	private ChatClient client;
	private ObservableMap<String, ChatUser> clients;

	public ChatUserController(ChatUserPane chatUserPane, ChatClient client) {
		this.chatUserPane = chatUserPane;
		this.client = client;
		this.clients = client.getClients();
		this.userList = chatUserPane.getListView();
		setupListenerForUserPane();
	}
	
	private void setupListenerForUserPane() {
		userList.getItems().addAll(clients.keySet());
		clients.addListener(new MyMapChanger(userList));
		userList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String userId) {
				ChatUser user = client.getClientByName(userId);
				messageController.startNewChat(user);
			}
		});
	}
	
	public void setOutputController(ChatMessageController messageController) {
		this.messageController = messageController;
	}
}
