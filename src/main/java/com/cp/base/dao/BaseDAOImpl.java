package com.cp.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDAOImpl implements BaseDAO {
	private JdbcTemplate jdbcTemplate;

	@Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public int insert(final String sql, final Object... args) {
		KeyHolder keyHolder = new GeneratedKeyHolder();// 关键
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				for (Object arg : args) {
					ps.setObject(index, arg);
					index++;
				}// -->> 不确定是否可行
				return ps;
			}
		}, keyHolder);// --> end
		return keyHolder.getKey() == null ? 0 : keyHolder.getKey().intValue();
	}

	public void insertObjects(String[] sqls) {
		jdbcTemplate.batchUpdate(sqls);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql, Object... args) {
		return jdbcTemplate.queryForList(sql, args);
	}
	
	@Override
	public <T> List<T> queryForEntity(RowMapper<T> rowMapper, String sql,
			Object... args) {
		return jdbcTemplate.query(sql, args, rowMapper);
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

	@Override
	public List<List<Object>> queryForArrays(String sql, Object... args) {
		final List<List<Object>> arrays = new ArrayList<List<Object>>();
		jdbcTemplate.query(sql, args, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				while (rs.next()) {
					List<Object> row = new ArrayList<Object>();
					int count = rs.getMetaData().getColumnCount();
					for (int col = 1; col <= count; col++) {
						row.add(rs.getObject(col));
					}
					arrays.add(row);
				}
			}
		});
		return arrays;
	}

	public int update(String sql, Object... args) {
		return jdbcTemplate.update(sql, args);
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