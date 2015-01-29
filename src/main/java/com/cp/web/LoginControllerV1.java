package com.cp.web;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author zengxm 2014年10月12日
 * 
 *         登录接口
 * 
 */
//@Controller
@RequestMapping("/v1")
public class LoginControllerV1 {

	/*	private static final Logger log = LoggerFactory
			.getLogger(LoginControllerV1.class);

	private AccountService accountService;
	private PhotoService photoService;

	// 登录接口
	@RequestMapping("/login")
	@ResponseBody
	public Map<Object, Object> login(
			@RequestParam(required = true) String account,
			@RequestParam(required = true) String password) {

		log.debug(String.format("someone login:%s,%s", account, password));

		boolean isExits = accountService.validate(account, password);

		Map<Object, Object> modelMap = new HashMap<Object, Object>();
		if (isExits) {
			Map<String, Object> accountMap = accountService
					.findUserByAccount(account);
			modelMap.put("ret", 1);
			modelMap.put("msg", "login success!");
			modelMap.put("acccount", accountMap);
		} else {
			modelMap.put("ret", -1);
			modelMap.put("msg", "login fail!");
			modelMap.put("acccount", new HashMap<Object, Object>());
		}
		return modelMap;
	}

	// 注册接口
	@RequestMapping("/register")
	@ResponseBody
	public Map<Object, Object> register(
			@RequestParam(required = true) String account,
			@RequestParam(required = true) String password,
			@RequestParam(required = true) String username,
			@RequestParam(required = true) String gender) {

		log.debug(String.format("someone register:%s", account));

		int ret = -1;
		String msg = "error";

		String cphoto = "cp_"
				+ (TimeUnit.MICROSECONDS.toSeconds(System.currentTimeMillis()) + 100);

		boolean isExits = accountService.isAccountExits(account);

		if (!isExits) {
			ret = 1;
			accountService
					.register(cphoto, account, password, username, gender);
			msg = "register success!";
		} else {
			ret = -1;
			msg = "account exits ready";
		}
		Map<Object, Object> usr = new HashMap<Object, Object>();
		usr.put("cphoto", cphoto);
		usr.put("username", username);
		usr.put("gender", gender);

		Map<Object, Object> modelMap = new HashMap<Object, Object>();
		modelMap.put("ret", ret);
		modelMap.put("msg", msg);
		if (ret == 1)
			modelMap.put("usr", usr);
		else
			modelMap.put("usr", null);

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
		// String uploadPath = "d:/";
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
			msg = "publish ok!";
			ret = 1;
		} catch (IOException e) {
			// e.printStackTrace();
			msg = "system error";
			ret = -1;
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
	public Map<Object, Object> listPics() {
		Map<Object, Object> modelMap = new HashMap<Object, Object>();
		modelMap.put("pics", photoService.findAllPics());
		return modelMap;
	}

	// 下载图片
	@RequestMapping(value = "/download")
	public void download(@RequestParam(required = true) Integer id,
			HttpServletResponse resp) {
		try {

			log.debug(String.format("ready to download pic id:%s", id));
			Map<String, Object> picData = photoService.findPicById(id);
			if (picData == null)
				return;
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("multipart/form-data");
			File file = new File(picData.get("pic_path").toString());
			if (file.exists()) {
				resp.setContentType("application/x-msdownload");
				resp.addHeader("Content-Disposition", "attachment; filename=\""
						+ picData.get("pic_name") + "\"");
				int fileLength = (int) file.length();
				resp.setContentLength(fileLength);
				if (fileLength != 0) {
					InputStream inStream = new FileInputStream(file);
					byte[] buf = new byte[4096];
					ServletOutputStream servletOS = resp.getOutputStream();
					int readLength;
					while (((readLength = inStream.read(buf)) != -1)) {
						servletOS.write(buf, 0, readLength);
					}
					inStream.close();
					servletOS.flush();
					servletOS.close();
				}
			}
			log.debug("download success!");
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	@Resource
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@Resource
	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}*/
}
