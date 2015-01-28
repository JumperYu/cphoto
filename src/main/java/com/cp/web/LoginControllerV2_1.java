package com.cp.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cp.cache.SpyClient;
import com.cp.constant.MessageBox;
import com.cp.constant.PushMessage;
import com.cp.entity.Event;
import com.cp.entity.Message;
import com.cp.msg.MessageService;
import com.cp.photo.service.PhotoService;
import com.cp.user.service.UserService;

/**
 * 
 * @author zengxm 2014年11月05日
 * 
 *         V2.1.0
 * 
 * @edit at 2014-11-24 模仿OAuth2.0协议
 * @edit at @date 2014-11-29 完善好友功能
 * 
 */
@Controller
@RequestMapping("/v2_1")
public class LoginControllerV2_1 {

	private static final Logger log = LoggerFactory
			.getLogger(LoginControllerV2_1.class);

	//private static String uploadPath = "/pics/";
	// private static String uploadPath = "d:/pics/";

	@Resource
	private UserService userService;

	@Resource
	private PhotoService photoService;

	@Resource
	private MessageService messageService;

	// 注册接口
	@RequestMapping("/register")
	@ResponseBody
	public Map<Object, Object> register(HttpServletRequest request) {

		Map<String, Object> params = Servlets.getParametersStartingWith(
				request, "");
		log.debug("register request: " + params.toString());

		String account = params.get("account").toString();
		String password = params.get("password").toString();
		String nickname = params.get("nickname").toString();
		String gender = params.get("gender").toString();
		String age = params.get("age").toString();
		String email = params.get("email").toString();
		String telphone = params.get("telphone").toString();

		String cphoto = ""
				+ (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + 100);

		boolean isExits = userService.isAccountExits(account);
		Map<Object, Object> modelMap = new HashMap<Object, Object>();
		if (!isExits) {
			userService.register(cphoto, account, password, nickname, gender,
					age, email, telphone);
			modelMap.put("ret", WebResultConstant.REGISTER_SUCCESS_RET);
			modelMap.put("msg", WebResultConstant.REGISTER_SUCCESS_MSG);
			Map<Object, Object> usr = new HashMap<Object, Object>();
			usr.put("cphoto", cphoto); // 兼容以前
			usr.put("userid", cphoto); //
			usr.put("nickname", nickname);
			usr.put("gender", gender);
			usr.put("age", age);
			usr.put("email", email);
			usr.put("telphone", telphone);
			modelMap.put("usr", usr);
			modelMap.put("success", true);
		} else {
			modelMap.put("ret", WebResultConstant.REGISTER_FAIL_RET);
			modelMap.put("msg", WebResultConstant.REGISTER_FAIL_MSG);
			modelMap.put("usr", new Object[] {});
			modelMap.put("success", false);
		}// -->> End if

		return modelMap;
	}

	// 登录接口
	@RequestMapping("/login")
	@ResponseBody
	public Map<Object, Object> login(
			@RequestParam(required = true) String account,
			@RequestParam(required = true) String password,
			HttpServletRequest request) {
		Servlets.printHeaderWithHttpRequest(request);
		Servlets.printBodyWihtHttpRequest(request);

		HttpSession session = request.getSession(true);// new session every time
		session.setMaxInactiveInterval(60 * 30);// 存活30分钟

		boolean isExits = userService.validate(account, password);

		Map<Object, Object> modelMap = new HashMap<Object, Object>();
		if (isExits) {
			Map<String, Object> accountMap = userService.login(account,
					password);
			modelMap.put("ret", MessageBox.Message.CP_REQ_SUCS.getRet());
			modelMap.put("msg", MessageBox.Message.CP_REQ_SUCS.getMsg());
			modelMap.put("account", accountMap);
			modelMap.put("success", true);// ext form need

			// --> put token and session in result
			String token = UUID.randomUUID().toString();
			Map<Object, Object> access_token = new HashMap<Object, Object>();
			access_token.put("token", token);
			access_token.put("expires_in", SpyClient.DEFAULT_SECONDS);
			Map<Object, Object> sessionMap = new HashMap<Object, Object>();
			sessionMap.put("sessionid", session.getId());
			sessionMap.put("expires_in", session.getMaxInactiveInterval());// 失效时间:秒

			modelMap.put("access_token", access_token);
			modelMap.put("session", sessionMap);

			// --> put userid:sessionid in memcached
			SpyClient.set(accountMap.get("userid").toString(), token);
			session.setAttribute("userid", accountMap.get("userid").toString());

		} else {
			modelMap.put("ret", MessageBox.Message.CP_LOGIN_FAIL.getRet());
			modelMap.put("msg", MessageBox.Message.CP_LOGIN_FAIL.getMsg());
			modelMap.put("account", new HashMap<Object, Object>());
			modelMap.put("success", false); // ext form need
		}
		return modelMap;
	}

	// access_token换session
	@RequestMapping("/token")
	@ResponseBody
	public Map<Object, Object> token(HttpServletRequest request) {
		Map<String, Object> params = Servlets.getParametersStartingWith(
				request, "");
		log.debug(String.format("request %s, params:%s",
				request.getRequestURI(), params.toString()));

		Object token = params.get("token");
		Object userid = params.get("userid");

		HttpSession session = request.getSession(true);// new session every time
		session.setMaxInactiveInterval(60 * 30);// 存活30分钟

		Map<Object, Object> modelMap = new HashMap<Object, Object>();
		if (token != null && userid != null) {
			Object cached_token = SpyClient.get(userid.toString());
			if (cached_token != null && token.equals(cached_token.toString())) {
				modelMap.put("ret", MessageBox.Message.CP_REQ_SUCS.getRet());
				modelMap.put("msg", MessageBox.Message.CP_REQ_SUCS.getMsg());

				// --> put token and session in result
				Map<Object, Object> access_token = new HashMap<Object, Object>();
				access_token.put("token", token);
				access_token.put("expires_in", SpyClient.DEFAULT_SECONDS);
				Map<Object, Object> sessionMap = new HashMap<Object, Object>();
				sessionMap.put("sessionid", session.getId());
				sessionMap.put("expires_in", session.getMaxInactiveInterval());// 失效时间:秒

				modelMap.put("access_token", access_token);
				modelMap.put("session", sessionMap);

				SpyClient.set(userid.toString(), token);
				session.setAttribute("userid", userid);

				modelMap.put("success", true);// ext form need

				return modelMap;

			}
		}
		// --> else
		modelMap.put("ret", MessageBox.Message.CP_LOGIN_FAIL.getRet());
		modelMap.put("msg", MessageBox.Message.CP_LOGIN_FAIL.getMsg());
		modelMap.put("success", false); // ext form need

		return modelMap;
	}

	// 上传
	@RequestMapping(value = "/publish", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> publish(@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("cphoto") String cphoto,
			@RequestParam("file") MultipartFile file) {

		log.debug(String.format(
				"someone publish, title: %s, content: %s, filename: %s", title,
				content, file.getOriginalFilename()));

		String msg = "";
		int ret = -1;

		String uploadPath = "/pics/";
		// String uploadPath = "d:/pics/";
		String filePath = uploadPath
				+ (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
		try {
			InputStream in = file.getInputStream();
			FileOutputStream out = new FileOutputStream(filePath);
			IOUtils.copy(in, out);
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
			photoService.savePic(file.getOriginalFilename(), filePath, title,
					content, cphoto);
			msg = WebResultConstant.PUBLISH_SUCCESS_MSG;
			ret = WebResultConstant.PUBLISH_SUCCESS_RET;
		} catch (IOException e) {
			log.error(e.getMessage());
			msg = WebResultConstant.PUBLISH_FAIL_MSG;
			ret = WebResultConstant.PUBLISH_FAIL_RET;
		}
		// 需要修改整的错误
		Map<Object, Object> modelMap = new HashMap<Object, Object>();
		modelMap.put("ret", ret);
		modelMap.put("msg", msg);
		return modelMap;
	}

	// 返回所有用户的图片
	@RequestMapping(value = "/list_pics")
	@ResponseBody
	public Map<Object, Object> listPics(HttpServletRequest request) {

		String userid = request.getParameter("userid");// 新版本
		String cphoto = request.getParameter("cphoto");// @Deprecated 兼容v2版本

		if (StringUtils.isEmpty(userid)) {
			userid = cphoto;
		}

		Map<Object, Object> modelMap = new HashMap<Object, Object>();
		modelMap.put("pics", photoService.findAllPics(userid));
		return modelMap;
	}

	// 请求添加
	@RequestMapping(value = "/add_friend")
	@ResponseBody
	public Map<String, Object> addFriend(int userid, int tar_userid,
			String remark) {
		int ret = messageService.addPushMsg(userid, tar_userid, remark,
				PushMessage.EVENT_FOR_FRIEND_ASKING);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("ret", ret);
		modelMap.put("msg", "added msg.");
		return modelMap;
	}

	// 搜索ren
	@RequestMapping(value = "/find_user")
	@ResponseBody
	public Map<String, Object> searchUser(String userid) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("ret", 1);
		modelMap.put("info", userService.findUserByUserid(userid));
		return modelMap;
	}

	// 搜索人
	@RequestMapping(value = "/find_users")
	@ResponseBody
	public Map<String, Object> searchUsers(String nickname, int userid) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("ret", 1);
		modelMap.put("info", userService.findUsersBy(nickname, userid));
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
		Map<String, Object> params = Servlets.getParametersStartingWith(
				request, "");
		log.debug(String.format("request %s, params:%s",
				request.getRequestURI(), params.toString()));

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

	// 搜索好友
	@RequestMapping("/find_friends")
	@ResponseBody
	public Map<String, Object> searchFriends(int userid) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("ret", 1);
		modelMap.put("friends", userService.getFriends(userid));
		return modelMap;
	}

	// 搜索好友
	@RequestMapping("/friends")
	@ResponseBody
	public List<List<Object>> searchFriendsArray(
			@RequestParam(required = true) String userid) {
		return userService.getFriendsArry(userid);
	}

	@RequestMapping(value = "/long_poll")
	@ResponseBody
	public Map<String, Object> longPoll(int userid) {
		List<Event> events = messageService.getEventMsg(userid);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (events != null && events.size() > 0) {
			modelMap.put("ret", 1);
			modelMap.put("events", events);
		}/*
		 * else { modelMap.put("ret", 0); modelMap.put("events", null); }
		 */
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
	 * @param state
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