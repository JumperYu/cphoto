package com.cp.page;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cp.base.service.PageService;
import com.cp.entity.Page;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/config/applicationContext**.xml")
public class TestPage {

	@Resource
	private PageService pageService;

	@Test
	public void testTemp() {
		Page page = new Page();
		page.setIndex(2);
		page.setSize(10);
		System.out.println(pageService.getPageDAO().queryForPageList(
				"select * from temp1", page));
	}

}
