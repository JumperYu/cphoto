package com.cp.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp.base.service.BaseService;
import com.cp.constant.PushMessage;

/**
 * 用户相关业务
 * 
 * @author zengxm @date 2014-11-29
 * 
 */
@Service
@Transactional
public class UserService extends BaseService {

	/*
	 * private static final Logger log = LoggerFactory
	 * .getLogger(UserService.class);
	 */

	private static final String LOGIN_SQL = "select id, nickname, sex, age, email, telphone, userid, account, `password` from cp_account where account=? and `password`=?";
	private static final String REGISTER_USER_SQL = "insert into cp_account(nickname, sex, age, email, telphone, userid, account, `password`) values(?,?,?,?,?,?,?,?)";
	private static final String FIND_FRIENDS = "select id, nickname, sex, age, email, telphone, t1.userid, account, `password` from cp_account t1, cp_friendship t2 where t1.userid=t2.cp_relatedid and t2.cp_userid=?";

	// private static final String REGISTER_USER_SQL =
	// "insert into cp_user(cp_userid, cp_account, cp_pwd) values(?, ?, ?)";
	// private static final String REGISTER_IDENTITY_SQL =
	// "insert into cp_identity(cp_nickname, cp_gender,cp_age,cp_email,cp_telphone,cp_userid) values(?, ?, ?, ?, ?, ?)";

	/**
	 * 查询账号唯一性
	 * 
	 * @param account
	 * 
	 * @return 存在 true, 不存在 false
	 */
	public boolean isAccountExits(String account) {
		String sql = "select count(1) from cp_account where account=?";
		int count = getBaseDAO().findIntBySql(sql, account);
		if (count >= 1)
			return true;
		else
			return false;
	}

	/**
	 * 查看账号&密码是否匹配
	 * 
	 * @param account
	 * @param password
	 * 
	 * @return 成功 true, 否则 false
	 */
	public boolean validate(String account, String password) {

		String sql = "select count(1) from cp_account where account=? and `password`=?";

		int count = getBaseDAO().findIntBySql(sql, account, password);

		if (count >= 1)
			return true;
		else
			return false;
	}

	/**
	 * 返回用户个人信息
	 * 
	 * @param String
	 *            account 账号
	 * 
	 * @return Map
	 */
	public Map<String, Object> findUserByAccount(String account) {
		String sql = "select cphoto,username,gender,login_account from account where login_account=?";

		return getBaseDAO().queryForMap(sql, account);
	}

	/**
	 * 返回用户个人信息
	 * 
	 * @param String
	 *            userid id
	 * 
	 * @return Map
	 */
	public Map<String, Object> findUserByUserid(String userid) {
		String sql = "select * from cp_user a, cp_identity b where a.cp_userid=b.cp_userid and a.cp_userid=?";

		return getBaseDAO().queryForMap(sql, userid);
	}

	/**
	 * 登录账号
	 * 
	 * @param account
	 * @param pwd
	 * @return Map 账号信息
	 */
	public Map<String, Object> login(String account, String pwd) {
		return getBaseDAO().queryForMap(LOGIN_SQL, account, pwd);
	}

	/**
	 * 注册账号
	 * 
	 * @param userid
	 *            唯一标识
	 * @param account
	 *            账号
	 * @param paswword
	 *            密码
	 * @param nickname
	 *            别名
	 * @param gender
	 *            性别
	 * @param age
	 *            年龄
	 * @param email
	 *            邮箱
	 * @param telphone
	 *            手机号
	 * 
	 * @return 是否成功
	 */
	public void register(String userid, String account, String password,
			String nickname, String gender, String age, String email,
			String telphone) {
		getBaseDAO().insert(REGISTER_USER_SQL, nickname, gender, age, email,
				telphone, userid, account, password);
		// addGroup(userid, "1", "default");// every one got the default group
	}

	/**
	 * 添加朋友
	 * 
	 * @param useridll
	 *            = 自己的userid
	 * @param friendId
	 *            想要添加的朋友的id
	 * @param groupId
	 *            分组id
	 * @param groupName
	 *            组
	 */
	public void addFriend(String userid, String friendId, String groupId,
			String groupName) {
		// -->>
		getBaseDAO()
				.insert("insert into cp_friendship(cp_userid, cp_relatedid, cp_groupid) values(?, ?, ?)",
						userid, friendId, groupId);
	}

	/**
	 * Get My firends
	 * 
	 * @param userid
	 * 
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getFriends(int userid) {
		return getBaseDAO().queryForList(FIND_FRIENDS, userid);
	}

	/**
	 * 查到所有的好友
	 * 
	 * @param userid
	 *            自己的id
	 * 
	 * @return JSONArray
	 */
	public List<List<Object>> getFriendsArry(String userid) {
		String sql = "select a.cp_account, a.cp_userid, b.cp_relatedid, c.cp_groupname"
				+ " from cp_user a, cp_friendship b, cp_group c "
				+ " where a.cp_userid=? and a.cp_userid=b.cp_userid and b.cp_groupid=c.cp_groupid";
		return getBaseDAO().queryForArrays(sql, userid);
	}

	/**
	 * 暂时作废
	 * 
	 * @param nickname
	 * @param userid
	 * @return
	 */
	public List<Map<String, Object>> findUsersBy(String nickname, int userid) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select b.* from cp_user a, cp_identity b where a.cp_userid=b.cp_userid";
		sql += " and b.cp_nickname like ? and a.cp_userid != ?";
		params.add("%" + nickname + "%");
		params.add(userid);
		return getBaseDAO().queryForList(sql, params.toArray());
	}

	/**
	 * 搜索人
	 * 
	 * @param nickname
	 * @param userid
	 * @return
	 */
	public List<Map<String, Object>> search(String query) {
		List<String> params = new ArrayList<String>();
		String sql = "select * from cp_user a, cp_identity b where a.cp_userid=b.cp_userid";
		if (StringUtils.isNotBlank(query)) {
			sql += " and b.cp_nickname like ?";
			params.add("%" + query + "%");
		}
		if (StringUtils.isNotBlank(query)) {
			sql += " and a.cp_userid like ?";
			params.add("%" + query + "%");
		}
		return getBaseDAO().queryForList(sql, params.toArray());
	}

	/**
	 * add group
	 * 
	 * @param groupId
	 *            分组id
	 * @param groupName
	 *            组
	 */
	public void addGroup(String userid, String groupId, String groupName) {
		// -->>
		getBaseDAO()
				.insert("insert into cp_group(cp_userid, cp_groupid, cp_groupname) values(?, ?, ?)",
						userid, groupId, groupName);
	}

	/**
	 * Get my groups
	 * 
	 * @param userid
	 * @param groupId
	 * @param groupName
	 * @return List<Map>
	 */
	public List<Map<String, Object>> getGroups(String userid, String groupId,
			String groupName) {
		String sql = "select * from cp_group where 1=1";
		List<String> params = new ArrayList<String>();
		if (StringUtils.isNotBlank(userid)) {
			sql += " and cp_userid=?";
			params.add(userid);
		} else {
			sql += " and 1=2";
		}
		if (StringUtils.isNotBlank(groupId)) {
			sql += " and cp_groupid=?";
			params.add(groupId);
		}
		if (StringUtils.isNotBlank(groupName)) {
			sql += " and cp_groupname=?";
			params.add(groupName);
		}

		return getBaseDAO().queryForList(sql, params.toArray());
	}

	/*
	 * public List<Message> getPushMsg(int userid) { String sql =
	 * "select msgid,msgevent,ori_userid,tar_userid,state from push_msg where tar_userid=? and state=?"
	 * ; return getBaseDAO().getJdbcTemplate().query(sql, new Object[] { userid,
	 * PushMessage.WAIT_STATE }, new RowMapper<Message>() {
	 * 
	 * @Override public Message mapRow(ResultSet rs, int rowNum) throws
	 * SQLException { Message message = new Message();
	 * message.setMsgid(rs.getInt(1)); message.setMsgevent(rs.getInt(2));
	 * message.setOriUserid(rs.getInt(3)); message.setTarUserid(rs.getInt(4));
	 * return message; } }); }
	 */

	public void addPushMsg(String ori_userid, String tar_userid, int msgevent) {
		String msgid = ""
				+ (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + 100);
		String sql = "INSERT INTO push_msg(msgid,ori_userid,tar_userid,msgevent,state) values(?,?,?,?,?);";
		getBaseDAO().insert(sql, msgid, ori_userid, tar_userid, msgevent,
				PushMessage.WAIT_STATE);
	}

	public void updatePushMsg(String msgid, String userid, String state) {
		String sql = "update push_msg set state=? where msgid=? and tar_userid=?";
		getBaseDAO().update(sql, state, msgid, userid);
		sql = "select * from push_msg where msgid=?";
		Map<String, Object> map = getBaseDAO().queryForMap(sql, msgid);
		String p1 = map.get("ori_userid").toString();
		String p2 = map.get("tar_userid").toString();
		addFriend(p1, p2, "1", "default");
		addFriend(p2, p1, "1", "default");
	}

	public void buildFriendShip(int msgid, int userid) {
		String sql = "select * from push_msg where msgid=?";
		Map<String, Object> map = getBaseDAO().queryForMap(sql, msgid);
		String p1 = map.get("ori_userid").toString();
		String p2 = map.get("tar_userid").toString();
		addFriend(p1, p2, "1", "default");
		addFriend(p2, p1, "1", "default");
	}
}
