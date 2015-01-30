<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>罗列所有主题</title>
<style type="text/css">
p {
	text-align: left;
}
</style>
<script src="/static/jquery/jquery-2.1.3.min.js" type="text/javascript"></script>
</head>
<body>
	<div>
		<fieldset title="发布主题">
		<form action="/v2_1/add_subject" method="post" enctype="multipart/form-data">
		文件:<input type="file" name="file"/>
		标题：<input type="text" name="title"/>
		内容:<input type="text" name="content"/>
		<input type="hidden" name="userid" value="${userid}" />
		<input type="submit">
		</form>
		</fieldset>
	</div>
	<ol></ol>
	<script type="text/javascript">
		$(function() {
			var $body = $("body");
			var $div = $("ol");
			var $update = "<p style=''><a href=\"javascript:void();\">点击更换新</a></p>";
			for (var i = 0; i < 10; i++) {
				var $img = "<p><img src=\"http://image.bradypod.com/cphoto/20150124/1422092980.jpg\" width=\"300\" height=\"100\"/></p>";
				var $author = "<p>作者:小鱼  这是标题   一分钟前 </p>";
				var $zan = "<p>10个人赞过:@小鱼1、@小鱼2、@小鱼3等</p>";
				var $comment = "<p>评论数:2</p><p>@小鱼1: 啊哈</p><p>@小鱼2: 啊哈</p><p>@小鱼2: 啊哈</p><p>等等</p><hr/>";
				$div.append("<li>").append($img).append($author).append($zan)
						.append($comment).append("</li>");
			}
			$body.append($update);
			$("a")
					.on(
							'click',
							function(e) {
								delta = 3;
								for (var i = 0; i < delta; i++) {
									var $img = "<p><img src=\"http://image.bradypod.com/cphoto/20150124/1422092980.jpg\" width=\"300\" height=\"100\"/></p>";
									var $author = "<p>作者:小鱼  这是标题   一分钟前 </p>";
									var $zan = "<p>10个人赞过:@小鱼1、@小鱼2、@小鱼3等</p>";
									var $comment = "<p>评论数:2</p><p>@小鱼1: 啊哈</p><p>@小鱼2: 啊哈</p><p>@小鱼2: 啊哈</p><p>等等</p><hr/>";
									$div.append("<li>").append($img).append(
											$author).append($zan).append(
											$comment).append("</li>");
								}
							});
		});
		// 分页展示
		function listPage(pageNo, pageSize) {
			$.ajax({
				type : "GET",
				url : "/v2_1/find_users",
				data : {
					nickname : encodeURIComponent($("#content").val()),
					userid :  "${userid}"
				},
				success : function(result) {
					
				}
			});
		}
	</script>
</body>
</html>