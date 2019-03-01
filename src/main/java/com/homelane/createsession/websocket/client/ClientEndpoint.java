package com.homelane.createsession.websocket.client;

import static java.lang.String.format;


import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import com.homelane.createsession.websocket.dto.Message;
import com.homelane.createsession.websocket.dto.MessageDecoder;
import com.homelane.createsession.websocket.dto.MessageEncoder;

import java.text.SimpleDateFormat;

@javax.websocket.ClientEndpoint(encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ClientEndpoint {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println(format("Connection established. session id: %s", session.getId()));
    }

    @OnMessage
    public void onMessage(Message message) {
        System.out.println(format("[%s:%s] %s", simpleDateFormat.format(message.getReceived()), message.getSender(), message.getContent()));
    }

}
