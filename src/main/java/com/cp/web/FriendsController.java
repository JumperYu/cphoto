package com.cp.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cp.constant.PushMessage;
import com.cp.msg.MessageService;
import com.cp.user.service.UserService;

/**
 * 
 * 好友功能
 * 
 * @author zengxm 2015年1月29日
 *
 */
@Controller
@RequestMapping("/v2_1")
public class FriendsController {
	
	@Resource
	private UserService userService;
	@Resource
	private MessageService messageService;
	
	//--> 网页接口
	@RequestMapping("/friends/list")
	public String goToFriends(int userid, Map<String, Object> context){
		context.put("users", userService.getFriends(userid));
		return "friend";
	}
	
	@RequestMapping("/friends/add")
	public String goToAddFriend(int userid, Map<String, Object> context){
//		context.put("users", userService.getFriends(userid));
		return "friend_add";
	}
	
	//--> 数据接口
	
	// 搜索好友
	@RequestMapping("/find_friends")
	@ResponseBody
	public Map<String, Object> searchFriends(int userid) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("ret", 1);
		modelMap.put("friends", userService.getFriends(userid));
		return modelMap;
	}
	
	// 搜索人
	@RequestMapping(value = "/find_users")
	@ResponseBody
	public Map<String, Object> searchUsers(String nickname, int userid) throws UnsupportedEncodingException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("ret", 1);
		modelMap.put("info", userService.findUsersBy(URLDecoder.decode(nickname, "utf-8"), userid));
		return modelMap;
	}
	
	// 请求添加
	@RequestMapping(value = "/add_friend")
	@ResponseBody
	public Map<String, Object> addFriend(int userid, int tar_userid,
			String remark) throws UnsupportedEncodingException {
		int ret = messageService.addPushMsg(userid, tar_userid, URLDecoder.decode(remark, "utf-8"),
				PushMessage.EVENT_FOR_FRIEND_ASKING);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("ret", ret);
		modelMap.put("msg", "added msg.");
		return modelMap;
	}
}
