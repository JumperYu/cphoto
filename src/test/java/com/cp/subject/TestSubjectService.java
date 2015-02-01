package com.cp.subject;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cp.entity.Page;
import com.cp.entity.Subject;
import com.cp.photo.service.PhotoService;
import com.cp.subject.service.SubjectService;
import com.cp.user.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/config/applicationContext**.xml")
public class TestSubjectService {

	@Autowired
	private UserService userService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private SubjectService subjectService;

	// @Test
	public void addSubject() {
		subjectService.addSubject("主题-1", "c-1", 1, 1, "小鱼");
	}

	// @Test
	public void addReply() {
		subjectService.addReply("主题-1", "c-1", 1, 1, "1");
	}

	// @Test
	public void addComment() {
		subjectService.addComment("hehe", "1", "1", 1, "小鱼");
	}

	@Test
	public void getSubjects() {
		List<Subject> subs = subjectService.getSubject(1417231971);
		System.out.println(subs);
	}

	@Test
	public void getPageSubject() {
		Page page = new Page();
		page.setIndex(1);
		System.out.println(subjectService.listSubjectByPage(1422524281, 1266L, 0L, "uptodate",
				page));
	}

}
