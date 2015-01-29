<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消息具现化</title>
</head>
<body>
	<div id="nav">
		<a href="/static/">返回首页</a> <a href="">朋友圈</a> <a href="">添加好友</a>
	</div>
	<div>
		<c:forEach items="${msgs}" var="msg">
			<hr>
			<div>
				用户【${msg.request_user.nickname}】   请求添加你为好友  <a href="">欣然同意</a> <a href="">残忍拒绝</a>
				
			</div>
			<hr>
		</c:forEach>
	</div>
</body>
</html>