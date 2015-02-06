package com.cp.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cp.constant.PushMessage;
import com.cp.entity.Event;
import com.cp.entity.Message;
import com.cp.msg.MessageService;
import com.cp.user.service.UserService;

/**
 * 
 * 消息功能
 * 
 * @author zengxm 2015年1月29日
 *
 */
@Controller
@RequestMapping("/v2_1")
public class MessageController {

	@Resource
	private MessageService messageService;
	
	@Resource
	private UserService userService;

	// --> 网页接口

	// 消息具现化
	@RequestMapping(value = "/message/list")
	public String msgList(int eventid, int userid, Map<String, Object> context) {
		List<Message> msgs = messageService.getMsg(eventid, userid);
		context.put("msgs", msgs);
		return "/msgs";
	}

	// --> 数据接口
	
	@RequestMapping(value = "/long_poll")
	@ResponseBody
	public Map<String, Object> longPoll(int userid) {
		List<Event> events = messageService.getEventMsg(userid);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (events != null && events.size() > 0) {
			modelMap.put("ret", 1);
			modelMap.put("events", events);
		}
		return modelMap;
	}
	
	// 轮询接口
	@RequestMapping(value = "/req_msg")
	@ResponseBody
	public Map<String, Object> reqMsg(int eventid, int userid) {

		Map<String, Object> modelMap = new HashMap<String, Object>();

		List<Message> msgs = messageService.getMsg(eventid, userid);
		if (msgs != null && msgs.size() > 0) {
			modelMap.put("ret", 1);
			modelMap.put("msgs", msgs);
			modelMap.put("eventid", eventid);
		} else {
			modelMap.put("ret", 0);
			modelMap.put("msgs", null);
			modelMap.put("eventid", eventid);
		}
		return modelMap;
	}

	/**
	 * 消息确认
	 * 
	 * @param msgid
	 * @param userid
	 * @param state  1 同意 2 拒绝
	 * @return
	 */
	@RequestMapping(value = "/confirm_msg")
	@ResponseBody
	public Map<String, Object> confirmMsg(int msgid, int userid, int state) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int ret = messageService.updateMsg(msgid, userid, state);
		if (ret > 0 && state == PushMessage.CONFIRM_STATE) {
			userService.buildFriendShip(msgid, userid);
			modelMap.put("ret", 1);
			modelMap.put("msg", "add friend success");
		} else {
			modelMap.put("ret", 0);
			modelMap.put("msg", "add friend fail");
		}
		return modelMap;
	}

}
