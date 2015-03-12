-- 当前版本

use `cphoto`;

-- 用户表
drop table if EXISTS `cp_account`;
create table `cp_account` (
	`id` int NOT NULL AUTO_INCREMENT,
	`nickname` varchar(30) NOT NULL COMMENT '别名',
	`sex` varchar(30) NOT NULL COMMENT '性别',
	`age` varchar(30) NOT NULL COMMENT '年龄',
	`email` varchar(50) COMMENT '邮件',
	`telphone` varchar(30) COMMENT '手机',
	`userid` int NOT NULL COMMENT '用户唯一标识',
	`account` varchar(30) NOT NULL COMMENT '应用账号',
	`password` varchar(30) NOT NULL COMMENT '应用密码',
	PRIMARY KEY(`id`),
	UNIQUE KEY (`account`, `userid`),
	KEY INDEX_PWD (`password`)
)ENGINE=INNODB default charset utf8;

-- 主题
drop table `cp_subject`;
create table cp_subject(
	id int AUTO_INCREMENT,
	title varchar(60) NOT NULL COMMENT '标题',
	content varchar(255) NOT NULL COMMENT '主要内容',
  	pictureid int NOT NULL comment '图片id', 
	userid int NOT NULL COMMENT '用户id',
	nickname varchar(30) COMMENT '用户别名',
	state  int NOT NULL DEFAULT 1 COMMENT '主题状态',
	create_time datetime NOT NULL COMMENT '记录时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间',
  	PRIMARY KEY(id)
)ENGINE=INNODB default charset utf8;

-- 跟帖
drop table if exists cp_reply;
create table cp_reply(
	id int AUTO_INCREMENT,
	title varchar(60) NOT NULL COMMENT '标题',
	content varchar(255) NOT NULL COMMENT '主要内容',
	pictureid int NOT NULL comment '图片id',
	userid int NOT NULL COMMENT '回帖者id',
	nickname varchar(30) COMMENT '用户别名',
	subjectid int NOT NULL COMMENT '主题id',
	create_time datetime NOT NULL COMMENT '记录时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY(id)
)ENGINE=INNODB default charset utf8;

-- 跟帖
drop table if exists cp_reply;
create table cp_reply(
	id int AUTO_INCREMENT,
	title varchar(60) NOT NULL COMMENT '标题',
	content varchar(255) NOT NULL COMMENT '主要内容',
	pictureid int NOT NULL comment '图片id',
	userid int NOT NULL COMMENT '回帖者id',
	nickname varchar(30) COMMENT '用户别名',
	subjectid int NOT NULL COMMENT '主题id',
	create_time datetime NOT NULL COMMENT '记录时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY(id)
)ENGINE=INNODB default charset utf8;

-- 评论
drop table if exists cp_comment;
create table cp_comment(
	id int AUTO_INCREMENT,
	content varchar(255) NOT NULL COMMENT '评论内容',
	subjectid int COMMENT '主题id',
	replyid int COMMENT '回帖id',
	userid int NOT NULL COMMENT '用户id',
	nickname varchar(30) COMMENT '用户别名',
	create_time datetime NOT NULL COMMENT '记录时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY(id)
)ENGINE=INNODB default charset utf8;
