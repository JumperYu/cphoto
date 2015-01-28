-- 查询事件个数
select a.eventid, a.event_name, count(1) from msgevent a left join push_msg b
on a.eventid=b.eventid and b.tar_userid=1;