package com.cp.entity;

/**
 * 评论
 * 
 * @author zengxm
 *
 */
public class Comment {

	private int id;
	private String content;
	private int subjectid;
	private int replyid;
	private int userid;
	private String create_time;
	private String update_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(int subjectid) {
		this.subjectid = subjectid;
	}

	public int getReplyid() {
		return replyid;
	}

	public void setReplyid(int replyid) {
		this.replyid = replyid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Comment [id=" + id + ", content=" + content + ", subjectid="
				+ subjectid + ", replyid=" + replyid + ", userid=" + userid
				+ ", create_time=" + create_time + ", update_time="
				+ update_time + "]";
	}
	
}
