<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>我的信息</title>
</head>
<body>
	<input id="hide_userid" value="" type="hidden" />
	<div id="msg" style="display: none;"></div>
	<div id="person" style="display: none;"></div>
	<script src="/static/jquery/jquery-2.1.3.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			var div_up = "<hr><div>"
				+ "<div>头像 " + "xxx" + "</div>"
				+ "<div>昵称  :" + "${nickname}"  + "</div>"
				+ "<div>平台号 : " +  "${userid}" + "</div>"
				+ "<div>性别 : 男</div>"
				+ "<span>年龄 : "+ "${age}" + "</span>"
			  	+ "</div>";
				var div_down = "<div>"
				+ "<a href=\"/v2_1/circle/index?userid=${userid}\">朋友圈</a> <a href=\"/v2_1/friends/list?userid=" + ${userid} + "\">我的好友</a> <a href=\"/v2_1/friends/add?userid=" + "${userid}"  + "\">添加好友</a>"
				+ "</div><hr>";
				$("#person").show().html(div_up + div_down);
				// 开启轮询
				$("#hide_userid").val("${userid}");
				setInterval(function(){
					$.ajax({
						type : "GET",
						url : "/v2_1/long_poll",
						data : {
							userid : $("#hide_userid").val()
						},
						success : function(result) {
							$.each(result.events, function(i, n){
								console.log(n);
								if(n.count > 0){
									var msg = "<p><a href=\"/v2_1/message/list?eventid=" + n.eventid + "&userid=" + $("#hide_userid").val() +  "\">您有" + n.count +"条新消息</a></p>";
									console.log(msg);
									$("#msg").show().html(msg);
								}
							});
						}
					});
				}, 5000);
		});
	</script>
</body>
</html>