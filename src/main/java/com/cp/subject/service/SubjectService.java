package com.cp.subject.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.cp.base.service.PageService;
import com.cp.entity.Comment;
import com.cp.entity.Page;
import com.cp.entity.Reply;
import com.cp.entity.Subject;

/**
 * 
 * 主题相关
 * 
 * @author zengxm @date 2014-12-20
 * 
 */
@Service
public class SubjectService extends PageService {

	// 分页查找主题
	private static String PAGE_SUBJECT = "SELECT\n" + "	a.id,\n"
			+ "	a.title,\n" + "	a.content,\n" + "	a.userid,\n"
			+ "	a.nickname,\n"
			+ "	unix_timestamp(a.create_time) create_time,\n"
			+ "	unix_timestamp(a.update_time) updae_time,\n" + "	b.pic_name,\n"
			+ "	b.pic_url,\n" + "	b.content_type,\n"
			+ "	unix_timestamp(b.update_time) pic_update_time\n" + "FROM\n"
			+ "	cp_subject a,\n" + "	cp_picture b\n" + "WHERE\n"
			+ "	a.pictureid = b.id\n" + "AND a.userid = b.userid\n"
			+ "AND a.id IN (\n" + "	SELECT\n" + "		a.id\n" + "	FROM\n"
			+ "		cp_subject a,\n" + "		cp_picture b\n" + "	WHERE\n"
			+ "		a.userid = ?\n" + "	AND a.pictureid = b.id\n"
			+ "	AND a.userid = b.userid\n" + "	UNION ALL\n" + "		SELECT\n"
			+ "			a.id\n" + "		FROM\n" + "			cp_subject a,\n"
			+ "			cp_picture b\n" + "		WHERE\n" + "			a.pictureid = b.id\n"
			+ "		AND EXISTS (\n" + "			SELECT\n" + "				c.subjectid\n"
			+ "			FROM\n" + "				cp_reply c\n" + "			WHERE\n"
			+ "				b.userid = ?\n" + "			AND a.id = c.subjectid\n" + "		)\n"
			+ "		UNION ALL\n" + "			SELECT\n" + "				a.id\n" + "			FROM\n"
			+ "				cp_subject a,\n" + "				cp_picture b\n" + "			WHERE\n"
			+ "				a.pictureid = b.id\n" + "			AND EXISTS (\n" + "				SELECT\n"
			+ "					t.cp_relatedid\n" + "				FROM\n" + "					cp_friendship t\n"
			+ "				WHERE\n" + "					t.cp_userid = ?\n"
			+ "				AND a.userid = t.cp_relatedid\n" + "			)\n" + ")" + " ";

	// 分页查找评论
	private static String PAGE_SUBJECT_COMMENTS = "SELECT\n" + "	id,\n"
			+ "	content,\n" + "	subjectid,\n" + "	replyid,\n" + "	userid,\n"
			+ "   nickname,\n" + "	UNIX_TIMESTAMP(create_time) create_time,\n"
			+ "	UNIX_TIMESTAMP(update_time) update_time\n" + "FROM\n"
			+ "	cp_comment\n" + "WHERE\n" + "	subjectid = ?"
			+ " order by create_time desc";

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
		return getPageDAO().queryForPageList(PAGE_SUBJECT_COMMENTS, page,
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
		String sql = "";
		Object[] params = null;
		if(timeline == null || timeline == 0) {
			sql += PAGE_SUBJECT
					+ " order by a.create_time desc";
			params = new Object[]{userid, userid, userid};
		} else if (StringUtils.isEmpty(method) || method.equals("uptodate")) {
			sql += PAGE_SUBJECT
					+ " and a.id > ? and UNIX_TIMESTAMP(a.create_time) > ? order by a.create_time desc";
			params = new Object[]{ userid, userid, userid, subjectId, timeline};
		} else {
			sql += PAGE_SUBJECT
					+ " and a.id < ? and UNIX_TIMESTAMP(a.create_time) < ? order by a.create_time desc";
			params = new Object[]{ userid, userid, userid, subjectId, timeline};
		}
		List<Map<String, Object>> subs = getPageDAO().queryForPageList(sql,
				page, params);
		for (Map<String, Object> sub : subs) {
			Page cp = new Page();
			List<Map<String, Object>> comms = getCommentsBySid(
					Integer.parseInt(sub.get("id").toString()), cp);
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
