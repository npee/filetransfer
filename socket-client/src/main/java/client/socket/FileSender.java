package client.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSender extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(FileSender.class);
	private Socket socket;
	private String filename;
	private FileInputStream fis;
	private OutputStream os;
	private InputStream is;
	private BufferedOutputStream bos;
	private BufferedInputStream bis;
	private int fileSize;
	private String result;
	
	public FileSender(Socket socket, String filestr) {
		logger.debug("-- File Sender Start --");
		this.socket = socket;
		this.filename = filestr;
		
		try {
			this.os = socket.getOutputStream();
			bos = new BufferedOutputStream(os);
			this.is = socket.getInputStream();
			bis = new BufferedInputStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			logger.debug("-- File Sender End --");
		}
	}
	
	public boolean sendFileSize(String fileName) throws IOException {
		logger.debug("-- FileSender 1 --");
		File imageFile = new File(fileName);
		fileSize = (int) imageFile.length() * 100;
		fis = new FileInputStream(imageFile);
		bos.write(Integer.toString(fileSize).getBytes());
		bos.flush();
		logger.debug("send file size : " + fileSize);
		return true;
	}
	
	public void sendImage(int fileSize) throws IOException {
		logger.debug("-- FileSender 2 --");
		byte[] data = new byte[(int) (fileSize)];
		bos.write(data, 0, fis.read(data));
		logger.debug("send image ...");
		bos.flush();
		fis.close();
	}
	
	public String receiveData(int buffer_size) throws IOException {
		logger.debug("-- FileSender 3 --");
		byte[] tmp = new byte[buffer_size];
		int zz = bis.read(tmp);
		logger.debug("server : " + new String(tmp, 0, zz));
		return new String(tmp, 0, zz);
	}
	
	public String getResult() {
		logger.debug("-- FileSender 4 --");
		return result;
	}
	
	public void close() {
		logger.debug("-- FileSender 5 --");
		try {
			bos.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		logger.debug("-- FileSender 6 --");
		// super.run();
		try {
			sendFileSize(filename);
			receiveData(100);
			sendImage(fileSize);
			result = receiveData(100);
			// logger.debug("result: " + result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
}
