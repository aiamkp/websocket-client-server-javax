package com.homelane.createsession.websocket.dto;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;
import java.util.Date;

public class MessageDecoder implements Decoder.Text<Message> {

	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public Message decode(String s) throws DecodeException {
		// TODO Auto-generated method stub
        Message message = new Message();
        JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
        message.setContent(jsonObject.getString("message"));
        message.setSender(jsonObject.getString("sender"));
        message.setReceived(new Date());
        return message;	}

	public boolean willDecode(String s) {
		// TODO Auto-generated method stub
		return true;
	}

 

}
