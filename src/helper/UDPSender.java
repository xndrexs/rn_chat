package helper;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.UUID;

public class UDPSender {
	
	private UUID id;
	private int port;
	private String address;

	public UDPSender(String address, int port) {
		this.port = port;
		this.address = address;
	}
	
	public void sendMessage(String message) {
		DatagramSocket clientSocket = null;
		InetAddress ipAddress = null;
		
		byte[] sendData = message.getBytes();
		
		try {
			clientSocket = new DatagramSocket();
			ipAddress = InetAddress.getByName(address);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
		try {
			clientSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		clientSocket.close();
	}
	
}
