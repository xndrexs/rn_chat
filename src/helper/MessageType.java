package helper;

public enum MessageType {
	Disconnect("Disconnect"), Connect("Connect");
	
	private String type;
	
	MessageType(String type){
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
