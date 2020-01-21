package client.controller;

import java.io.BufferedReader;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neovisionaries.ws.client.WebSocket;

import client.websocket.EchoClient;

@Controller
public class WebsocketController extends EchoClient {
    /**
     * The entry point of this command line application.
     */
	@RequestMapping(value="/socket", method=RequestMethod.GET)
    public void main() throws Exception {
        // Connect to the echo server.
        WebSocket ws = connect();
        // The standard input via BufferedReader.
        BufferedReader in = getInput();
        // A text read from the standard input.
        String text;
        // Read lines until "exit" is entered.
        while ((text = in.readLine()) != null) {
            // If the input string is "exit".
            if (text.equals("exit")) {
                // Finish this application.
                break;
            }
            // Send the text to the server.
            ws.sendText(text);
        }
        // Close the WebSocket.
        ws.disconnect();
    }
}
