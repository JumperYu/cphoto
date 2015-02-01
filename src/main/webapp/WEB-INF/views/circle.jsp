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
div {
margin: 5px 0;
}
</style>
<script src="/static/jquery/jquery-2.1.3.min.js" type="text/javascript"></script>
<script src="/static/app/showtime.js" type="text/javascript"></script>
</head>
<body>
	<div id="nav">
		<a href="/v2_1/index?userid=${userid}">返回首页</a>
	</div>
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
	<p style=''><a id="up" href="javascript:void(0);">点击更新</a></p>
	<ol></ol>
	<p style=''><a id="down" href="javascript:void(0);">点击更多</a></p>
	<script type="text/javascript">
		var _userid = "${userid}", _index = 1, _count = 100, timeline = 0; // 总页数
	
		$(function() {
			loadNextPage(_index);
			$(document).on('click', "a", function(e) {
				var $a = $(this);
				if($a.attr("id") == "up"){
					var subjectid = $("ol li").first().find("input").first().val();
					var timeline = $("ol li").first().find("input").last().val();
					loadNextPage(_index, subjectid, timeline, "uptodate");	
				} else {
					var subjectid = $("ol li").last().find("input").first().val();
					var timeline = $("ol li").last().find("input").last().val();
					loadNextPage(_index, subjectid, timeline, "last");
				}
				//
			});
		});
		function loadNextPage(pageIndex, subjectid, timeline, method) {
			$.ajax({
				type : "GET",
				url : "/v2_1/list_subjects",
				data : {
					userid : _userid,
					subjectid : subjectid,
					timeline : timeline,
					method : method,
					index : pageIndex
				},
				success : function(result) {
					//var $body = $("body");
					var $div = $("ol");
					console.log(result.subjects);
					if(result.ret && result.ret == 1) {
						$.each(result.subjects, function(i, n){
							var $img = "<li><p><img src=\"" + n.pic_url + "?300x300" + "\" width=\"300\" height=\"100\"/></p>";
							var $author = "<p>作者:<b>@" + n.nickname  + "    </b><b>" + n.title  + "</b>  <b>" + showtime(n.create_time) + "</b></p>";
							console.log(n);
							var $zan = "<p>10个人赞过:@小鱼1、@小鱼2、@小鱼3等</p>";
							var $comment = "<p>评论数:" + n.comments_page.count  + "</p>";
							var $input = "<input type='hidden' value='" + n.id + "' />" + "<input type='hidden' value='" + n.create_time + "' />" + "</li>";
							$.each(n.comments, function(j, m) {
								$comment += "<p>@" + m.nickname + " : " + m.content + "</p>";
							});
							if(method == "uptodate")
								$div.prepend($img + $author + $zan + $comment + $input);
							else
								$div.append($img + $author + $zan + $comment + $input);
						});
						
					}
					
				}
			});
		}
	</script>
</body>
</html>