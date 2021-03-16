<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>基础资料</title>
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">

    <!-- CSS Code -->
    <link rel="stylesheet" href='<c:url value="/static/css/common/base.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <link rel="stylesheet" href='<c:url value="/static/css/users/user_detail.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    
    <!-- JavaScript Code -->
	<script type="text/javascript" src="<c:url value="/static/js/jquery-1.9.1.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/js/common.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/layer/layer.js"/>"></script>

	<script type="text/javascript">
		$(function(){
			
			
		})
		
		function save(url) {
			var name = $("#name").val();
			if (isEmpty(name)) {
				layer.msg("小孩姓名不能为空"); return ;
			}
			var mobile = $("#mobile").val();
			if (isEmpty(mobile)) {
				layer.msg("手机号码不能为空"); return ;
			}
			var pattern = /^1[34578]\d{9}$/;
			if (!pattern.test(mobile)) {
				layer.msg("手机号码格式不对"); return ;
			}
			var cardId = $("#cardId").val();
// 			if (cardId == null || cardId == '') {
// 				layer.msg("卡号不能为空"); return ;
				
// 			}
			var schoolName = $("#schoolName").val();
			if(isEmpty(schoolName)) {
				layer.msg("学校不能为空"); return ;
			}
			var gradeCode = $("#gradeCode").val();
			if(isEmpty(gradeCode)) {
				layer.msg("班级不能为空"); return ;
			}
			common.getData(
				url,
    			{"name": name, "mobile": mobile, "cardId": cardId, "schoolName": schoolName, "gradeCode": gradeCode}, 
    			function(data) {
    				var msg = data.success ? msg = "操作成功" : msg = data.errorMsg[0];
    				layer.msg(msg);
    				setTimeout(function() { location.reload(); }, 2000);
    			}
	    	);
		}
	</script>
</head>
<body bgcolor="#FFFFFF">
	<nav class="_main">
	
   		<div class="head_content">
	   		<img width="100%" height="100%" src="<c:url value="/static/image/homepage/p3.png"/>">
    	</div>
    	
    	<div class="div_content"><p class="title_span">基础资料</p></div><br/>
    	
	   	<!-- 主要内容 -->
	   	<form:form action="" name="chlidrenForm" modelAttribute="childrenVO" method="POST">
		    <ul class="sell">
		    	<li>
		    		<label>*</label><span class="title">小孩姓名：</span>
		    		<span><form:input type="text" maxlength="5" class="input" path="name" placeholder="请输入读者全名" /></span>
		    	</li>
		    	<li>
		    		<label>*</label><span class="title">手机号码：</span>
		    		<span><form:input type="text" maxlength="11" class="input" path="mobile" placeholder="请输入手机号码" /></span>
		    	</li>
		    	<li>&emsp;</li>
		    	<li>
		    		<span class="title">卡&emsp;&emsp;号：</span>
		    		<span><form:input type="text" maxlength="10" class="input" path="cardId" placeholder="请输入读者证上的卡号" oninput="value=value.replace(/[^\d]/g,'')" /></span>
		    	</li>
		    	<li>
		    		<label>*</label><span class="title">学&emsp;&emsp;校：</span>
		    		<span><form:input type="text" maxlength="20" class="input" path="schoolName" placeholder="请输入学校名称" /></span>
		    	</li>
		    	<li>
		    		<label>*</label><span class="title">班&emsp;&emsp;级：</span>
		    		<span><form:input type="text" maxlength="8" class="input" path="gradeCode" placeholder="请输入八位数的班级编号" /></span>
		    	</li>
		    	<li>
			    	<div class="add_body">
			        	<c:if test="${childrenVO.id == null}">
				       		<a class="button" onclick="save('<c:url value="/api/addChildren"/>')" >绑定读者卡</a>
			        	</c:if>
			        	<c:if test="${childrenVO.id != null}">
				       		<a class="button" onclick="save('<c:url value="/api/editChildren"/>')" >保存</a>
			        	</c:if>
			        </div>
		        </li>
		    </ul>
		    
	    </form:form>
	</nav>
</body>
</html>