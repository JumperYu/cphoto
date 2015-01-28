package com.cp.user;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cp.constant.PushMessage;
import com.cp.photo.service.PhotoService;
import com.cp.user.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/config/applicationContext**.xml")
public class TestUserService {

	@Autowired
	private UserService userService;

	@Autowired
	private PhotoService photoService;
	
	@Test
	public void testZhongwen(){
		userService.getBaseDAO().insert("insert into temp1 values('中文')");
		userService.getBaseDAO().insert("insert into temp2 values('中文')");
	}

	@Test
	public void testUserRegister() {
		String cphoto = ""
				+ (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + 100);
		String account = "mytest_" + Math.round(Math.random() * 1000);
		String password = "123";
		String nickname = "小雨";
		String gender = "x";
		String age = "25";
		String email = "zxm@163.com";
		String telphone = "18675857854";
		userService.register(cphoto, account, password, nickname, gender, age,
				email, telphone);
	}

	@Test
	public void testUserLogin() {
		String account = "mytest_112";
		String pwd = "123";
		System.out.println(userService.login(account, pwd));
	}

	@Test
	public void testListPics() {
		String cphoto = "1417231971";
		System.out.println(photoService.findAllPics(cphoto));
	}

	@Test
	public void testAddFriend() {
/*		userService.addFriend("1417231971", "1417236064", "1", "组-1");
		userService.addFriend("1417231971", "1417236714", "1", "组-1");*/
		userService.addFriend("1417231971", "1417244641", "1", "组-1");
	}

	@Test
	public void testGetFriends() {
//		System.out.println(userService.getFriendsArry("1417231971"));
		System.out.println(userService.getFriends(1417231971));
	}
	
	@Test
	public void testGetUsers() {
//		System.out.println(userService.getFriendsArry("1417231971"));
		System.out.println(userService.findUsersBy("123", 1417231971));
	}

	@Test   
	public void testGetGroups() {
		System.out.println(userService.getGroups("1417231971", "", ""));
	}
	
/*	@Test   
	public void testPushMsg() {
		System.out.println(userService.getPushMsg(1417248218));
	}*/
	
	@Test
	public void testAddMsg(){
		userService.addPushMsg("1417231971", "1417236064", PushMessage.EVENT_FOR_FRIEND_ASKING);
	}
	
	@Test
	public void testGetUserid(){
		System.out.println(userService.findUserByUserid("1417231971"));
	}
	
}
