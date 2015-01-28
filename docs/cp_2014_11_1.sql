
create database if not EXISTS `cphoto` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

use `cphoto`;

-- 个人资料
drop table if EXISTS `cp_identity`;
create table cp_identity (
	cp_id int(11) NOT NULL AUTO_INCREMENT,
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
);

drop table if EXISTS `cp_group`;
create table `cp_group` (
	cp_groupid int(11),
	cp_groupname varchar(30)
);

drop table if EXISTS `photo_wall`;
create table `photo_wall` (
	id int(11) NOT NULL AUTO_INCREMENT,
	title varchar(30) NOT NULL DEFAULT '' COMMENT '标题',
	content varchar(80) NOT NULL DEFAULT '' COMMENT '主体内容',
	pic_name varchar(30) NOT NULL DEFAULT '' COMMENT '图片名',
	pic_path varchar(60) NOT NULL DEFAULT '' COMMENT '文件路径',
	create_time DATETIME DEFAULT NULL	COMMENT '创建时间',
	update_time DATETIME DEFAULT NULL	COMMENT '更新时间',
	cphoto varchar(30) NOT NULL COMMENT '应用账号',
	PRIMARY KEY(id),
	KEY INDEX_CPHOTO(cphoto)
);