<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>班级阅读排行榜</title>
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">

    <!-- CSS Code -->
    <link rel="stylesheet" href='<c:url value="/static/css/common/base.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <link rel="stylesheet" href='<c:url value="/static/css/users/read_ranking.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <!-- JavaScript Code -->
    <script type="text/javascript" src="<c:url value="/static/js/jquery-1.9.1.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/js/common.js"/>"></script>
	<script type="text/javascript">
	$(function() {
		
		var gradeName = "${usersVO.childrenVO.gradeVO.name}";
		$(".title").html(gradeName + "阅读排行榜");
		var gradeId = "${usersVO.childrenVO.gradeId}";
		common.getData(common.basePath + "/api/getByGradeBorrowBook", {"gradeId": gradeId}, function(data) {
			if (data.success) {
				var list = data.models.childrenList;
				var html = '';
				$(list).each(function(i, obj) {
					html += '<li>';
					html += '<div><span class="name">'+ obj.name +'</span><span class="_button">'+ obj.borrowCount +'本</span></div>';
					html += '</li>';
				});
				$(".content ul").html(html);
			}
		});
		
	})
	
	</script>
</head>

<body bgcolor="#FFFFFF">
	<div class="head_title">
		<span class="title">三年一班阅读排行榜</span>
	</div>
    <div class="content">
		<ul>
		
<!-- 			<li> -->
<%-- 				<div><span class="name">张三</span><span class="_button">321本</span></div> --%>
<!-- 			</li> -->
<!-- 			<li> -->
<%-- 				<div><span class="name">李四</span><span class="_button">11本</span></div> --%>
<!-- 			</li> -->
		</ul>
    </div>
</body>
</html>