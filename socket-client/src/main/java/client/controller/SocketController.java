package client.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import client.socket.Client;
import client.socket.HandlerFile;

@Controller
public class SocketController {
	
	@RequestMapping("/socket")
	public String socketclient(Model model) {
		model.addAttribute("serverTime");
		return "analysis";
	}

	@RequestMapping(value="/socket/upload", method=RequestMethod.POST)
	public void fileupload(MultipartHttpServletRequest multipartRequest, HttpServletResponse response, HttpServletRequest request) {
		System.err.println(multipartRequest.getParameter("temp"));
		String filePath = request.getSession().getServletContext().getRealPath("tmp");
		
		HandlerFile handlerFile = new HandlerFile(multipartRequest, filePath);
		
		Map<String, List<String>> fileNames = handlerFile.getUploadFileName();
		
		System.err.println("filename : " + fileNames.toString());
		String fileName = handlerFile.getFileFullPath();
		Client client = new Client(fileName);
		String result = client.getResult();
		String js;
		ServletOutputStream out;
		
		
		try {
			response.setContentType("text/html; charset=UTF-8");
			out = response.getOutputStream();
			
			if (result.equals("null") || result.equals("fall")) {
				js = "<script>history.back(); alert('Result : Error! Page Reload!');</script>";
			} else {
				js = "<script>alert('Result : " + result + "'); location.href='https://www.google.co.kr/search?q=" + result + "'</script>";
			}
			
			out.println(js);
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
