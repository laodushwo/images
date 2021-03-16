<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>共读一本书</title>
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">

    <!-- CSS Code -->
    <link rel="stylesheet" href='<c:url value="/static/css/common/base.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <link rel="stylesheet" href='<c:url value="/static/css/users/read_together.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    
    <!-- JavaScript Code -->
	<script type="text/javascript" src="<c:url value="/static/js/jquery-1.9.1.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/js/common.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/layer/layer.js"/>"></script>

	<script type="text/javascript">
		$(function(){
			
			// 判断是否存在活动
			common.getData(common.basePath +"/api/findTogeter", {}, function(data) {
				var together = data.models.activityTogetherVO;
				if (null != together) {
					$("#bookId").val(together.bookId);
					$("#name").val(together.bookName);
					$("#author").val(together.bookAuthor);
					var spantxt = together.bookIntro.substring(0, 10) + "...";
					$("#intro").val(spantxt);
				} else {
					layer.msg("活动不见了，请联系班级管理员...");
				}
			});
			
		})
	</script>
</head>
<body bgcolor="#FFFFFF">
	<div class="head_title">
		<span class="title">共读一本书</span>
	</div>
	<ul class="sell">
    	<li>
    		<span class="title">书名：</span>
    		<span><input type="text" class="input" maxlength="20" id="name" placeholder="请输入书籍名称" disabled /></span>
    	</li>
    	<li>
    		<span class="title">作者：</span>
    		<span><input type="text" class="input" id="author" placeholder="请输入作者" disabled/></span>
    	</li>
    	<li>
    		<span class="title">简介：</span>
    		<span><input type="text" class="input" id="intro" placeholder="请输入简介" disabled/></span>
    	</li>
    	<li>
    		<span class="title">作业：</span>
    		<span><input type="text" class="input" value="1. 做两张思维导图" readonly="readonly" /></span>
    	</li>
    	<li>
    		<span class="title">&emsp;&emsp;&emsp;</span>
    		<span><input type="text" class="input" value="2. 写不少于300字的读后感" readonly="readonly" /></span>
    	</li>
    	<li>
    		<span class="title">&emsp;&emsp;&emsp;</span>
    		<span><input type="text" class="input" value="3. 背诵你认为最有趣的一段" readonly="readonly" /></span>
    	</li>
    </ul>
    <div class="remark">
	   	<p>一个学期精选2-4本书籍, 活动开始后2个月, 阅读课做公开的讨论: </p>
	   	<p>1. 选出10张有特点的思维导图</p>
	   	<p>2. 选出10篇读后感，进行引导性讨论</p>
	   	<p>3. 选10个小读者背诵自己认为有趣的片段</p>
    </div>
</body>
</html>