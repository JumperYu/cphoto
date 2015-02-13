package com.cp.subject.service;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.cp.base.service.PageService;
import com.cp.entity.Comment;
import com.cp.entity.Page;
import com.cp.entity.Reply;
import com.cp.entity.Subject;
import com.cp.utils.xml.SQLPool;

/**
 * 
 * 主题相关
 * 
 * @author zengxm @date 2014-12-20
 * 
 */
@Service
public class SubjectService extends PageService {

	private static URL subject_sql_path = SubjectService.class.getClassLoader()
			.getResource("subject.xml");

	/**
	 * 
	 * 添加主题
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param photoid
	 *            照片表对应的ID
	 * @param userid
	 *            用户
	 * @param nickname
	 *            别名
	 * @return int id
	 * 
	 */
	public int addSubject(String title, String content, int photoid,
			int userid, String nickname) {
		String sql = "insert into cp_subject(title,content,pictureid,userid,nickname,create_time,update_time) values(?,?,?,?,?,now(),now())";
		int id = getPageDAO().insert(sql, title, content, photoid, userid,
				nickname);
		return id;
	}

	/**
	 * 添加一条回帖
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param picId
	 *            图片id
	 * @param userid
	 *            用户id
	 * @param subjectid
	 *            主题id
	 * @return int
	 */
	public int addReply(String title, String content, int picId, int userid,
			String subjectid) {
		String sql = "insert into cp_reply(title,content,pictureid,userid,subjectid,create_time,update_time) values(?,?,?,?,?,now(),now())";
		int id = getPageDAO().insert(sql, title, content, picId, userid,
				subjectid);
		return id;
	}

	/**
	 * 添加一条回帖
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param picId
	 *            图片id
	 * @param userid
	 *            用户id
	 * @param subjectid
	 *            主题id
	 * @return int
	 */
	public int addComment(String content, String subjectid, String replyid,
			int userid, String nickname) {
		String sql = "insert into cp_comment(content,subjectid,replyid,userid,nickname,create_time,update_time) values(?,?,?,?,?,now(),now())";
		int id = getPageDAO().insert(sql, content, subjectid, replyid, userid);
		return id;
	}

	/**
	 * 查找主题评论
	 * 
	 * @param subjectid
	 *            主题id
	 * @return
	 */
	public List<Map<String, Object>> getCommentsBySid(int subjectid, Page page) {
		String find_subject_comments = SQLPool.getSQL(subject_sql_path,
				"find_coms");
		return getPageDAO().queryForPageList(find_subject_comments, page,
				subjectid);
	}

	/**
	 * 查找回帖评论
	 * 
	 * @param subjectid
	 *            主题id
	 * @return
	 */
	public List<Map<String, Object>> getCommentsByRid(int subjectid) {
		return null;
	}

	/**
	 * 分页显示主题 (-自己、 朋友、参与-) 按发布时间排序
	 * 
	 * @param userid
	 *            用户id
	 * @param subjectid
	 *            主题id
	 * @param timeline
	 *            时间轴
	 * @param page
	 *            分页
	 * @param method
	 *            uptodate/past
	 * @return
	 */
	public List<Map<String, Object>> listSubjectByPage(int userid,
			Long subjectId, Long timeline, String method, Page page) {
		String find_subjects = SQLPool
				.getSQL(subject_sql_path, "find_subjects");
		Object[] params = null;
		if (timeline == null || timeline == 0) {
			find_subjects += " order by a.create_time desc";
			params = new Object[] { userid, userid, userid };
		} else if (StringUtils.isEmpty(method) || method.equals("uptodate")) {
			find_subjects += " and a.id > ? and UNIX_TIMESTAMP(a.create_time) > ? order by a.create_time desc";
			params = new Object[] { userid, userid, userid, subjectId, timeline };
		} else {
			find_subjects += " and a.id < ? and UNIX_TIMESTAMP(a.create_time) < ? order by a.create_time desc";
			params = new Object[] { userid, userid, userid, subjectId, timeline };
		}// 拼接查询逻辑
		List<Map<String, Object>> subs = getPageDAO().queryForPageList(
				find_subjects, page, params);
		String page_reply = SQLPool.getSQL(subject_sql_path, "find_replies");
		for (Map<String, Object> sub : subs) {
			Page rp = new Page();
			rp.setSize(100);
			List<Map<String, Object>> replies = getPageDAO().queryForPageList(
					page_reply, page, sub.get("id"));
			Page cp = new Page();
			List<Map<String, Object>> comms = getCommentsBySid(
					Integer.parseInt(sub.get("id").toString()), cp);
			cp.setCount(comms.size());
			sub.put("replies", replies);
			sub.put("comments", comms);
			sub.put("comments_page", cp);
		}
		return subs;
	}

	/**
	 * 获取该用户的所有主题
	 * 
	 * @param userid
	 * @return
	 */
	public List<Subject> getSubject(int userid) {
		// 获取自己的所有主题
		final String sql = "select a.id, a.title, a.content, unix_timestamp(a.create_time), unix_timestamp(a.update_time), b.id, b.pic_name,b.pic_url,b.content_type,"
				+ " unix_timestamp(b.create_time),unix_timestamp(b.update_time) from cp_subject a, cp_picture b"
				+ " where a.userid=? and a.photoid=b.id and a.userid=b.userid order by a.create_time desc";
		// 获取所有回帖
		final String replies_sql = "select a.id, a.title, a.content, unix_timestamp(a.create_time), unix_timestamp(a.update_time), b.id, b.pic_name,b.pic_url,b.content_type,"
				+ " unix_timestamp(b.create_time),unix_timestamp(b.update_time) from cp_reply a, cp_picture b"
				+ " where a.userid=? and a.subjectid=? and a.photoid=b.id and a.userid=b.userid order by a.create_time desc";
		// 获取所有主题评论
		final String subject_comments_sql = "SELECT id,content,subjectid,replyid,userid,"
				+ "UNIX_TIMESTAMP(create_time),UNIX_TIMESTAMP(update_time) FROM cp_comment"
				+ " where userid=? and subjectid=?";
		// 获取所有回帖评论
		final String reply_comments_sql = "SELECT id,content,subjectid,replyid,userid,"
				+ "UNIX_TIMESTAMP(create_time),UNIX_TIMESTAMP(update_time) FROM cp_comment"
				+ " where userid=? and replyid=?";

		List<Subject> subjects = getPageDAO().queryForEntity(
				new SubjectRowMapper(), sql, userid);

		for (Subject subject : subjects) {// list for query replies
			// reply
			List<Reply> replies = getPageDAO().queryForEntity(
					new ReplyRowMapper(), replies_sql, userid, subject.getId());

			for (Reply reply : replies) {// list for replies comments
				List<Comment> replyComments = getPageDAO().queryForEntity(
						new SubjectCommentRowMapper(), reply_comments_sql,
						userid, reply.getId());
				reply.setComments(replyComments);
			}// reply comment

			// subject comment
			List<Comment> subComments = getPageDAO().queryForEntity(
					new SubjectCommentRowMapper(), subject_comments_sql,
					userid, subject.getId());
			subject.setReplies(replies);
			subject.setComments(subComments);

		}// end of for

		return subjects;
	}

}
