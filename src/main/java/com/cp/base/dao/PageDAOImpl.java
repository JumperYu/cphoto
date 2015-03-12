package com.cp.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cp.entity.Page;

@Repository("pageDAO")
public class PageDAOImpl extends BaseDAOImpl implements PageDAO {

	@Override
	public List<Map<String, Object>> queryForPageList(String sql, Page page,
			Object... args) {
		return queryForList(buildLimitCondition(sql, page), args);
	}

	@Override
	public int queryPageCount(String sql, Object... args) {
		sql = "select count(1) from (" + sql + ")t";
		int pageCount = findIntBySql(sql, args);
		return pageCount;
	}

	// 构建MySQL分页语句
	public static String buildLimitCondition(String sql, Page page) {
		if (page.getIndex() > 0) {
			page.setIndex(page.getIndex() - 1);
		}
		if (page.getSize() <= 0) {
			page.setSize(Page.DEFAULT_SIZE);
		}
		return sql += " limit " + page.getIndex() * page.getSize() + ", "
				+ page.getSize();
	}
}
