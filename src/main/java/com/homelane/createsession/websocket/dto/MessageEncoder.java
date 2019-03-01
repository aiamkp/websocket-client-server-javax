package com.homelane.createsession.websocket.dto;


import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.homelane.createsession.websocket.util.JsonUtil;

public class MessageEncoder implements Encoder.Text<Message> {

	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public String encode(Message object) throws EncodeException {
		// TODO Auto-generated method stub
        return JsonUtil.formatMessage(object.getContent(), object.getSender());
	}

   
}
