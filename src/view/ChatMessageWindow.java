package view;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javafx.scene.control.TabPane;
import model.ChatMessage;

public class ChatMessageWindow extends TabPane {
	
	Map<String, ChatMessageTab> tabs;
	
	public ChatMessageWindow() {
		tabs = new HashMap<String, ChatMessageTab>();
	}
	
	public Map<String, ChatMessageTab> getTabsMap() {
		return tabs;
	}
}
