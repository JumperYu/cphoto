package com.cp.subject.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cp.entity.Comment;

/**
 * 
 * 主题实体转换
 * 
 * @author zengxm 2015年1月5日
 * 
 */
public class ReplyCommentRowMapper implements RowMapper<Comment> {

	@Override
	public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
		Comment comment = new Comment();
		comment.setId(rs.getInt(1));
		comment.setContent(rs.getString(2));
		comment.setSubjectid(rs.getInt(3));
		comment.setReplyid(rs.getInt(4));
		comment.setUserid(rs.getInt(5));
		comment.setCreate_time(rs.getString(6));
		comment.setUpdate_time(rs.getString(7));
		return comment;
	}
}
