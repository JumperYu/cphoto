-- 创建数据库
create database if not EXISTS `cphoto` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

use `cphoto`;

-- 账号
drop table if EXISTS `account`;
create table `account` (
	id int(11) NOT NULL AUTO_INCREMENT,
	cphoto varchar(30) NOT NULL DEFAULT '' COMMENT '应用账号',
	username varchar(30) NOT NULL DEFAULT '' COMMENT '玩家姓名',
    gender VARCHAR(10) NOT NULL	COMMENT '性别',
	login_account VARCHAR(30) NOT NULL	COMMENT '登录账号',
	login_pwd VARCHAR(30) NOT NULL	COMMENT '登录密码',
	create_time DATETIME DEFAULT NULL	COMMENT '创建时间',
	update_time DATETIME DEFAULT NULL	COMMENT '更新时间',
	PRIMARY KEY(id),
	UNIQUE KEY (cphoto, login_account),
	KEY INDEX_PWD (login_pwd)
);

-- 图片主题
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