package helper;

public enum MessageType {
	Disconnect("Disconnect"), Connect("Connect"), Login("Login"), Register("Register");
	
	private String type;
	
	MessageType(String type){
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
