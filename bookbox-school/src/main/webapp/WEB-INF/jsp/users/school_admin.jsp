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
	<script type="text/javascript">
	$(function(){
	});
	</script>
</head>

<body bgcolor="#FFFFFF">
	<nav class="_main">
    	<div class="head"><img src="http://thirdwx.qlogo.cn/mmopen/NBxKbQdWCGQJZxuvBHaX7zXSYNJg8YUXmYlwNoMqlvNbRzNxa6LzZCOXNKGxWqpvZOnBicicOia1zVzACVjnKEuuMdRMOCpJJial/132"></div>
    	<div class="name">
			<ul>
				<li>
					&nbsp;<span>小明</span>&emsp;
					<span>男</span>
				</li>
				<li>【非米二号】学校管理员</li>
			</ul>	
    	</div>
    </nav>
	<div class="_main_section">
        <ul>
			<a href="<c:url value="/account/agent/total"/> "><li>
            	<span class="pic"><img src="<c:url value="/static/image/homepage/psgl.png"/>"></span>三年一班
            </li></a>
			<a href="<c:url value="/agent/borrow/view"/> "><li>
            	<span class="pic"><img src="<c:url value="/static/image/homepage/psgl.png"/>"></span>三年二班
            </li></a>
            <a href="<c:url value="/agent/users/list"/> "><li>
            	<span class="pic"><img src="<c:url value="/static/image/homepage/psgl.png"/>"></span>二年三班
            </li></a>
        </ul>    
    </div>
</body>
</html>