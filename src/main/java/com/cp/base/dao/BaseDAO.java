package com.cp.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * 
 * 基于Spring-JDBC的Template
 * 
 * @author zengxm
 * 
 * @version 1.0.1 2014-10-10
 * @version 1.1.0 2014-12-20 添加返回主键
 * 
 */
public interface BaseDAO {

	public JdbcTemplate getJdbcTemplate();

	/**
	 * 查询多行数据
	 * 
	 * @param sql
	 * @param args
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryForList(String sql, Object... args);

	/**
	 * 查询单行多列数据
	 * 
	 * @param sql
	 * @param args
	 * @return Map<String, Object>
	 */
	public Map<String, Object> queryForMap(String sql, Object... args);
	
	/**
	 * 不好的设计 SQL 转换实体
	 * 
	 * @param rowMapper 转换器
	 * @param sql		查询
	 * @param args		形参
	 * @return
	 */
	public <T> List<T> queryForEntity(RowMapper<T> rowMapper, String sql,
			Object... args);
	
	/**
	 * 查看数据以数组返回
	 * 
	 * @param sql
	 * @param args
	 * @return List<List<Object>>
	 */
	public List<List<Object>> queryForArrays(String sql, Object... args);

	/**
	 * 查询返回int值的sql
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public int findIntBySql(String sql, Object... args);

	/**
	 * 查询返回String的SQL
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public String findStringBySql(String sql, Object... args);

	public int update(String sql, Object... args);

	public int insert(String sql, Object... args);

	public void save(String sql);

	public void edit(String sql);

	// public void execute(String sql, PreparedStatementCallback callback);

	public void delete(String sql);

	public void insertObjects(String[] sqls);

}