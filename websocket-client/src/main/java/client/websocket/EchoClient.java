package client.websocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;
 
 
public class EchoClient
{
    /**
     * The echo server on websocket.org.
     */
    private static final String SERVER = "ws://localhost:5000/chat";
 
    /**
     * The timeout value in milliseconds for socket connection.
     */
    private static final int TIMEOUT = 5000;
 
    /**
     * Connect to the server.
     */
    public static WebSocket connect() throws IOException, WebSocketException {
        System.out.println("Start get data from upbit");
        return new WebSocketFactory()
            .setConnectionTimeout(TIMEOUT)
            .createSocket(SERVER)
            .addListener(new WebSocketAdapter() {
                // binary message arrived from the server
                public void onBinaryMessage(WebSocket websocket, byte[] binary) {
                    String str = new String(binary);
                    System.out.println("str: " + str);
                }
                // A text message arrived from the server.
                public void onTextMessage(WebSocket websocket, String message) {
                    System.out.println("msg" + message);
                }
            })
            .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
            .connect();
    }
 
    /**
     * Wrap the standard input with BufferedReader.
     */
    public static BufferedReader getInput() throws IOException {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}

