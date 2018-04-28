package model;

public enum SenderType {
	
	Server("Server"), 
	Client("Client");
	
	private String type;
	
	SenderType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}	
}
