package controller;

import helper.MyMapChanger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableMap;
import javafx.scene.control.ListView;
import model.ChatUser;
import view.ChatUserPane;

public class ChatUserController {

	private ChatUserPane chatUserPane;
	private ChatOutputController output;
	private ObservableMap<String, ChatUser> clients;
	private ListView<String> userList;

	public ChatUserController(ChatUserPane chatUserPane, ObservableMap<String, ChatUser> clients) {
		this.chatUserPane = chatUserPane;
		this.clients = clients;
		this.userList = chatUserPane.getListView();
		setupListenerForUserPane();
	}
	
	private void setupListenerForUserPane() {
		userList.getItems().addAll(clients.keySet());
		clients.addListener(new MyMapChanger(userList));
		userList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				output.openNewChatTab(clients.get(arg2));
				// System.out.println(arg2);
			}
		});
	}
	
	public void setOutputController(ChatOutputController output) {
		this.output = output;
	}
}
