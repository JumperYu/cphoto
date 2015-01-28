package com.cp.subject.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cp.entity.Picture;
import com.cp.entity.Reply;

/**
 * 
 * 主题实体转换
 * 
 * @author zengxm 2015年1月5日
 *
 */
public class ReplyRowMapper implements RowMapper<Reply>{

	@Override
	public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
		Reply reply = new Reply();
		
		reply.setId(rs.getInt(1));
		reply.setTitle(rs.getString(2));
		reply.setContent(rs.getString(3));
		reply.setCreateTime(rs.getString(4));
		reply.setUpdateTime(rs.getString(5));
		
		Picture picture = new Picture();
		picture.setId(rs.getInt(6));
		picture.setPicName(rs.getString(7));
		picture.setPicUrl(rs.getString(8));
		picture.setContentType(rs.getString(9));
		picture.setCreateTime(rs.getString(10));
		picture.setUpdateTime(rs.getString(11));
		reply.setPicture(picture);
		
		return reply;
	}

}
