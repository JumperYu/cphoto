package com.cp.msg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.cp.base.service.BaseService;
import com.cp.constant.PushMessage;
import com.cp.entity.Event;
import com.cp.entity.Message;

@Service
public class MessageService extends BaseService {

	/**
	 * 长轮询获取事件
	 * 
	 * @param userid
	 * @return
	 */
	public List<Event> getEventMsg(int userid) {
		String sql = "select a.eventid, a.event_name, count(b.msgid) from"
				+ " msgevent a left join push_msg b"
				+ " on a.eventid=b.eventid and b.tar_userid=? and b.state=?"
				+ " group by a.eventid order by a.eventid";
		return getBaseDAO().getJdbcTemplate().query(sql,
				new Object[] { userid, PushMessage.WAIT_STATE },
				new RowMapper<Event>() {
					@Override
					public Event mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Event event = new Event();
						event.setEventid(rs.getInt(1));
						event.setEventName(rs.getString(2));
						event.setCount(rs.getInt(3));
						return event;
					}
				});
	}

	public List<Message> getMsg(int eventid, int userid) {
		String sql = "select b.msgid,b.eventid, a.cp_nickname,a.cp_userid,a.cp_gender from cp_identity a, push_msg b"
				+ " where a.cp_userid=b.ori_userid and b.eventid=? and b.tar_userid=? and b.state=?";
		return getBaseDAO().getJdbcTemplate().query(sql,
				new Object[] { eventid, userid, PushMessage.WAIT_STATE },
				new RowMapper<Message>() {
					@Override
					public Message mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Message message = new Message();
						message.setMsgid(rs.getInt(1));
						message.setEventid(rs.getInt(2));
						HashMap<String, Object> request_user = new HashMap<String, Object>();
						request_user.put("nickname", rs.getString(3));
						request_user.put("userid", rs.getInt(4));
						request_user.put("gender", rs.getString(5));
						message.setRequest_user(request_user);
						return message;
					}
				});
	}

	/**
	 * 添加一条请求添加好友的推送
	 * 
	 * @param userid		请求者id	
	 * @param tar_userid	接收者id
	 * @param remark		备注
	 * @param eventForFriendAsking	事件id
	 * @return int  1 成功插入 -1 插入失败
	 */
	public int addPushMsg(int userid, int tar_userid, String remark,
			int eventForFriendAsking) {
		String sql = "insert into push_msg(eventid,ori_userid,tar_userid, remark,create_time) values(?, ?, ?, ?, now())";
		int ret = getBaseDAO().insert(sql, eventForFriendAsking, userid,
				tar_userid, remark);
		return ret > 0 ? 1 : -1;
	}
	
	public int updateMsg(int msgid, int userid, int state){
		String sql = "update push_msg set state=? where msgid=? and tar_userid=?";
		int ret = getBaseDAO().update(sql, state, msgid, userid);
		return ret;
	}
}
