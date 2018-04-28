package model;
import java.util.Date;
import java.util.UUID;

public class MessageFormatter {
	
	private SenderType senderType;
	private UUID id;
	
	public MessageFormatter(UUID id, SenderType senderType) {
		this.id = id;
		this.senderType = senderType;
	}
	
	public void printMessage(String message) {
		System.out.println(new Date().toString() + " - " + senderType.getType() + "(" + id + ")" +  " - " + message);
	}
	
	public static void print(String message) {
		System.out.println(new Date().toString() + " - " + message);
	}

}
