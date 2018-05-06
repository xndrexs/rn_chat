package helper;

import javafx.collections.MapChangeListener;
import javafx.scene.control.ListView;
import model.ChatUser;

public class MyMapChanger implements MapChangeListener<String, ChatUser> {
	
	ListView<String> listView;
	
	public MyMapChanger(ListView<String> listView) {
		this.listView = listView;
	}

	@Override
	public void onChanged(Change<? extends String, ? extends ChatUser> change) {
		System.out.println("CHANGE CALLED");
		if(change.wasAdded()) {
			listView.getItems().add(change.getKey().toString());
		}
		if(change.wasRemoved()) {
			listView.getItems().remove(change.getKey().toString());
		}
	}
}
