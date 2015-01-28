package com.cp.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cp.photo.service.PhotoService;
import com.cp.utils.ImageUtil;

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
public class ImageLoaderController {

	private static final Logger log = LoggerFactory
			.getLogger(ImageLoaderController.class);

	@Resource
	private PhotoService photoService;

	// 改进load图逻辑
	@RequestMapping(value = "/download")
	public void loadImage(HttpServletRequest request, HttpServletResponse resp) {

		// --> print header and body
		Map<String, Object> headers = Servlets
				.getHeaderWihtHttpRequest(request);
		Map<String, Object> params = Servlets.getBodyWihtHttpRequest(request); // -->

		try {
			String userid = params.get("userid").toString();
			String id = params.get("id").toString();
			// --> 缩略图尺寸
			String width = Servlets.ignoreStringNull(params.get("width"));
			String height = Servlets.ignoreStringNull(params.get("height"));
			// -->
			Map<String, Object> picData = photoService.findPicById(userid, id);
			if (picData == null) {
				log.debug(String.format(
						"user:%s,load image id:%s, is not exists", userid, id));
				return;
			}
			String picPath = picData.get("pic_path").toString();
			String picName = picData.get("pic_name").toString();
			String contentType = picData.get("content_type").toString();
			File file = new File(picPath);
			if (file.exists()) {
				ServletOutputStream output = resp.getOutputStream();
				// set download file name
				if (headers.get("user-agent").toString().contains("MSIE")) {
					// IE
					picName = URLEncoder.encode(picName, "utf-8");
				} else {
					// 只能兼容火狐的中文
					picName = new String(picName.getBytes(), "iso-8859-1");
				}

				// --> set response content
				resp.setCharacterEncoding("utf-8");
				// resp.setContentType("application/x-msdownload");
				resp.addHeader("Content-Disposition", "attachment; filename=\""
						+ picName + "\"");

				if (StringUtils.isNotEmpty(width)
						&& StringUtils.isNotEmpty(height)) {
					ImageUtil.transform(file, output, Integer.valueOf(width),
							Integer.valueOf(height), contentType);
					// need to add file length
				} else {
					// set content length
					int fileLength = (int) file.length();
					resp.setContentLength(fileLength);
					InputStream input = new FileInputStream(file);
					IOUtils.copy(input, output);
					IOUtils.closeQuietly(input);
				}// 如果有尺寸则取缩略图 否则 原图

				IOUtils.closeQuietly(output);

				log.debug("download success!");
			} else {
				log.debug("download file is not exists!");
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
}
