package com.cp.photo.service;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cp.dao.BaseDAO;

/**
 * 
 * @author zengxm 2014年10月10日
 * 
 *         账号业务
 * 
 */
//@Service
public class AccountService {

	private static final Logger log = LoggerFactory
			.getLogger(AccountService.class);

	private BaseDAO baseDAO;

	/**
	 * 查看账号&密码是否匹配
	 */
	public boolean validate(String account, String password) {

		log.debug("excute validate here");

		String sql = "select 1 from account where login_account=? and login_pwd=?";

		Map<String, Object> rs = baseDAO.queryForMap(sql, account, password);

		if (rs != null)
			return true;
		else
			return false;
	}

	/**
	 * 返回用户个人信息
	 * 
	 * @param String account 账号
	 * 
	 * @return Map
	 */
	public Map<String, Object> findUserByAccount(String account) {
		String sql = "select cphoto,username,gender,login_account from account where login_account=?";

		return baseDAO.queryForMap(sql, account);
	}

	/**
	 * 注册账号
	 * 
	 * @param
	 * @param paswword
	 * @param username
	 * @param gender
	 * @return 暂这样
	 */
	public void register(String cphoto, String account, String paswword,
			String username, String gender) {
		String sql = "insert into account(cphoto,username,gender,login_account,login_pwd,create_time) "
				+ " values(?, ?, ?, ?, ?, now())";
		baseDAO.update(sql, cphoto, username, gender, account, paswword);
	}

	/**
	 * 查询账号唯一性
	 * 
	 * @param account
	 * @return true or false
	 */
	public boolean isAccountExits(String account) {
		String sql = "select 1 from account where login_account=?";
		Map<String, Object> rs = baseDAO.queryForMap(sql, account);
		if (rs != null)
			return true;
		else
			return false;
	}

	@Resource
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

}
