<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>添加-好友</title>
</head>
<body>
	<div id="nav">
		<a href="/v2_1/index?userid=${userid}">返回首页</a> <a href="">朋友圈</a>
	</div>
	<div id="search-div">
		<div>
			查找 <input id="content" type="text" value="" />
			<button id="search">查询</button>
		</div>
		<div id="msg"></div>
	</div>
	<script src="/static/jquery/jquery-2.1.3.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			$("#search").on("click", function(e) {
				$.ajax({
					type : "GET",
					url : "/v2_1/find_users",
					data : {
						nickname : encodeURIComponent($("#content").val()),
						userid :  "${userid}"
					},
					success : function(result) {
						$("#msg").html("");
						if(result.info && result.info.length > 0){
							$.each(result.info, function(i,n){
							var user = "<hr><div>"
								+ "<div>头像 " + "xxx" + "</div>"
								+ "<div>昵称  :" + n.nickname  + "</div>"
								+ "<div>平台号 : " +  n.userid + "</div>"
								+ "<div>性别 : 男</div>"
								+ "<span>年龄 : "+ n.age + "</span>"
							    + "</div>"
							    + "<a href=\"#\">添加</a> 备注<input type=\"text\" value=\"加个朋友吧\"/><input type=\"hidden\" value=\""+ n.userid +"\"/>";
								+ "<hr>";
							$("#msg").append(user);
							$("a").on("click", function(e) {
								var _url = "/v2_1/add_friend";
								$this = $(this);
								console.log($this)
								$.ajax({
									type : "GET",
									url : _url,
									data : {
										tar_userid : $this.next().next().val(),
										userid :  "${userid}",
										remark : encodeURIComponent($this.next().val())
									},
									success : function(result) {
										if(result.ret){
											alert("添加请求");
											$("#msg").html("");
										}
									}
								});
							});
							});
						}
					}
				});
			});
	
		});
	</script>
</body>
</html>

</html>