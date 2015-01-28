package com.cp.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cp.entity.Subject;
import com.cp.photo.service.PhotoService;
import com.cp.subject.service.SubjectService;
import com.cp.utils.DateUtils;
import com.cp.utils.PictureIO;

/**
 * 
 * @author zengxm 2015年01月05日
 * 
 *         V2.2.0
 * 
 * @update 完善图片的压缩和存放
 * 
 */
@Controller
@RequestMapping("/v2_1")
public class PublishController {

	private static final Logger log = LoggerFactory
			.getLogger(PublishController.class);

	// 图片资源地址
	private static final String IMAGE_DOMAIN = "http://image.bradypod.com/";
	// 硬盘根目录
	private static final String DISK_UPLOAD_ROOT_DIR = "/pics/";

	@Resource
	private SubjectService subjectService;

	@Resource
	private PhotoService photoService;

	// 发表一个主题
	@RequestMapping(value = "/add_subject", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> addSubject(HttpServletRequest request,
			@RequestParam("file") MultipartFile file) {
		// --> print header and body
		Servlets.printHeaderWithHttpRequest(request);
		Map<String, Object> params = Servlets.getBodyWihtHttpRequest(request); // -->

		String msg = "";
		int ret = -1;
		// --> 生成时间戳相关参数
		String tempFileName = String.valueOf(TimeUnit.MILLISECONDS
				.toSeconds(System.currentTimeMillis()));
		String dateDir = DateUtils.getDateStr("YYYYMMdd");
		/* 构建真实参数 */
		int userid = Integer.valueOf(params.get("userid").toString());
		String fileName = file.getOriginalFilename();
		String contentType = fileName.substring(fileName.lastIndexOf(".") + 1);
		tempFileName += "." + contentType;
		String fileDir = DISK_UPLOAD_ROOT_DIR + dateDir;
		String filePath = fileDir + "/" + tempFileName;
		String fileUrl = IMAGE_DOMAIN + dateDir + "/" + tempFileName;
		/* 构建参数结束 */

		try {
			// 生成新名字写入硬盘
			PictureIO.copyFileFromInputStream(file.getInputStream(), fileDir,
					tempFileName);

			int picId = photoService.savePic(file.getOriginalFilename(),
					filePath, contentType, userid, fileUrl);

			/* int subId = */
			subjectService.addSubject(
					Servlets.ignoreStringNull(params.get("title")),
					Servlets.ignoreStringNull(params.get("content")), picId,
					userid);

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

	// 发表一个回帖
	@RequestMapping(value = "/add_reply", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> addReply(HttpServletRequest request,
			@RequestParam("file") MultipartFile file) {

		Servlets.printHeaderWithHttpRequest(request); // --> print header
		Map<String, Object> params = Servlets.getBodyWihtHttpRequest(request); // -->

		String msg = "";
		int ret = -1;

		// --> 生成时间戳相关参数
		String tempFileName = String.valueOf(TimeUnit.MILLISECONDS
				.toSeconds(System.currentTimeMillis()));
		String dateDir = DateUtils.getDateStr("YYYYMMdd");
		/* 构建真实参数 */
		int userid = Integer.valueOf(params.get("userid").toString());
		String subjectid = Servlets.ignoreStringNull(params.get("subjectid"));
		String fileName = file.getOriginalFilename();
		String contentType = fileName.substring(fileName.lastIndexOf(".") + 1);
		tempFileName += "." + contentType;
		String fileDir = DISK_UPLOAD_ROOT_DIR + dateDir;
		String filePath = fileDir + "/" + tempFileName;
		String fileUrl = IMAGE_DOMAIN + dateDir + "/" + tempFileName;
		/* 构建参数结束 */

		try {
			// 生成新名字写入硬盘
			PictureIO.copyFileFromInputStream(file.getInputStream(), fileDir,
					tempFileName);

			int picId = photoService.savePic(file.getOriginalFilename(),
					filePath, contentType, userid, fileUrl);

			/* int replyId = */
			subjectService.addReply(
					Servlets.ignoreStringNull(params.get("title")),
					Servlets.ignoreStringNull(params.get("content")), picId,
					userid, subjectid);

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

	@RequestMapping("/list_subjects")
	@ResponseBody
	public Map<String, Object> reqUserSubjects(HttpServletRequest request) {
		Servlets.printHeaderWithHttpRequest(request); // --> print header
		Map<String, Object> params = Servlets.getBodyWihtHttpRequest(request); // -->

		Map<String, Object> modelMap = new HashMap<String, Object>();

		List<Subject> subs = subjectService.getSubject(Integer
				.parseInt(Servlets.ignoreStringNull(params.get("userid"))));
		if (subs != null && subs.size() > 0) {
			modelMap.put("ret", 1);
			modelMap.put("subjects", subs);
		} else {
			modelMap.put("ret", 0);
			modelMap.put("subjects", null);
		}
		return modelMap;
	}

	// 添加一条评论
	@RequestMapping("/add_comment")
	@ResponseBody
	public Map<String, Object> addComment(HttpServletRequest request) {
		Servlets.printHeaderWithHttpRequest(request); // --> print header
		Map<String, Object> params = Servlets.getBodyWihtHttpRequest(request); // -->

		// --> 参数
		String content = Servlets.ignoreStringNull(params.get("content"));
		String userid = Servlets.ignoreStringNull(params.get("userid"));
		String subjectid = "";
		String replyid = "";
		if (StringUtils.isEmpty(params.get("subjectid"))) {
			subjectid = null;
		} else {
			subjectid = params.get("subjectid").toString();
		}
		if (StringUtils.isEmpty(params.get("replyid"))) {
			replyid = null;
		} else {
			replyid = params.get("replyid").toString();
		}

		Map<String, Object> modelMap = new HashMap<String, Object>();

		int ret = subjectService
				.addComment(content, subjectid, replyid, userid);

		modelMap.put("ret", ret > 0 ? 1 : -1);
		modelMap.put("msg", "added comment");
		return modelMap;
	}
}
