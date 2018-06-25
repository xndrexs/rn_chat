package view;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TabPane;

public class ChatMessageWindow extends TabPane {
	
	Map<String, ChatMessageTab> tabs;
	
	public ChatMessageWindow() {
		tabs = new HashMap<String, ChatMessageTab>();
	}
	
	public Map<String, ChatMessageTab> getTabsMap() {
		return tabs;
	}
}
