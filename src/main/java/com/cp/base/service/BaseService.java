package com.cp.base.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cp.base.dao.BaseDAO;

/**
 * 
 * @author zengxm 2014年11月6日
 * 
 */
@Service
public class BaseService {

	private BaseDAO baseDAO;

	@Resource
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}
}
