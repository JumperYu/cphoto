package com.cp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author zengxm 2014-12-20
 *
 */
public class Reply {

	private int id; //
	private String title;// '标题',
	private String content; // '主要内容'
	private int userid; // '用户id'
	private String createTime; // '记录时间'
	private String updateTime; // '更新时间'
	private Picture picture = new Picture();
	List<Comment> comments = new ArrayList<Comment>(); // 评论

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Reply [id=" + id + ", title=" + title + ", content=" + content
				+ ", userid=" + userid + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", picture=" + picture
				+ ", comments=" + comments + "]";
	}
	
}
