package com.cp.ws;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

/**
 * 
 * tomcat 7 websocket
 * 
 * @author zengxm 2015年3月13日
 *
 */
public class SocketServer extends WebSocketServlet  {

	private static final long serialVersionUID = 1L;
	
	private final AtomicInteger connectionIds = new AtomicInteger(0);
	
	@Override
	protected StreamInbound createWebSocketInbound(String arg0) {
		return new ChatWebSocket(connectionIds.getAndIncrement(), UUID.randomUUID().toString());
	}
}
