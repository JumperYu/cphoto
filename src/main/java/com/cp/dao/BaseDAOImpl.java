package com.cp.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

//@Repository
public class BaseDAOImpl implements BaseDAO {
	private JdbcTemplate jdbcTemplate;

	@Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(Object obj) {

	}

	public void insert(String sql) {
		jdbcTemplate.execute(sql);
	}

	public void insert(String sql, Object... args) {
		jdbcTemplate.update(sql, args);
	}

	public void insertObjects(String[] sqls) {
		jdbcTemplate.batchUpdate(sqls);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql, Object... args) {
		return jdbcTemplate.queryForList(sql, args);
	}

	// hack
	@Override
	public Map<String, Object> queryForMap(String sql, Object... args) {
		List<Map<String, Object>> result = queryForList(sql, args);
		if (result != null && result.size() > 0)
			return result.get(0);
		else
			return null; // or new HashMap<String, Object>();
	}

	public void update(String how, Object... args) {
		jdbcTemplate.update(how, args);
	}

	public void delete(String sql) {
		if (sql == null) {
			return;
		}
		jdbcTemplate.execute(sql);
	}

	public void edit(String sql) {
		if (sql == null) {
			return;
		}
		jdbcTemplate.execute(sql);
	}

	public int findIntBySql(String sql, Object... args) {
		return jdbcTemplate.queryForObject(sql, args, Integer.class);
	}

	public String findStringBySql(String sql, Object... args) {
		return jdbcTemplate.queryForObject(sql, args, String.class);
	}

	public void save(String sql) {
		if (sql == null) {
			return;
		}
		jdbcTemplate.execute(sql);
	}

}