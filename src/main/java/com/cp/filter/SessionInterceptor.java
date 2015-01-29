package com.cp.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cp.cache.SpyClient;
import com.cp.constant.MessageBox;
import com.cp.utils.JsonResponseUtil;

/**
 * 
 * 
 * @author zengxm 2014年12月11日
 * 
 *         session拦截, 如果发现session不存在, 则返回需登录, 已登录则刷新session
 * 
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = LoggerFactory
			.getLogger(SessionInterceptor.class);

	private static final String INDEX_URI = "/index";
	private static final String LOGIN_URI = "/login";
	private static final String REGISTER_URI = "/register";
	private static final String TOKEN_URI = "/token";
	private static final String[] IGNORE_URI = { INDEX_URI, LOGIN_URI,
			REGISTER_URI, TOKEN_URI };

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		boolean flag = false;
		String uri = request.getRequestURI();
		log.debug(String.format("request uri:%s", uri));
		for (String ignore : IGNORE_URI) {
			// 后期改成equals全量匹配
			if (uri.contains("v2_1") && uri.endsWith(ignore)) {
				flag = true;// -->> set true, only true can do next thing. hehe
							// :)
				break;
			}// -->> End of if
		}// -->> End of for
		if (!flag) {// -->>
			HttpSession session = request.getSession(false);
			String userid = request.getParameter("userid");
			if (session != null) {
				String sessionid = session.getId();
				Object session_userid = session.getAttribute("userid");
				if (session_userid != null && session_userid instanceof String
						&& session_userid.equals(userid)) {
					log.debug("session id: " + sessionid);
					log.debug("from memcached:" + SpyClient.get(userid));
					log.debug("session expire_in: "
							+ (session.getMaxInactiveInterval() - (System
									.currentTimeMillis() - session
									.getLastAccessedTime()) / 1000));
					flag = true;
				} else {
					log.debug(String.format("request uri:%s,userid not found",
							uri));
				}
			} else {
				log.debug(String
						.format("request uri:%s,session not found", uri));
				response.getWriter().write(
						JsonResponseUtil.formate(
								MessageBox.Message.CP_SESSION_FAIL.getRet(),
								MessageBox.Message.CP_SESSION_FAIL.getMsg()));
			}
		}// -->> End of if
		return flag;// -->> 如果return false 整个流程就结束了
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
}