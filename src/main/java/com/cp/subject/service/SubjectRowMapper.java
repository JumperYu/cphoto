package com.cp.subject.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cp.entity.Picture;
import com.cp.entity.Subject;

/**
 * 
 * 主题实体转换
 * 
 * @author zengxm 2015年1月5日
 *
 */
public class SubjectRowMapper implements RowMapper<Subject>{

	@Override
	public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
		Subject subject = new Subject();
		
		subject.setId(rs.getInt(1));
		subject.setTitle(rs.getString(2));
		subject.setContent(rs.getString(3));
		subject.setCreateTime(rs.getString(4));
		subject.setUpdateTime(rs.getString(5));
		
		Picture picture = new Picture();
		picture.setId(rs.getInt(6));
		picture.setPicName(rs.getString(7));
		picture.setPicUrl(rs.getString(8));
		picture.setContentType(rs.getString(9));
		picture.setCreateTime(rs.getString(10));
		picture.setUpdateTime(rs.getString(11));
		subject.setPicture(picture);
		
		return subject;
	}

}
