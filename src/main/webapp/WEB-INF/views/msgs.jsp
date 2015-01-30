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
				用户【${msg.request_user.nickname}】   请求添加你为好友  <a href="#" onclick="sendConfirm(${msg.msgid},${userid},1)">欣然同意</a> <a href="/v2_1/confirm_msg?msgid=${msg.msgid}&userid=${userid}&state=2">残忍拒绝</a>
			</div>
			<hr>
		</c:forEach>
		<script type="text/javascript">
			function sendConfirm(_msgid, _userid, _state) {
				$.ajax({
					type : "GET",
					url : "/v2_1/confirm_msg",
					data : {
						msgid : _msgid,
						userid : _userid,
						state : _state
					},
					success : function(result) {
						if(result.ret && result.ret == 1)
							alert("添加成功");
						else 
							alert("添加失败或拒绝添加");
						window.location.href = "/v2_1/index?userid=" + ${userid};
					}
				});
			}
		</script>
		<script src="/static/jquery/jquery-2.1.3.min.js" type="text/javascript"></script>
	</div>
</body>
</html>