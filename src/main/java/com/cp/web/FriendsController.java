package com.cp.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	//--> 网页接口
	@RequestMapping("/friends/list")
	public String goToFriends(int userid, Map<String, Object> context){
		context.put("users", userService.getFriends(userid));
		return "/friend";
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
	
}
