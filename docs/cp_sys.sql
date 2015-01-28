-- 菜单栏
drop table if exists application;
create table application (
	appid varchar(15) NOT NULL COMMENT '标识',
	parentId varchar(15) DEFAULT NULL COMMENT '父标识',
	leaf bit(1) NOT NULL DEFAULT 0 COMMENT '叶子节点 (0:不是;1:是)',
    expanded bit(1) NOT NULL DEFAULT 0 COMMENT '展开节点 (0:不展开;1:展开)',
    text varchar(50) NOT NULL COMMENT '节点名称',
	html varchar(100) NOT NULL COMMENT '节点html内容',
	PRIMARY KEY (appid)
);
insert into application(appid, parentId, leaf, expanded, text, html) values('1001', NULL, 0, 1, '系统管理', '');
insert into application(appid, parentId, leaf, expanded, text, html) values('10001', '1001', 1, 0, '用户管理', 'http://localhost:8080/cphoto/static/panel.html');
insert into application(appid, parentId, leaf, expanded, text, html) values('10002', '1001', 1, 0, '菜单配置', 'http://localhost:8080/cphoto/static/panel.html');
