package com.cp.subject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cp.entity.Comment;
import com.cp.entity.Reply;
import com.cp.entity.Subject;
import com.cp.photo.service.PhotoService;
import com.cp.subject.service.ReplyRowMapper;
import com.cp.subject.service.SubjectCommentRowMapper;
import com.cp.subject.service.SubjectRowMapper;
import com.cp.subject.service.SubjectService;
import com.cp.user.service.UserService;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("/config/applicationContext**.xml")
public class TestSubjectService {

	@Autowired
	private UserService userService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private SubjectService subjectService;

	//@Test
	public void addSubject() {
		subjectService.addSubject("主题-1", "c-1", 1, 1);
	}

	//@Test
	public void addReply() {
		subjectService.addReply("主题-1", "c-1", 1, 1, "1");
	}

	//@Test
	public void addComment() {
		subjectService.addComment("hehe", "1", "1", "1");
	}

	//@Test
	public void getSubjects() {
		List<Subject> subs = subjectService.getSubject(1417231971);
		System.out.println(subs);
	}

	//@Test
	public void testRowMapper() {
		final String sql = "select a.id, a.title, a.content, unix_timestamp(a.create_time), unix_timestamp(a.update_time), b.id, b.pic_name,"
				+ " unix_timestamp(b.create_time),unix_timestamp(b.update_time) from cp_subject a, cp_picture b"
				+ " where a.userid=? and a.photoid=b.id and a.userid=b.userid order by a.create_time desc";
		final String replies_sql = "select a.id, a.title, a.content, unix_timestamp(a.create_time), unix_timestamp(a.update_time), b.id, b.pic_name,"
				+ " unix_timestamp(b.create_time),unix_timestamp(b.update_time) from cp_reply a, cp_picture b"
				+ " where a.userid=? and a.subjectid=? and a.photoid=b.id and a.userid=b.userid order by a.create_time desc";
		final String subject_comments_sql = "SELECT id,content,subjectid,replyid,userid,"
				+ "UNIX_TIMESTAMP(create_time),UNIX_TIMESTAMP(update_time) FROM cp_comment"
				+ " where userid=? and subjectid=?";
		final String reply_comments_sql = "SELECT id,content,subjectid,replyid,userid,"
				+ "UNIX_TIMESTAMP(create_time),UNIX_TIMESTAMP(update_time) FROM cp_comment"
				+ " where userid=? and replyid=?";
		List<Subject> subjects = subjectService.getBaseDAO().queryForEntity(
				new SubjectRowMapper(), sql, 1417231971);
		for (Subject subject : subjects) {
			// reply
			List<Reply> replies = subjectService.getBaseDAO().queryForEntity(
					new ReplyRowMapper(), replies_sql, 1417231971,
					subject.getId());
			
			for (Reply reply : replies) {
				List<Comment> replyComments = subjectService.getBaseDAO()
						.queryForEntity(new SubjectCommentRowMapper(),
								reply_comments_sql, 1417231971, reply.getId());
				reply.setComments(replyComments);
			}// reply comment
			
			// subject comment
			List<Comment> subComments = subjectService.getBaseDAO()
					.queryForEntity(new SubjectCommentRowMapper(),
							subject_comments_sql, 1417231971, subject.getId());
			subject.setReplies(replies);
			subject.setComments(subComments);
		}
		System.out.println(subjects);
	}
}
