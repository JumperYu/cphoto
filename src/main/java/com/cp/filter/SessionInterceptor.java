package com.cp.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cp.constant.MessageBox;
import com.cp.entity.UserAgent;
import com.cp.utils.JsonResponseUtil;
import com.cp.web.Servlets;

/**
 * 
 * 
 * @author zengxm 2014年12月11日
 * 
 *         session拦截, 如果发现session不存在, 则返回需登录, 已登录则刷新session
 *         
 * @update 2015-02-05
 * 
 * 		    判断agent, 如果来源于web页面则redirect; 否则返回ret非登陆态
 * 
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = LoggerFactory
			.getLogger(SessionInterceptor.class);

	// 定义忽略uri
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
		
		// 迭代查找是否为需要忽略uri 后期改成equals全量匹配
		for (String ignore : IGNORE_URI) {
			if (uri.contains("v2_1") && uri.endsWith(ignore)) {
				flag = true;// -->> set true // :)
				break;
			}// -->> End of if
		}// -->> End of for
		if (!flag) {// -->> 如果不匹配忽略数组
			HttpSession session = request.getSession(false);
			String userid = request.getParameter("userid");
			if (session != null) {
				//String sessionid = session.getId();
				Object session_userid = session.getAttribute("userid");
				if (session_userid != null && session_userid instanceof String
						&& session_userid.equals(userid)) {
					flag = true;
				} else {
					log.error(String.format("request uri:%s,userid not found",
							uri));
				}
			} else {
				log.error(String
						.format("request uri:%s,session not found", uri));
			}// -->> End of session is not null
		}// -->> End of if flag
		
		// 最终决定是否通关 如果请求来自webpage则redirect
		if(!flag) {
			Servlets.printHeaderWithHttpRequest(request);
			if(Servlets.whereDoYouCameFrom(request).equals(UserAgent.WebPageKey)) {
				response.sendRedirect("/static/");
			} else {
				response.getWriter().write(
					JsonResponseUtil.formate(
							MessageBox.Message.CP_SESSION_FAIL.getRet(),
							MessageBox.Message.CP_SESSION_FAIL.getMsg()));
			}
		}
		return flag;// -->> 如果return false 整个流程就结束了
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
}