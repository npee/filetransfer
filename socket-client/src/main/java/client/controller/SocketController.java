package client.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class SocketController {
	
	@RequestMapping("/socket")
	public String socketclient() {
		return "analysis";
	}

	@RequestMapping(value="/socket/upload", method=RequestMethod.POST)
	public void fileupload(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
		
	}
}
