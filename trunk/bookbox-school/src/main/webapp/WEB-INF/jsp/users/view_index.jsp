<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>管理员</title>
	<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0 user-scalable=no, width=device-width">

    <!-- CSS Code -->
    <link rel="stylesheet" href='<c:url value="/static/css/common/base.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <link rel="stylesheet" href='<c:url value="/static/css/users/view_index.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <script type="text/javascript" src="<c:url value="/static/js/jquery-1.9.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/static/js/common.js"/>?v=<%=System.currentTimeMillis() %>"></script>
	<script type="text/javascript">
	$(function(){
		$(".title").html("${gradeName}");
	});
	</script>
</head>

<body bgcolor="#FFFFFF">
	<div class="head_title">
		<span class="title"></span>
	</div>
	<div class="_main_section">
        <ul>
			<a href="<c:url value="/activity/admin/show/together/${gradeId}"/>"><li>
            	<span class="pic"><img src="<c:url value="/static/image/homepage/psgl.png"/>"></span>活动展示
            </li></a>
			<a href="<c:url value="/school/admin/grade/book/page/${gradeId}"/>"><li>
            	<span class="pic"><img src="<c:url value="/static/image/homepage/psgl.png"/>"></span>班级图书
            </li></a>
            <a href="<c:url value="/activity/admin/read/ranking/${gradeId}"/>"><li>
            	<span class="pic"><img src="<c:url value="/static/image/homepage/psgl.png"/>"></span>借阅排行
            </li></a>
        </ul>    
    </div>
</body>
</html>