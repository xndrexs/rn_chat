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
		UUID id = chatMessage.getUUID();
		if (tabs.containsKey(id)) {
			ChatMessageTab tab = tabs.get(id);
			tab.displayMessage(chatMessage.getMessage());
		} else {
			ChatMessageTab tab = new ChatMessageTab(id.toString());
			tabs.put(id, tab);
			getTabs().add(tab);
		}
	}

}
