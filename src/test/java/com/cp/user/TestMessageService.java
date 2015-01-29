package com.cp.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.cp.msg.MessageService;
import com.cp.photo.service.PhotoService;
import com.cp.user.service.UserService;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("/config/applicationContext**.xml")
public class TestMessageService {

	@Autowired
	private UserService userService;

	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private MessageService messageService;
	
	//@Test
	public void testEvent(){
		System.out.println(messageService.getEventMsg(1));
	}
	
	//@Test
	public void testMessage(){
		System.out.println(messageService.getMsg(1001, 1417231971));
	}
}
