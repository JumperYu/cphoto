package com.cp.web;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cp.cache.SpyClient;
import com.cp.constant.MessageBox;
import com.cp.entity.UserAccount;
import com.cp.msg.MessageService;
import com.cp.photo.service.PhotoService;
import com.cp.user.service.UserService;

/**
 * 
 * 登陆、注册控制层
 * 
 * @author zengxm 2014年11月05日
 * 
 *         V2.2.0
 * 
 * @edit at 2014-11-24 实现session通用化
 * @edit at @date 2014-11-29 完善好友功能
 * @edit at @date 2015-02-06 完善模块划分
 * 
 */
@Controller
@RequestMapping("/v2_1")
public class LoginController {

	private static final Logger log = LoggerFactory
			.getLogger(LoginController.class);

	@Resource
	private UserService userService;

	@Resource
	private PhotoService photoService;

	@Resource
	private MessageService messageService;

	// 页面接口
	@RequestMapping("/index")
	public String index(Integer userid, Map<String, Object> context, HttpServletRequest request) {
		if(userid == null) {
			HttpSession session = request.getSession(false);
			if(session != null) {
				Object session_userid = session.getAttribute("userid");
				if(session_userid != null && session_userid instanceof String) {
					userid = Integer.valueOf(session_userid.toString());
				}
			}
		}
		if(userid != null) {
			context.putAll(userService.findUserByUserid(userid));
		} else {
			log.error("request index page with no correct identity");
		}
		return "index";
	}

	// 注册接口
	@RequestMapping("/register")
	@ResponseBody
	public Map<Object, Object> register(HttpServletRequest request, UserAccount user) {

		Servlets.printBodyWihtHttpRequest(request);

		boolean isExits = userService.isAccountExits(user.getAccount());
		
		String userid = String.valueOf(TimeUnit.MICROSECONDS.toSeconds(System.currentTimeMillis()));
		user.setUserid(userid);
		
		Map<Object, Object> modelMap = new HashMap<Object, Object>();
		if (!isExits) {
			/*int ret = */
			userService.register(user);
			modelMap.put("ret", WebResultConstant.REGISTER_SUCCESS_RET);
			modelMap.put("msg", WebResultConstant.REGISTER_SUCCESS_MSG);
			modelMap.put("account", user);
			modelMap.put("success", true);
			
			// --> put userid:sessionid in memcached
			String token = UUID.randomUUID().toString();
			SpyClient.set(user.getUserid(), token);
			request.getSession().setAttribute("userid", user.getUserid());
		} else {
			modelMap.put("ret", WebResultConstant.REGISTER_FAIL_RET);
			modelMap.put("msg", WebResultConstant.REGISTER_FAIL_MSG);
			modelMap.put("account", new Object[] {});
			modelMap.put("success", false);
		}// -->> End if

		return modelMap;
	}

	// 登录接口
	@RequestMapping("/login")
	@ResponseBody
	public Map<Object, Object> login(HttpServletRequest request) {
		// 暂不做非空校验
		Map<String, Object> params = Servlets.getBodyWihtHttpRequest(request);
		String account = params.get("account").toString();
		String password = params.get("password").toString();

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

			// --> put token and session to client
			String token = UUID.randomUUID().toString();
			Map<Object, Object> access_token = new HashMap<Object, Object>();
			access_token.put("token", token);// 长时间持有token
			access_token.put("expires_in", SpyClient.DEFAULT_SECONDS);// 失效时间:秒
			Map<Object, Object> sessionMap = new HashMap<Object, Object>();
			sessionMap.put("sessionid", session.getId());// 短时间持有session
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
		}// end if account exists
		return modelMap;
	}

	// 当session失效后 可使用access_token换取新session
	@RequestMapping("/token")
	@ResponseBody
	public Map<Object, Object> token(HttpServletRequest request) {
		Map<String, Object> params = Servlets.getBodyWihtHttpRequest(request);

		Object token = params.get("token");
		Object userid = params.get("userid");

		HttpSession session = request.getSession(true);// new session every time
		session.setMaxInactiveInterval(60 * 30);// 存活30分钟

		Map<Object, Object> modelMap = new HashMap<Object, Object>();
		if (token != null && userid != null) {
			Object cached_token = SpyClient.get(userid.toString());
			if (cached_token != null && cached_token instanceof String
					&& token.equals(cached_token.toString())) {
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
		}// end if
		modelMap.put("ret", MessageBox.Message.CP_LOGIN_FAIL.getRet());
		modelMap.put("msg", MessageBox.Message.CP_LOGIN_FAIL.getMsg());
		modelMap.put("success", false); // ext form need

		return modelMap;
	}

	// 上传
/*	@RequestMapping(value = "/publish", method = RequestMethod.POST)
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
	}*/

/*	// 返回所有用户的图片
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
	}*/

}
