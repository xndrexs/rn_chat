package view;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javafx.scene.control.TabPane;
import model.ChatMessage;

public class ChatMessageWindow extends TabPane {
	
	Map<UUID, ChatMessageTab> tabs;
	
	public ChatMessageWindow() {
		tabs = new HashMap<UUID, ChatMessageTab>();
	}
	
	public void displayMessage(ChatMessage chatMessage) {
		if (tabs.containsKey(chatMessage.getUUID())) {
			ChatMessageTab tab = tabs.get(chatMessage.getUUID());
			tab.displayMessage(chatMessage.getMessage());
		} else {
			ChatMessageTab tab = new ChatMessageTab(chatMessage.getUUID().toString());
			tabs.put(chatMessage.getUUID(), tab);
			getTabs().add(tab);
		}
	}

}
