package helper;

import javafx.application.Platform;
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
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if(change.wasAdded()) {
					listView.getItems().add(change.getKey().toString());
				}
				if(change.wasRemoved()) {
					listView.getItems().remove(change.getKey().toString());
				}
			}
		});
	}
}
