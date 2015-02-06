package com.cp.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
	@RequestMapping(value = "/find_user")
	@ResponseBody
	public Map<String, Object> searchUser(int userid) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("ret", 1);
		modelMap.put("info", userService.findUserByUserid(userid));
		return modelMap;
	}
	
	// 模糊搜索人
	@RequestMapping(value = "/find_users")
	@ResponseBody
	public Map<String, Object> searchUsers(String nickname, int userid) throws UnsupportedEncodingException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("ret", 1);
		modelMap.put("info", userService.findUsersBy(URLDecoder.decode(nickname, "utf-8"), userid));
		return modelMap;
	}
	
	/**
	 * 多功能更搜索
	 * 
	 * @param method
	 *            [FIND_USERS]
	 * @param request
	 * @return Map info
	 */
	@RequestMapping(value = "/search")
	@ResponseBody
	public Map<String, Object> search(String method, int userid,
			HttpServletRequest request) {
		Map<String, Object> params = Servlets.getBodyWihtHttpRequest(request);

		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(method) && method.equals("FIND_USERS")) {
			String query = Servlets.ignoreStringNull(params.get("query"));
			modelMap.put("ret", 1);
			modelMap.put("info", userService.findUsersBy(query, userid));
		} else {
			modelMap.put("ret", -1);
			modelMap.put("info", new Object[] {});
		}
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
