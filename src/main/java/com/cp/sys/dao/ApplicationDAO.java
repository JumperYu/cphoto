package com.cp.sys.dao;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.cp.base.dao.EntityDAOImpl;
import com.cp.sys.entity.Application;

@Repository
@Transactional
public class ApplicationDAO extends EntityDAOImpl<Application, String> {
	
}
