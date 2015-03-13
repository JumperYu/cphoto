package com.cp.ws;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

public class ChatWebSocket extends MessageInbound {

	private String WS_NAME;
//	private final String FORMAT = "%s : %s";
	private final String PREFIX = "ws_";
	private String sessionId = "";

	public ChatWebSocket(int id, String sessionId) {
		this.WS_NAME = PREFIX + id;
		this.sessionId = sessionId;
	}

	// 二进制消息
	@Override
	protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
		
	}

	// 文本消息
	@Override
	protected void onTextMessage(CharBuffer charBuffer) throws IOException {
		System.out.println(charBuffer);
	}

	@Override
	protected void onClose(int status) {
		System.out.println("连接关闭");
		super.onClose(status);
	}

	@Override
	protected void onOpen(WsOutbound outbound) {
		super.onOpen(outbound);
		System.out.println(WS_NAME + " 建立连接");
		System.out.println("sessionid:" + this.sessionId);
	}
}
