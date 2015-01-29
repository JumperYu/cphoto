package com.cp.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 基于Spring-JDBC的Template
 * 
 * @author zengxm
 * 
 * @version 1.0.1 2014-10-10
 * 
 */
public interface BaseDAO {

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

	public void update(String how, Object... args);

	public void insert(Object obj);

	public void insert(String sql);

	public void insert(String sql, Object... args);

	public void save(String sql);

	public void edit(String sql);

	// public void execute(String sql, PreparedStatementCallback callback);

	public void delete(String sql);

	public void insertObjects(String[] sqls);

}