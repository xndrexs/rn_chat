package main;

import model.ChatServer;

public class StartChatServer {

	public static void main (String [] args) {
		ChatServer server = new ChatServer();
		server.start();
	}

}
