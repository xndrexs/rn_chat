package view;

import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class ChatUserPane extends VBox {
	
	ListView<String> list;
	
	public ChatUserPane() {
		list = new ListView<String>();
	}
	
	public ListView<String> getListView(){
		return list;
	}
}
