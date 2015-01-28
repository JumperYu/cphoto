package com.cp.subject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cp.base.service.BaseService;
import com.cp.entity.Comment;
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
public class SubjectService extends BaseService {

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
	 * @return int id
	 */
	public int addSubject(String title, String content, int photoid, int userid) {
		String sql = "insert into cp_subject(title,content,photoid,userid,create_time,update_time) values(?,?,?,?,now(),now())";
		int id = getBaseDAO().insert(sql, title, content, photoid, userid);
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
		String sql = "insert into cp_reply(title,content,photoid,userid,subjectid,create_time,update_time) values(?,?,?,?,?,now(),now())";
		int id = getBaseDAO().insert(sql, title, content, picId, userid,
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
			String userid) {
		String sql = "insert into cp_comment(content,subjectid,replyid,userid,create_time,update_time) values(?,?,?,?,now(),now())";
		int id = getBaseDAO().insert(sql, content, subjectid, replyid, userid);
		return id;
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

		List<Subject> subjects = getBaseDAO().queryForEntity(
				new SubjectRowMapper(), sql, userid);

		for (Subject subject : subjects) {// list for query replies
			// reply
			List<Reply> replies = getBaseDAO().queryForEntity(
					new ReplyRowMapper(), replies_sql, userid, subject.getId());

			for (Reply reply : replies) {// list for replies comments
				List<Comment> replyComments = getBaseDAO().queryForEntity(
						new SubjectCommentRowMapper(), reply_comments_sql,
						userid, reply.getId());
				reply.setComments(replyComments);
			}// reply comment

			// subject comment
			List<Comment> subComments = getBaseDAO().queryForEntity(
					new SubjectCommentRowMapper(), subject_comments_sql,
					userid, subject.getId());
			subject.setReplies(replies);
			subject.setComments(subComments);

		}// end of for

		return subjects;
	}

}
