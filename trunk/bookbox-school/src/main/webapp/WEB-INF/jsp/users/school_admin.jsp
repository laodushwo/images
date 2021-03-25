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
    <link rel="stylesheet" href='<c:url value="/static/css/users/school_admin.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <script type="text/javascript" src="<c:url value="/static/js/jquery-1.9.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/static/js/common.js"/>?v=<%=System.currentTimeMillis() %>"></script>
	<script type="text/javascript">
	$(function(){
		
		$("#user_image").attr("src", "${usersVO.headImage}");
		$("#user_name").html("${usersVO.nickName}");
		var sex = '';
		switch("${usersVO.sex}") {
		case '0': sex = '未知'; break;
		case '1': sex = '男'; break;
		case '2': sex = '女'; break;
		}
		$("#user_sex").html(sex);
		$("#user_role").html("【${schoolName}】管理员");
		
		// 根据学校ID查询该学校所有班级
		common.getData(common.basePath +"/api/school/admin/grade", {"schoolId": "${schoolId}"}, function(data) {
			if(data.success) {
				var list = data.models.gradeList;
				var html ='';
				$(list).each(function(i, obj) {
					html += '<a href="<c:url value="/school/view/index/'+ obj.id +'/'+ obj.newName +'"/>"><li>';
	            	html += '<span class="pic"><img src="<c:url value="/static/image/homepage/psgl.png"/>"></span>'+ obj.newName;
	            	html += '</li></a>';
				});
				$("._main_section ul").append(html);
			}
		});
	});
	</script>
</head>

<body bgcolor="#FFFFFF">
	<nav class="_main">
    	<div class="head"><img id="user_image" src=""></div>
    	<div class="name">
			<ul>
				<li>
					&nbsp;<span id="user_name"></span>&emsp;
					<span id="user_sex"></span>
				</li>
				<li id="user_role"></li>
			</ul>	
    	</div>
    </nav>
	<div class="_main_section">
        <ul>
        </ul>    
    </div>
</body>
</html>