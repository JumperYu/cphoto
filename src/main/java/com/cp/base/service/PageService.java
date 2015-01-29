package com.cp.base.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cp.base.dao.PageDAO;

/**
 * 
 * @author zengxm 2015年01月08日
 * 
 */
@Service
public class PageService {

	private PageDAO pageDAO;
	
	@Resource
	public void setPageDAO(PageDAO pageDAO) {
		this.pageDAO = pageDAO;
	}
	

}
