
create database if not EXISTS `cphoto` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

use `cphoto`;

-- 个人资料
drop table if EXISTS `cp_identity`;
create table cp_identity (
	cp_id int(11) NOT NULL AUTO_INCREMENT,
	cp_nickname varchar(30) NOT NULL,
	cp_gender varchar(30) COMMENT '性别',
	cp_age varchar(30) COMMENT '年龄',
	cp_email varchar(30) COMMENT '邮件',
	cp_telphone varchar(30) COMMENT '手机',
	cp_userid int(11) COMMENT '关联账号',
	UNIQUE KEY (cp_userid),
	PRIMARY KEY(cp_id)
);

-- 应用账号
set AUTO_INCREMENT=0;
drop table if EXISTS `cp_user`;
create table `cp_user` (
	cp_userid int(11) NOT NULL AUTO_INCREMENT,
	cp_account varchar(30) NOT NULL COMMENT '应用账号',
	cp_pwd varchar(30) NOT NULL COMMENT '应用密码',
	PRIMARY KEY(cp_userid),
	UNIQUE KEY (cp_account),
	KEY INDEX_PWD (cp_pwd)
);

-- 社交账号
drop table if EXISTS `cp_social`;

create table `cp_social` (
	cp_socialid int(11) NOT NULL AUTO_INCREMENT,
	cp_platform varchar(30) NOT NULL COMMENT '社交平台',
	cp_openid varchar(30) NOT NULL COMMENT '开放标识',
	cp_openpwd varchar(30) NOT NULL COMMENT '开放密码-保留字段',
	cp_userid varchar(30) NOT NULL COMMENT '关联用户标识',
	PRIMARY KEY(cp_socialid)
);

-- 好友关系
drop table if EXISTS `cp_friendship`;
create table `cp_friendship` (
	cp_userid int(11),
	cp_relatedid int (11),
	cp_groupid int(11)
	primary key(cp_userid, cp_relatedid)
);

drop table if EXISTS `cp_group`;
create table `cp_group` (
	cp_userid int(11),
	cp_groupid int(11),
	cp_groupname varchar(30),
	primary key(cp_userid, cp_groupid)
);

-- pics 
drop table if EXISTS `photo_wall`;
create table `photo_wall` (
	id int(11) NOT NULL AUTO_INCREMENT,
	title varchar(30) NOT NULL DEFAULT '' COMMENT '标题',
	content varchar(80) NOT NULL DEFAULT '' COMMENT '主体内容',
	pic_name varchar(30) NOT NULL DEFAULT '' COMMENT '图片名',
	pic_path varchar(60) NOT NULL DEFAULT '' COMMENT '文件路径',
	pic_url varchar(80) NOT NULL DEFAULT '' COMMENT '文件url路径'
	create_time DATETIME DEFAULT NULL	COMMENT '创建时间',
	update_time DATETIME DEFAULT NULL	COMMENT '更新时间',
	cphoto varchar(30) NOT NULL COMMENT '应用账号', -- deprecate
	userid int NOT NULL COMMENT '应用账号', -- instead cphoto
	PRIMARY KEY(id),
	KEY INDEX_USERID(userid)
);

-- 评论
drop table if EXISTS `                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        `;
create table `photo_comment`(
	int id NOT NULL AUTO_INCREMENT,
	userid int NOT NULL AUTO_INCREMENT,
	comment varchar(255) NOT NULL,
	photo_id int NOT NULL,
	primary key(id)
);

-- 节点
drop table if EXISTS `cp_node`;
create table `cp_node`(
	int node_id NOT NULL AUTO_INCREMENT,
	int next_node,
	primary key(node_id)
);

-- 表情
-- 表情

-- 推送消息
drop table if EXISTS `push_msg`;
create table `push_msg` (
	msgid int NOT NULL AUTO_INCREMENT,
	eventid int NOT NULL COMMENT '事件',
	ori_userid int NOT NULL COMMENT '源用户id',
	tar_userid int NOT NULL COMMENT '目标用户id',
	remark varchar(80) COMMENT '备注',
	state int NOT NULL DEFAULT 0 COMMENT '状态',
	create_time datetime NOT NULL COMMENT '记录时间',
	PRIMARY KEY(msgid)
)default charset utf8;


-- 消息事件
drop table if EXISTS `msgevent`;
create table `msgevent` (
	eventid int NOT NULL AUTO_INCREMENT,
	event_name varchar(60) NOT NULL COMMENT '事件名',
	event_desc varchar(60) COMMENT '事件描述', 
	PRIMARY KEY(eventid)
)default charset utf8;


---------------
-- 主题
drop table if exists cp_subject;
create table cp_subject(
	id int AUTO_INCREMENT,
	title varchar(60) NOT NULL COMMENT '标题',
	content varchar(255) NOT NULL COMMENT '主要内容',
    photoid int NOT NULL comment '图片id', 
	userid int NOT NULL COMMENT '用户id',
	state  int NOT NULL DEFAULT 1 COMMENT '主题状态',
	create_time datetime NOT NULL COMMENT '记录时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY(id)
)default charset utf8;
-- 跟帖
drop table if exists cp_reply;
create table cp_reply(
	id int AUTO_INCREMENT,
	title varchar(60) NOT NULL COMMENT '标题',
	content varchar(255) NOT NULL COMMENT '主要内容',
	photoid int NOT NULL comment '图片id',
	userid int NOT NULL COMMENT '回帖者id',
	subjectid int NOT NULL COMMENT '主题id',
	create_time datetime NOT NULL COMMENT '记录时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY(id)
)default charset utf8;
-- 评论
drop table if exists cp_comment;
create table cp_comment(
	id int AUTO_INCREMENT,
	content varchar(255) NOT NULL COMMENT '评论内容',
	subjectid int COMMENT '主题id',
	replyid int COMMENT '回帖id',
	userid int NOT NULL COMMENT '用户id',
	create_time datetime NOT NULL COMMENT '记录时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY(id)
)default charset utf8;
-- 图片
drop table if exists cp_picture;
create table cp_picture(
	id int AUTO_INCREMENT,
	pic_name varchar(60) NOT NULL COMMENT '图片原名',
	pic_path varchar(60) NOT NULL COMMENT '服务器存储目录',
	pic_url varchar(80) NOT NULL DEFAULT '' COMMENT '文件url路径',
	content_type varchar(60) COMMENT '文件类型',
	userid int NOT NULL COMMENT '用户id',
	create_time datetime NOT NULL COMMENT '记录时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY(id)
)default charset utf8;
