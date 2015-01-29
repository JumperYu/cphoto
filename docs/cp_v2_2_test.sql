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








