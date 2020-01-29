package client.socket;

import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
	
	private static final Logger logger = LoggerFactory.getLogger(Client.class);
	Socket socket = null;
	String serverIP = "localhost";
	int serverPort = 5000;
	String fileName;
	String result;
	
	public Client(String fileName) {
		logger.debug("-- Client Start --");
		this.fileName = fileName;
		
		try {
			socket = new Socket(serverIP, serverPort);
			logger.debug("서버에 연결되었습니다");
			logger.debug(serverIP + " : " + serverPort);
			
			FileSender fileSender = new FileSender(socket, fileName);
			fileSender.start();
			fileSender.join();
			result = fileSender.getResult();
			logger.debug("result from server : " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("-- Client End --");
	}
	
	public String getResult() {
		return result;
	}
}
