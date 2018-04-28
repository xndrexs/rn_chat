package model;

public class ChatRoomClient {
	
	public static void main (String [] args) {
		
		ChatClient client = new ChatClient("localhost", 12345);
		UDPSender sender1 = new UDPSender("localhost", 7777, "FROM UDP SENDER");
	}
}
