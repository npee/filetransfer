package client.socket;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class HandlerFile {

	private static final Logger logger = LoggerFactory.getLogger(HandlerFile.class);
	private MultipartHttpServletRequest multipartRequest;
	private String filePath;
	private Map<String ,List<String>> fileNames;
	private String oldName;
	private HttpServletResponse resp;
	private byte[] fileByte;
	
	String fileFullPath;
	
	public HandlerFile(MultipartHttpServletRequest multipartRequest, String filePath) {
		this.multipartRequest = multipartRequest;
		this.filePath = filePath;
		fileNames = new HashMap<String, List<String>>();
	}
	
	public HandlerFile(HttpServletResponse resp, String filePath, String saveName, String oldName) {
		this.resp = resp;
		this.filePath = filePath + "/" + saveName;
		this.oldName = oldName;
	}
	
	public HandlerFile(String filePath, String saveName) {
		this.filePath = filePath + "/" + saveName;
	}
	
	public Map<String, List<String>> getUploadFileName() {
		logger.debug("-- HandlerFile 1 --");
		upload();
		return fileNames;
	}
	
	public byte[] getDownloadFileByte() {
		logger.debug("-- HandlerFile 2 --");
		download();
		return fileByte;
	}
	
	public void deleteFileExecute() {
		logger.debug("-- HandlerFile 3 --");
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}
	
	public String getFileFullPath() {
		logger.debug("-- HandlerFile 4 --");
		return fileFullPath;
	}
	
	private void upload() {
		logger.debug("-- Upload --");
		Iterator<String> itr = multipartRequest.getFileNames();
		List<String> oldNames = new ArrayList<String>();
		List<String> saveNames = new ArrayList<String>();
		StringBuffer sb = null;
		while(itr.hasNext()) {
			MultipartFile mpf = multipartRequest.getFile(itr.next());
			sb = new StringBuffer();
			String oldFileName = mpf.getOriginalFilename();
			String saveFileName = sb.append(new SimpleDateFormat("yyyyMMddhhmmss").format(System.currentTimeMillis()))
					.append(UUID.randomUUID().toString()).append(oldFileName.substring(oldFileName.lastIndexOf("."))).toString();
			fileFullPath = filePath + "/" + saveFileName;
			
			try {
				mpf.transferTo(new File(fileFullPath));
				oldNames.add(oldFileName);
				saveNames.add(saveFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		fileNames.put("oldNames", oldNames);
		fileNames.put("saveNames", saveNames);
		logger.debug("-- Upload End --");
	}
	
	private void download() {
		logger.debug("-- Download --");
		try {
			fileByte = FileUtils.readFileToByteArray(new File(filePath));
			resp.setContentType("application/octet-stream");
			resp.setContentLength(fileByte.length);
			resp.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(oldName, "UTF-8") + "\";");
			resp.setHeader("Content-Transfer-Encoding", "binary");
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("-- Download End --");
	}
}
