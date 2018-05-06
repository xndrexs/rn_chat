package controller;

import java.util.Map;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Tab;
import model.ChatMessage;
import model.ChatUser;
import view.ChatMessageTab;
import view.ChatMessageWindow;

public class ChatOutputController {
	
	private ChatMessageWindow chatMessageWindow;

	public ChatOutputController(ChatMessageWindow chatMessageWindow) {
		this.chatMessageWindow = chatMessageWindow;
	}
	
	public void displayMessage(ChatMessage message) {
		Map<String, ChatMessageTab> tabs = chatMessageWindow.getTabsMap();
		String id = message.getUUID().toString();
		ChatMessageTab tab;
		if (tabs.containsKey(id)) {
			tab = tabs.get(id);
			
		} else {
			tab = new ChatMessageTab(id.toString());
			tabs.put(id, tab);
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					chatMessageWindow.getTabs().add(tab);
				}	
			});
		}
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				tab.displayMessage(message.getMessage());
			}	
		});
		
	}
	
	public void openNewChatTab(ChatUser user) {
		Map<String, ChatMessageTab> tabs = chatMessageWindow.getTabsMap();
		String id = user.getID().toString();
		if (!tabs.containsKey(id)) {
			ChatMessageTab tab = new ChatMessageTab(id.toString());
			tabs.put(id, tab);
			chatMessageWindow.getTabs().add(tab);
		}
	}
	
	public void setActiveTabAndUser() {
		chatMessageWindow.getTabs().addListener(new ListChangeListener<Tab>() {

			@Override
			public void onChanged(Change<? extends Tab> arg0) {
								
			}
		});
	}
	
	public ChatMessageWindow getTabPane() {
		return chatMessageWindow;
	}
}
