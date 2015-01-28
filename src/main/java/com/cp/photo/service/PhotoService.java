package com.cp.photo.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp.base.service.BaseService;

/**
 * 
 * @author zengxm 2014年10月10日
 * 
 *         图片业务
 * 
 */
@Service
@Transactional
public class PhotoService extends BaseService {

	private static final Logger log = LoggerFactory
			.getLogger(PhotoService.class);

	/**
	 * 保存一张图片
	 * 
	 * @param picName
	 * @param picPath
	 * @param title
	 * @param content
	 * @param cphoto
	 *            return int id
	 */
	public int savePic(String picName, String picPath, String title,
			String content, String cphoto) {
		log.debug("save pic ing...");
		String sql = "insert into photo_wall(pic_name, pic_path, title, content, cphoto, create_time) values"
				+ "(?, ?, ?, ?, ?, now())";
		int id = getBaseDAO().insert(sql, picName, picPath, title, content,
				cphoto);
		log.debug("save pic finish id:" + id);
		return id;
	}

	/**
	 * v2.1 存储图片
	 * 
	 * @param originalFilename
	 * 			  源文件名
	 * @param filePath
	 *            文件路径
	 * @param contentType
	 *            类型
	 * @param userid
	 * 			  用户id
	 * @return
	 */
	public int savePic(String originalFilename, String filePath,
			String contentType, int userid, String picUrl) {
		String sql = "insert into cp_picture(pic_name,pic_path,content_type,userid,pic_url,create_time,update_time)"
				+ " values(?,?,?,?,?,now(),now())";
		int id = getBaseDAO().insert(sql, originalFilename, filePath,
				contentType, userid, picUrl);
		return id;
	}

	/**
	 * 查询所有图片的信息
	 * 
	 * @return 返回所有行列
	 */
	public List<Map<String, Object>> findAllPics(String cphoto) {
		String sql = "select * from cp_picture where userid=?";
		return getBaseDAO().queryForList(sql, cphoto);
	}

	public Map<String, Object> findPicById(String userid, String id) {
		String sql = "select * from cp_picture where userid=? and id=? ";
		return getBaseDAO().queryForMap(sql, userid, id);
	}

}
