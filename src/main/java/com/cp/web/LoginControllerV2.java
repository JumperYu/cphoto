package com.cp.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.cp.constant.PushMessage;
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
@RequestMapping("/v2")
public class LoginControllerV2 {

	private static final Logger log = LoggerFactory
			.getLogger(LoginControllerV2.class);

	@Resource
	private UserService userService;

	@Resource
	private PhotoService photoService;

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

		HttpSession session = request.getSession();
		// session.setMaxInactiveInterval(1);
		log.debug("sessionid: " + session.getId());

		SpyClient.set("sessionid", session.getId());

		if (session != null) {
			Object cphoto = session.getAttribute("cphoto");
			if (cphoto != null) {
				log.debug("session login is : " + cphoto);
			} else {
				session.setAttribute("cphoto", account);
			}
		}

		Map<String, Object> params = Servlets.getParametersStartingWith(
				request, "");
		log.debug("login request: " + params.toString());

		boolean isExits = userService.validate(account, password);

		Map<Object, Object> modelMap = new HashMap<Object, Object>();
		if (isExits) {
			Map<String, Object> accountMap = userService.login(account,
					password);
			modelMap.put("ret", WebResultConstant.LOGIN_SUCCESS_RET);
			modelMap.put("msg", WebResultConstant.LOGIN_SUCCESS_MSG);
			modelMap.put("acccount", accountMap);
			modelMap.put("success", true);// ext form need

			// --
			session.setAttribute("userid", account);

		} else {
			modelMap.put("ret", WebResultConstant.LOGIN_FAIL_RET);
			modelMap.put("msg", WebResultConstant.LOGIN_FAIL_MSG);
			modelMap.put("acccount", new HashMap<Object, Object>());
			modelMap.put("success", false); // ext form need
		}
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
	public String reqForFriend(String userid, String tar_userid,
			String groupId, String groupName, String msg) {
		userService.addPushMsg(userid, tar_userid,
				PushMessage.EVENT_FOR_FRIEND_ASKING);
		return "success";
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

	// 搜索ren
	@RequestMapping(value = "/find_users")
	@ResponseBody
	public Map<String, Object> searchUsers(String nickname, int userid) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("ret", 1);
		modelMap.put("info", userService.findUsersBy(nickname, userid));
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

	@RequestMapping(value = "/req_msg")
	@ResponseBody
	public Map<String, Object> reqMsg(int userid) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("ret", 1);
		// modelMap.put("info", userService.getPushMsg(userid));
		return modelMap;
	}

	@RequestMapping(value = "/confirm_msg")
	@ResponseBody
	public String confirmMsg(String msgid, String userid, String state) {
		if (StringUtils.isBlank(state)) {
			state = PushMessage.CANCEL_STATE + "";
		}
		userService.updatePushMsg(msgid, userid, state);
		return PushMessage.MESSAGE_FOR_CONFIRM_RET_SUC;
	}
}
