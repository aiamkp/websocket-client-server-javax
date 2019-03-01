package com.homelane.createsession.websocket.client;


import java.net.URI;
import java.util.Scanner;
import javax.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;

import com.homelane.createsession.websocket.util.JsonUtil;

public class Client {

    public static final String SERVER = "ws://localhost:8025/ws/createsession";

    public static void main(String[] args) throws Exception {
        ClientManager client = ClientManager.createClient();
        String message;

        // connect to server
        Scanner scanner = new Scanner(System.in);
        Session session = client.connectToServer(ClientEndpoint.class, new URI(SERVER));
        System.out.println("Connected to server");
        System.out.println("Logged in sessionId: " + session.getId());
        String user = null;
        do {
            message = scanner.nextLine();
            session.getBasicRemote().sendText(JsonUtil.formatMessage(message, user));
        } while (!message.equalsIgnoreCase("quit"));
    }

}
