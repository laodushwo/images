<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>书柜管理</title>
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">

    <!-- CSS Code -->
    <link rel="stylesheet" href='<c:url value="/static/css/common/base.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <link rel="stylesheet" href='<c:url value="/static/css/users/activity.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <!-- JavaScript Code -->
    <script type="text/javascript" src="<c:url value="/static/js/jquery-1.9.1.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/js/common.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/layer/layer.js"/>"></script>
	<script type="text/javascript">
	$(function() {
		
	});
	
	function validChildren(type) {
		var childrenId = "${usersVO.childrenVO.id}";
		if (isEmpty(childrenId)) {
			layer.msg("无法访问，基础资料还未填写哦！"); 
			return ;
		}
		
		// 班级管理员
		var outId = "${usersVO.outId}";
		if (isEmpty(outId)) {
			layer.msg("亲，只有班级管理员权限才能查看哦哦！");
			return ;
		}
		
		switch (type) {
			case 1: location.href = '<c:url value="/book/grade/book/ud"/>'; break;
			case 2: location.href = '<c:url value="/book/grade/drift/ud"/>'; break;
		}
	}
	</script>
</head>

<body bgcolor="#FFFFFF">
	<div class="head_title">
		<span class="title">书柜管理</span>
	</div>
    <div class="content">
		<ul>
			<li>
				<p>每次上下架书籍清单</p>
				<div class="xdhg">
					<a href="javascript:void(0);" onclick="validChildren(1)" class="_button">点击查看</a>
				</div>
			</li>
			<li>
				<p>图书漂流清单</p>
				<div class="xdhg">
					<a href="javascript:void(0);" onclick="validChildren(2)" class="_button">点击查看</a>
				</div>
			</li>
		</ul>
    </div>
</body>
</html>