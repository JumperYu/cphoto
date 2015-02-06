-- 2015-02-05 暂不用

-- 用户账号
DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `uid` int NOT NULL auto_increment,
  `username` varchar(32) default NULL,
  `nickname` varchar(32) default NULL,
  `password` varchar(32) default NULL,
  `passwordmd5` varchar(64) default NULL,
  `joindate` datetime default NULL,
  `joinip` varchar(15) default NULL,
  `lastlogintime` datetime default NULL,
  `lastloginip` varchar(15) default NULL,
  `state` TINYINT default 0 COMMENT '0:正常无状态;',
  `face` varchar(64) default NULL COMMENT '头像url',
  `level` int(5) default NULL,
  `exp` int(10) default NULL,
  `sign` varchar(200) default NULL,
  PRIMARY KEY  (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 朋友关系
DROP TABLE IF EXISTS `cp_friendship`;

CREATE TABLE `friendship` (
  `id` int NOT NULL auto_increment COMMENT 'id序号',
  `uid` int NOT NULL COMMENT '用户id',
  `frienduid` int NOT NULL COMMENT '用户的好友id',
  `groupid` int default NULL COMMENT '好友分组id',
  `addtime` datetime NOT NULL COMMENT '加为好友的时间',
  `state` TINYINT default 0 COMMENT '0:正常无状态;',
  PRIMARY KEY  (`id`),
  KEY `INDEX_FRIENDSHIP_GROUPID` (`groupid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 好友关系组
DROP TABLE IF EXISTS `friendgroup`;

CREATE TABLE `friendgroup` (
  `groupid` int NOT NULL auto_increment COMMENT '分组id',
  `groupname` varchar(50) default NULL COMMENT '分组名',
  `friendcount` int NOT NULL COMMENT '某个分组的人数',
  `createuid` int NOT NULL COMMENT '创建分组的用户id',
  `createtime` datetime NOT NULL COMMENT '创建分组的时间',
  `state` TINYINT default 0 COMMENT '0:正常无状态;',
  PRIMARY KEY  (`groupid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 用户留言
DROP TABLE IF EXISTS `leavemsg`;

CREATE TABLE `leavemsg` (
  `lid` int NOT NULL auto_increment COMMENT '留言id',
  `uida` int NOT NULL COMMENT '留言者(用户a给用户b留言)',
  `uidb` int NOT NULL COMMENT '用户a给用户b留言',
  `content` varchar(500) NOT NULL COMMENT '留言内容',
  `leavetime` datetime NOT NULL COMMENT '留言时间',
  `isread` bit(1) default 0 COMMENT '是否已读',
  PRIMARY KEY  (`lid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 留言回复
DROP TABLE IF EXISTS `leave_answer`;

CREATE TABLE `leave_answer` (
  `aid` int NOT NULL auto_increment COMMENT '留言回复id',
  `lid` int NOT NULL COMMENT '留言id',
  `uid` int NOT NULL,
  `replytime` datetime NOT NULL,
  `content` varchar(2000) default NULL,
  PRIMARY KEY  (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 小动作 类似于表情
DROP TABLE IF EXISTS `motion`;

CREATE TABLE `motion` (
  `mid` int) NOT NULL auto_increment COMMENT '小动作id',
  `uid` int default NULL COMMENT '用户',
  `motiondesc` varchar(100) default NULL COMMENT '小动作描述',
  `picname` varchar(100) default NULL COMMENT '小动作图片名',
  `addtime` datetime default NULL COMMENT '上传小动作图片的时间',
  PRIMARY KEY  (`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 消息盒子
DROP TABLE IF EXISTS `msgbox`;

CREATE TABLE `msgbox` (
  `mid` int(11) NOT NULL auto_increment,
  `senduid` int(11) default NULL COMMENT '发送消息的用户',
  `acceptuid` int(11) default NULL COMMENT '接收消息的用户',
  `msgtype` varchar(32) default NULL COMMENT '消息类型',
  `title` varchar(50) default NULL COMMENT '消息标题',
  `content` varchar(3000) default NULL COMMENT '消息内容',
  `createtime` datetime default NULL COMMENT '消息产生时间',
  `isread` bit(1) default NULL COMMENT '是否已读：0表示未读,1表已读',
  `isanonymity` int(1) default NULL COMMENT '是否匿名：0表示非匿名,1表匿名',
  PRIMARY KEY  (`mid`),
  UNIQUE KEY `SYS_C005350` (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8;

-- 消息

DROP TABLE IF EXISTS `msgboxcount`;

CREATE TABLE `msgboxcount` (
  `uid` int NOT NULL default '0',
  `short_count` int default '0' COMMENT '短消息 总数量',
  `short_unread` int default '0' COMMENT '短消息 未读数量',
  `leave_count` int default '0' COMMENT '留言 总数量',
  `leave_unread` int default '0' COMMENT '留言 未读数量',
  `comment_count` int default '0' COMMENT '评论 总数量',
  `comment_unread` int default '0' COMMENT '评论 未读数量',
  `system_count` int default '0' COMMENT '系统消息 总数量',
  `system_unread` int default '0' COMMENT '系统消息 未读数量',
  `friendreq_count` int default '0' COMMENT '好友请求 总数量',
  `friendreq_unread` int default '0' COMMENT '好友请求 未读数量',
  `gift_count` int default '0' COMMENT '礼物消息 总数量',
  `gift_unread` int default '0' COMMENT '礼物消息 未读数量',
  `motion_count` int default '0' COMMENT '小动作 总数量',
  `motion_unread` int default '0' COMMENT '小动作 未读数量',
  PRIMARY KEY  (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 提问
DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `qid` int NOT NULL auto_increment,
  `uid` int default NULL,
  `title` varchar(50) default NULL,
  `content` varchar(3000) default NULL,
  `state` varchar(30) default NULL,
  `gold` int default NULL,
  `readcount` int default NULL,
  `answercount` int default NULL,
  `complaincount` int default NULL,
  `sendtime` datetime default NULL,
  `endtime` datetime default NULL,
  `gameid` int default NULL,
  PRIMARY KEY  (`qid`),
  UNIQUE KEY `QID` (`qid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 问题回复
DROP TABLE IF EXISTS `questionanswer`;

CREATE TABLE `questionanswer` (
  `aid` int(11) NOT NULL auto_increment,
  `qid` int(11) default NULL,
  `uid` int(11) default NULL,
  `replytime` datetime default NULL,
  `content` varchar(2000) default NULL,
  `isbest` int(1) default NULL,
  PRIMARY KEY  (`aid`),
  UNIQUE KEY `AID` (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `questionasset`;

DROP TABLE IF EXISTS `talktagall`;

CREATE TABLE `talktagall` (
  `tagid` int(11) NOT NULL auto_increment COMMENT '标签id',
  `tagname` varchar(50) default NULL COMMENT '标签名',
  `tagcount` int(11) default NULL COMMENT '标签数量(所有人总数量)',
  PRIMARY KEY  (`tagid`),
  UNIQUE KEY `TAGID2` (`tagid`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

CREATE TABLE `questionasset` (
  `uid` int(11) NOT NULL,
  `qlv` int(11) default NULL,
  `exps` int(11) default NULL,
  `golds` int(11) default NULL,
  `isbestcount` int(11) default NULL,
  `addgoldtime` datetime default NULL,
  PRIMARY KEY  (`uid`),
  UNIQUE KEY `SYS_C005312` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `talktag`;

CREATE TABLE `talktag` (
  `tagid` int(11) NOT NULL auto_increment COMMENT '标签id',
  `uid` int(11) default NULL COMMENT '用户',
  `tagname` varchar(50) default NULL COMMENT '标签名',
  `tagcount` int(11) default NULL COMMENT '标签数量',
  PRIMARY KEY  (`tagid`),
  UNIQUE KEY `TALKTAG` (`tagid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `userbasic`;

CREATE TABLE `userbasic` (
  `uid` int(11) NOT NULL,
  `realname` varchar(32) default NULL COMMENT '真实姓名',
  `sex` varchar(2) default NULL COMMENT '性别',
  `bloodtype` varchar(2) default NULL COMMENT '血型',
  `birthday` datetime default NULL COMMENT '生日',
  `hometown` varchar(50) default NULL COMMENT '家乡',
  `place` varchar(50) default NULL COMMENT '现居住地',
  `display` decimal(10,0) default NULL,
  `work` varchar(50) default NULL COMMENT '工作',
  `gameage` int(3) default NULL COMMENT '游戏年龄',
  `playedgame` varchar(100) default NULL COMMENT '玩过的游戏',
  `disposition` varchar(100) default NULL COMMENT '职位',
  PRIMARY KEY  (`uid`),
  UNIQUE KEY `CMNTY_USER_BASIC_PK` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 投票 类似于点赞
DROP TABLE IF EXISTS `vote`;

CREATE TABLE `vote` (
  `voidid` int(11) NOT NULL auto_increment,
  `uid` int(11) default NULL,
  `voidtitle` varchar(50) default NULL,
  `voidtype` varchar(15) default NULL,
  `createtime` datetime default NULL,
  `begintime` datetime default NULL,
  `endtime` datetime default NULL,
  `showcount` int(11) default NULL,
  `joincount` int(11) default NULL,
  `discription` varchar(250) default NULL,
  `circle` varchar(32) default NULL,
  `hotcount` int(11) default NULL,
  `dingcount` int(11) default NULL,
  `caicount` int(11) default NULL,
  `replaycount` int(11) default NULL,
  `replytime` datetime default NULL,
  PRIMARY KEY  (`voidid`),
  UNIQUE KEY `CMNTY_VOTE_INFO_PK` (`voidid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `voteitem`;

CREATE TABLE `voteitem` (
  `mid` int(11) NOT NULL auto_increment,
  `uid` int(11) default NULL,
  `vid` int(11) default NULL,
  `message` varchar(250) default NULL,
  `caicount` int(11) default NULL,
  `dingcount` int(11) default NULL,
  `createtime` datetime default NULL,
  `img` varchar(30) default NULL,
  `photo` varchar(30) default NULL,
  PRIMARY KEY  (`mid`),
  UNIQUE KEY `CMNTY_VOTE_MESSAGE_PK` (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
