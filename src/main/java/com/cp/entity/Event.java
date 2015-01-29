package com.cp.entity;

/**
 *  事件模型
 * 
 * @author zengxm 2014-12-21
 *
 */
public class Event {

	private int eventid;	// 事件
	private String eventName;// 事件名
	private String eventDesc;// 事件描述
	private int count; //计数
	
	public int getEventid() {
		return eventid;
	}
	public void setEventid(int eventid) {
		this.eventid = eventid;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventDesc() {
		return eventDesc;
	}
	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
