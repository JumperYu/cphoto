-- 查询用户是否存在
SELECT id, nickname, sex, age, email, telphone, userid, account, `password` 
FROM cp_account where account='xiaoyu' and `password`='123';

-- 插入数据
insert into cp_account(nickname, sex, age, email, telphone, userid, account, `password`)
values(?,?,?,?,?,?,?,?);

-- 查询朋友
select id, nickname, sex, age, email, telphone, t1.userid, account, `password` from cp_account t1, cp_friendship t2 
where t1.userid=t2.cp_relatedid and t2.cp_userid='1422518896';

-- 查询具体事件
select b.msgid,b.eventid, a.nickname,a.userid,a.sex from cp_account a, push_msg b
where a.userid=b.ori_userid and b.eventid='1001' and b.tar_userid='1422524281' and b.state='0';

-- 查询自己的主题
SELECT
	a.id,
	a.title,
	a.content,
	unix_timestamp(a.create_time) create_time,
	unix_timestamp(a.update_time) updae_time,
	b.pic_name,
	b.pic_url,
	b.content_type,
	unix_timestamp(b.update_time) pic_update_time 
FROM
	cp_subject a,
	cp_picture b
WHERE
	a.userid = '1422524281'
AND a.pictureid = b.id
AND a.userid = b.userid
ORDER BY
	a.create_time DESC;


-- 查询参与的主题
SELECT
	a.id,
	a.title,
	a.content,
	a.userid,
	a.nickname,
	unix_timestamp(a.create_time) create_time,
	unix_timestamp(a.update_time) updae_time,
	b.pic_name,
	b.pic_url,
	b.content_type,
	unix_timestamp(b.update_time) pic_update_time
FROM
	cp_subject a,
	cp_picture b
WHERE
	a.pictureid = b.id
AND a.id='1577';


-- 查出朋友的帖子
SELECT
	a.id,
	a.title,
	a.content,
	unix_timestamp(a.create_time) create_time,
	unix_timestamp(a.update_time) updae_time,
	b.pic_name,
	b.pic_url,
	b.content_type,
	unix_timestamp(b.update_time) pic_update_time
FROM
	cp_subject a,
	cp_picture b
WHERE
	a.pictureid = b.id
AND EXISTS (
	SELECT
		t.cp_relatedid
	FROM
		cp_friendship t
	WHERE
		t.cp_userid = '1422524281'
	AND a.userid = t.cp_relatedid
)
ORDER BY
	a.create_time DESC;

	
-- 查看帖子联结sql
SELECT
	a.id,
	a.title,
	a.content,
	a.userid,
	a.nickname,
	unix_timestamp(a.create_time) create_time,
	unix_timestamp(a.update_time) updae_time,
	b.pic_name,
	b.pic_url,
	b.content_type,
	unix_timestamp(b.update_time) pic_update_time
FROM
	cp_subject a,
	cp_picture b
WHERE
	a.pictureid = b.id
AND a.userid = b.userid
AND a.id IN (
	SELECT
		a.id
	FROM
		cp_subject a,
		cp_picture b
	WHERE
		a.userid = '1422524281'
	AND a.pictureid = b.id
	AND a.userid = b.userid
	UNION ALL
		SELECT
			a.id
		FROM
			cp_subject a,
			cp_picture b
		WHERE
			a.pictureid = b.id
		AND EXISTS (
			SELECT
				c.subjectid
			FROM
				cp_reply c
			WHERE
				b.userid = '1422524281'
			AND a.id = c.subjectid
		)
		UNION ALL
			SELECT
				a.id
			FROM
				cp_subject a,
				cp_picture b
			WHERE
				a.pictureid = b.id
			AND EXISTS (
				SELECT
					t.cp_relatedid
				FROM
					cp_friendship t
				WHERE
					t.cp_userid = '1422524281'
				AND a.userid = t.cp_relatedid
			)
);

-- 查找主题下面的评论
SELECT
	id,
	content,
	subjectid,
	replyid,
	userid,
	UNIX_TIMESTAMP(create_time) create_time,
	UNIX_TIMESTAMP(update_time) update_time
FROM
	cp_comment
WHERE
	subjectid = 1;

-- 查找回帖
SELECT
	a.id,
	a.title,
	a.content,
	a.userid,
	a.nickname,
	unix_timestamp(a.create_time) create_time,
	unix_timestamp(a.update_time) updae_time,
	b.pic_name,
	b.pic_url,
	b.content_type,
	unix_timestamp(b.update_time) pic_update_time
FROM
	cp_reply a,
	cp_picture b
WHERE
	a.pictureid = b.id
AND a.userid = b.userid
AND a.subjectid='1577';



	