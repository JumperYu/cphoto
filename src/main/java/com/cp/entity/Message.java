package com.cp.entity;

import java.util.HashMap;

/**
 * 推送消息模型
 * 
 * @author zengxm 2014-12-20
 *
 */
public class Message {

	private int msgid;
	private int eventid;
	private HashMap<String, Object> request_user = new HashMap<String, Object>();

	public int getMsgid() {
		return msgid;
	}

	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}

	public int getEventid() {
		return eventid;
	}

	public void setEventid(int eventid) {
		this.eventid = eventid;
	}

	public HashMap<String, Object> getRequest_user() {
		return request_user;
	}

	public void setRequest_user(HashMap<String, Object> request_user) {
		this.request_user = request_user;
	}

}
