package com.cp.form;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.cp.utils.HttpRequestUtil;

/**
 * 
 * @author zengxm 2014年9月18日
 * 
 * 
 *         测试表单接口
 */
public class TestForm {

	private static String sessionid = "";
	private static String userid = "";
	private static String release_domain = "http://121.199.67.141";
	private static String local_domain = "http://localhost";

	@Before
	public void testLogin() {
		String param = "account=xiaoyu&password=123";
		String body = HttpRequestUtil.httpRequest("GET", release_domain
				+ "/v2_1/login", param, null);
		JSONObject jsb = new JSONObject(body);
		sessionid = jsb.getJSONObject("session").getString("sessionid");
		userid = jsb.getJSONObject("account").get("userid").toString();
	}

	@Test
	public void testPublishSubject() throws IOException, InterruptedException {
		int count = 0;
		while (true) {
			String targetURL = null; // -- 指定URL

			File targetFile = null; // -- 指定上传文件

			String filepath = "E:\\logo6.png";
			String filename = "ok.jpg";

			targetURL = release_domain + "/v2_1/add_subject";
			// targetURL = "http://localhost:8080/cphoto/v2_1/add_subject";

			CloseableHttpClient httpclient = HttpClients.createDefault();

			try {

				targetFile = new File(filepath);

				FileBody bin = new FileBody(targetFile, ContentType.create(
						"multipart/form-data", Consts.UTF_8), filename);

				StringBody title = new StringBody("话题标语-" + count, ContentType.create(
						"text/plain", Consts.UTF_8));
				StringBody content = new StringBody("这是一个内容",
						ContentType.create("text/plain", Consts.UTF_8));
				StringBody cphoto = new StringBody(userid, ContentType.create(
						"text/plain", Consts.UTF_8));

				HttpPost httpPost = new HttpPost(targetURL);
				// 以浏览器兼容模式运行,防止文件名乱码
				HttpEntity reqEntity = MultipartEntityBuilder.create()
						.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
						.addPart("file", bin).addPart("title", title)
						.addPart("content", content).addPart("userid", cphoto)
						.setCharset(CharsetUtils.get("UTF-8")).build();

				httpPost.setEntity(reqEntity);
				httpPost.setHeader("cookie", "JSESSIONID=" + sessionid);

				CloseableHttpResponse response = httpclient.execute(httpPost);
				try {
					System.out.println(response.getStatusLine());
					HttpEntity resEntity = response.getEntity();
					if (resEntity != null) {
						System.out.println("Response: "
								+ resEntity.getContentLength());
					}
					EntityUtils.consume(resEntity);
				} finally {
					response.close();
				}

			} catch (Exception ex) {

				ex.printStackTrace();

			} finally {
				httpclient.close();
			}
			// 缓缓
			Thread.sleep(1000);
			count++;
//			break;
		}

	}
	
	@Test
	public void testPublishReply() throws IOException, InterruptedException {
		int count = 0;
		while (true) {
			String targetURL = null; // -- 指定URL

			File targetFile = null; // -- 指定上传文件

			String filepath = "E:\\logo6.png";
			String filename = "ok.jpg";

			targetURL = release_domain + "/v2_1/add_reply";

			CloseableHttpClient httpclient = HttpClients.createDefault();

			try {

				targetFile = new File(filepath);

				FileBody bin = new FileBody(targetFile, ContentType.create(
						"multipart/form-data", Consts.UTF_8), filename);

				StringBody title = new StringBody("话题标语-" + count, ContentType.create(
						"text/plain", Consts.UTF_8));
				StringBody content = new StringBody("这是一个内容",
						ContentType.create("text/plain", Consts.UTF_8));
				StringBody cphoto = new StringBody(userid, ContentType.create(
						"text/plain", Consts.UTF_8));
				StringBody subjectid = new StringBody("1577", ContentType.create(
						"text/plain", Consts.UTF_8));


				HttpPost httpPost = new HttpPost(targetURL);
				// 以浏览器兼容模式运行,防止文件名乱码
				HttpEntity reqEntity = MultipartEntityBuilder.create()
						.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
						.addPart("file", bin).addPart("title", title)
						.addPart("content", content).addPart("userid", cphoto)
						.addPart("subjectid", subjectid)
						.setCharset(CharsetUtils.get("UTF-8")).build();

				httpPost.setEntity(reqEntity);
				httpPost.setHeader("cookie", "JSESSIONID=" + sessionid);

				CloseableHttpResponse response = httpclient.execute(httpPost);
				try {
					System.out.println(response.getStatusLine());
					HttpEntity resEntity = response.getEntity();
					if (resEntity != null) {
						System.out.println("Response: "
								+ resEntity.getContentLength());
					}
					EntityUtils.consume(resEntity);
				} finally {
					response.close();
				}

			} catch (Exception ex) {

				ex.printStackTrace();

			} finally {
				httpclient.close();
			}
			// 缓缓
			Thread.sleep(1000);
			count++;
//			break;
		}

	}

	// //@Test
	public void testDownload() throws IOException {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("cookie", "JSESSIONID=" + sessionid);
		String param = "?userid=" + userid + "&id=" + "5";
		URL url = new URL("http://" + release_domain + "/cphoto/v2_1/download"
				+ param);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("cookie", "JSESSIONID=" + sessionid);
		conn.connect();
		InputStream in = conn.getInputStream();
		/*
		 * FileOutputStream out = new FileOutputStream(); byte[] bytes = new
		 * byte[1024]; int len = -1; while ((len = in.read(bytes)) != -1) {
		 * out.write(bytes, 0, len); } in.close(); out.flush(); out.close();
		 */
		FileUtils.copyInputStreamToFile(in, new File("d://jaodan.jpg"));
	}

	// @Test
	public void testMuzhiwan() throws IOException {

		String targetURL = null; // -- 指定URL

		targetURL = "https://sdk.muzhiwan.com/oauth2/getuser.php";

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {

			StringBody token = new StringBody(
					"7dbd09fdc51a02432da2383c155f6ea9", ContentType.create(
							"text/plain", Consts.UTF_8));
			StringBody appkey = new StringBody(
					"5c434edb390a046f314d1c250967c39c", ContentType.create(
							"text/plain", Consts.UTF_8));

			HttpPost httpPost = new HttpPost(targetURL);
			// 以浏览器兼容模式运行,防止文件名乱码
			HttpEntity reqEntity = MultipartEntityBuilder.create()
					.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
					.addPart("token", token).addPart("appkey", appkey)
					.setCharset(CharsetUtils.get("UTF-8")).build();

			httpPost.setEntity(reqEntity);

			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				System.out.println(response.getStatusLine());
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					System.out.println(resEntity.getContentLength());
				}
				EntityUtils.consume(resEntity);
			} finally {
				response.close();
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {
			httpclient.close();
		}

	}

	@Test
	public void testRegister() throws UnsupportedEncodingException {
		String account = "mytest_" + Math.round(Math.random() * 1000);
		String password = "123";
		String nickname = "小雨";
		String gender = "x";
		String age = "25";
		String email = "zxm@163.com";
		String telphone = "18675857854";

		HttpRequestUtil
				.httpRequest("GET",
						// "http://121.199.67.141/cphoto/v2/register",
						release_domain + "/v2_1/register",
						String.format(
								"account=%s&password=%s&nickname=%s&gender=%s&age=%s&email=%s&telphone=%s",
								account, password,
								URLEncoder.encode(nickname, "utf-8"), gender,
								age, email, telphone), null);
	}

	// @Test
	public void testFind() throws UnsupportedEncodingException {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("cookie", "JSESSIONID=" + sessionid);
		String param = "userid=" + userid;
		/*
		 * HttpRequestUtil.request("GET",
		 * "http://121.199.67.141/cphoto/v2/find_users",
		 * String.format("nickname=%s&userid=%s", nickname, userid));
		 */
		HttpRequestUtil.httpRequest("GET",
				"http://121.199.67.141/cphoto/v2_1/find_friends", param,
				headers);/**/
		/*
		 * HttpRequestUtil.request("GET",
		 * "http://121.199.67.141/cphoto/v2/find_user",
		 * String.format("userid=%s", userid)); HttpRequestUtil.request("GET",
		 * "http://121.199.67.141/cphoto/v2/req_msg", String.format("userid=%s",
		 * userid)); HttpRequestUtil.request("GET",
		 * "http://121.199.67.141/cphoto/v2/confirm_msg",
		 * String.format("userid=%s&tar_userid=%s", userid, "ta"));
		 */
		/*
		 * HttpRequestUtil.request("GET",
		 * "http://121.199.67.141/cphoto/v2/add_friend",
		 * String.format("userid=%s&tar_userid=%s", userid, tar_userid));
		 */
		/*
		 * HttpRequestUtil.request("GET",
		 * "http://localhost:8080/cphoto/v2/req_msg", String.format("userid=%s",
		 * userid)); HttpRequestUtil.request("GET",
		 * "http://localhost:8080/cphoto/v2/req_msg", String.format("userid=%s",
		 * tar_userid)); HttpRequestUtil.request("GET",
		 * "http://121.199.67.141/cphoto/v2/confirm_msg",
		 * String.format("msgid=%s&userid=%s&state=%s", "1417342507",
		 * tar_userid, "1"));
		 */
	}

	// @Test
	public void sendBody() {
		String body = "{\"sign\":\"d2221b8355a46287902026976d8db18b\",\"id\":1417505599240,\"data\":{\"amount\":100,\"callbackinfo\":\"\",\"failedDesc\":\"\",\"gameId\":\"752388\",\"grade\":\"6\",\"lwid\":143919,\"orderId\":\"ms_yilan_1_143919_1417504679\",\"orderStatus\":\"S\",\"payWay\":\"2\",\"roleId\":\"\",\"roleName\":\"\",\"sdkorderid\":\"20141202153258372\",\"serverId\":\"1\"}}";
		HttpRequestUtil.httpRequest("GET",
				"http://localhost:8080/mobile/yilan/payback", body, null);
	}

	@Test
	public void testListPics() {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("cookie", "JSESSIONID=" + sessionid);
		String param = "userid=" + userid;
		HttpRequestUtil.httpRequest("GET", release_domain + "/v2_1/list_pics",
				param, headers);
	}

	// @Test
	public void testToken() {
		String param = "userid=1417231971&token=371b85af-8609-4ac7-8bb0-1";
		HttpRequestUtil.httpRequest("GET",
				"http://localhost:8080/cphoto/v2_1/token", param, null);
	}

	@Test
	public void testListSubject() {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("cookie", "JSESSIONID=" + sessionid);
		String param = "userid=" + userid + "";
		HttpRequestUtil.httpRequest("GET", local_domain
				+ "/v2_1/list_subjects", param, headers);
	}

	// @Test
	public void testAddComment() {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("cookie", "JSESSIONID=" + sessionid);
		String param = String.format(
				"userid=%s&content=%s&subjectid=%s&replyid=%s", userid, "1",
				"1", "1");
		HttpRequestUtil
				.httpRequest("GET",
						"http://localhost:8080/cphoto/v2_1/add_comment", param,
						headers);
	}

	// @Test
	public void testEventMsg() {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("cookie", "JSESSIONID=" + sessionid);
		String param = "userid=" + userid;
		HttpRequestUtil.httpRequest("GET", "http://" + local_domain
				+ "/cphoto/v2_1/long_poll", param, headers);
	}

	// @Test
	public void testGetMsg() {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("cookie", "JSESSIONID=" + sessionid);
		String param = "userid=" + userid + "&eventid=" + "1001";
		HttpRequestUtil.httpRequest("GET", "http://" + release_domain
				+ "/cphoto/v2_1/req_msg", param, headers);
	}

	// @Test
	public void testAddFriend() {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("cookie", "JSESSIONID=" + sessionid);
		String param = "userid=" + userid + "&tar_userid=" + "1417247032"
				+ "&remark=keke";
		HttpRequestUtil.httpRequest("GET",
				"http://localhost:8080/cphoto/v2_1/add_friend", param, headers);
	}

	// @Test
	public void testConfirmMsg() {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("cookie", "JSESSIONID=" + sessionid);
		String param = "userid=" + userid + "&msgid=" + "3&state=1";
		HttpRequestUtil.httpRequest("GET", "http://" + local_domain
				+ "/cphoto/v2_1/confirm_msg", param, headers);
	}

	// @Test
	public void testFindFriends() {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("cookie", "JSESSIONID=" + sessionid);
		String param = "userid=" + userid + "&method=FIND_USERS&query=xm";
		HttpRequestUtil.httpRequest("GET", "http://" + release_domain
				+ "/cphoto/v2_1/search", param, headers);
	}
}
