<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>好友-通讯录</title>
</head>
<body>
	<div id="nav">
		<a href="/static/">返回首页</a> <a href="">朋友圈</a> <a href="">添加好友</a>
	</div>
	<div>
		<c:forEach items="${users}" var="user">
			<hr>
			<div>
				<div>头像  xxx</div>
				<div>昵称 : ${user.nickname}</div>
				<div>平台号 : ${user.userid}</div>
				<div>性别 : 男</div>
				<span>年龄 : ${user.age}</span>
			</div>
			<hr>
		</c:forEach>
	</div>
</body>
</html>