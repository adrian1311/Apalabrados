package edu.uclm.esi.apalabreitor.apalabreitor.web.ws;

import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uclm.esi.apalabreitor.apalabreitor.model.*;
import edu.uclm.esi.apalabreitor.apalabreitor.web.controllers.Manager;

@Component
public class WSServer extends TextWebSocketHandler {
	private static ConcurrentHashMap<String, WebSocketSession> sessionsById=new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String, WebSocketSession> sessionsByUser=new ConcurrentHashMap<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessionsById.put(session.getId(), session);
		/* Player player = (Player) session.getAttributes().get("player");
		String userName=player.getUserName();
		if (sessionsByUser.get(userName)!=null) 
			sessionsByUser.remove(userName);
		sessionsByUser.put(userName, session);
		System.out.println(userName);*/
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println(message.getPayload());
		JSONObject jso=new JSONObject(message.getPayload());
		if (jso.getString("TYPE").equals("TYPE")) {

		}
	}

	private void sendError(WebSocketSession session, String message) throws Exception {
		JSONObject jso = new JSONObject();
		jso.put("TYPE", "ERROR");
		jso.put("MESSAGE", message);
		WebSocketMessage<?> wsMessage=new TextMessage(jso.toString());
		session.sendMessage(wsMessage);
	}
}
