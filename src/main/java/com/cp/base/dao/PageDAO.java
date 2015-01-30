package com.cp.base.dao;

import java.util.List;
import java.util.Map;

import com.cp.entity.Page;

/**
 * 
 * @author zengxm
 * @date 2015-01-18
 *
 */
public interface PageDAO extends BaseDAO{

	/**
	 * 分页查询
	 * 
	 * @param sql
	 *            查询语句
	 * @param page
	 *            分页对象
	 * @param args
	 *            动态参数
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryForPageList(String sql, Page page,
			Object... args);
	
	/**
	 * 动态拼接SQL,返回查询表的结果集总数
	 * 
	 * @param sql
	 * @param args
	 * @return int 
	 */
	public int queryPageCount(String sql, Object... args);
}
