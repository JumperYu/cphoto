package com.cp.constant;

import com.cp.utils.JsonResponseUtil;

public class MessageBox {

	public enum Message {

		// -1 系统繁忙
		// 1 请求成功
		// 40001  登陆失败，或者获取access_token失败，或者access_token失效
		// 40002 access_token换取sessionid失败，或者sessionid失效
		// 40100 不合法的参数，或者缺少请求参数
		CP_SYS_BUSY(-1, "system busy"), CP_REQ_SUCS(1, "request success"), CP_LOGIN_FAIL(
				40001, "request access_token fail"), CP_SESSION_FAIL(40002,
				"request session fail"), CP_PARAMS_FAIL(40100,
				"params invalidat");

		private int ret;
		private String msg;

		private Message(int ret, String msg) {
			this.ret = ret;
			this.msg = msg;
		}

		// -->>
		public static String get(int ret) {
			for (Message m : Message.values()) {
				if (m.getRet() == ret) {
					return m.msg;
				}
			}
			return null;
		}

		public int getRet() {
			return ret;
		}
		
		public String getMsg() {
			return msg;
		}

		public void setRet(int ret) {
			this.ret = ret;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}

	public static void main(String[] args) {

		System.out.println(JsonResponseUtil.formate(MessageBox.Message.CP_SESSION_FAIL.getRet(), MessageBox.Message.CP_SESSION_FAIL.getMsg()));

	}
}
