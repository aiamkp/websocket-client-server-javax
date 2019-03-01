package com.homelane.createsession.websocket.server;

import static java.lang.String.format;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import com.homelane.createsession.websocket.dto.Message;
import com.homelane.createsession.websocket.dto.MessageDecoder;
import com.homelane.createsession.websocket.dto.MessageEncoder;


@javax.websocket.server.ServerEndpoint(value = "/createsession", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ServerEndpoint {

    static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session session) {
        System.out.println(format("%s joined the sesssion", session.getId()));
        peers.add(session);
    }

    @OnMessage
    public void onMessage(Message message, Session session) throws IOException, EncodeException {
        String user = (String) session.getUserProperties().get("user");
        if (user == null) {
            session.getUserProperties().put("user", message.getSender());
        }
        if ("quit".equalsIgnoreCase(message.getContent())) {
            session.close();
        }

        System.out.println(format("[%s:%s] %s", session.getId(), message.getReceived(), message.getContent()));

        //broadcast the message
        for (Session peer : peers) {
            if (!session.getId().equals(peer.getId())) { // do not resend the message to its sender
                peer.getBasicRemote().sendObject(message);
            }
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        System.out.println(format("%s left the session", session.getId()));
        peers.remove(session);
        //notify peers about leaving the chat room
        for (Session peer : peers) {
            Message chatMessage = new Message();
            chatMessage.setSender("Server");
            chatMessage.setContent(format("%s left the session", (String) session.getUserProperties().get("user")));
            chatMessage.setReceived(new Date());
            peer.getBasicRemote().sendObject(chatMessage);
        }
    }

}