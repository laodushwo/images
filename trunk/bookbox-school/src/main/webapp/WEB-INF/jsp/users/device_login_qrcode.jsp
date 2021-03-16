<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>设备二维码登录</title>
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">

    <!-- CSS Code -->
    <link rel="stylesheet" href='<c:url value="/static/css/common/base.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <style type="text/css">
    #qrcode {
    	text-align: center;
    	margin: 30% auto;
    }
    </style>
    
    <!-- JavaScript Code -->
	<script type="text/javascript" src="<c:url value="/static/js/jquery-1.9.1.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/js/common.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/layer/layer.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/js/jquery.qrcode.min.js"/>"></script>

	<script type="text/javascript">
		$(function(){
			var outId = "${usersVO.outId}";
			if (isEmpty(outId)) {
				layer.msg("亲，只有班级管理员才能使用此功能哦！");
				return ;
			}
			// 判断是否存在活动
			common.getData(common.basePath +"/api/device/login/qrcode", {}, function(data) {
				console.log(data);
				if (data.success) {
					var deviceVO = data.models.deviceVO;
					if (null == deviceVO) {
						layer.msg("设备还未初始化，无法生成二维码");
						return ;
					}
					$('#qrcode').qrcode({
						render: "canvas",
// 						width: 250,
// 					    height: 250,
					    text: deviceVO.loginPwd
					});
					return ;
				}
				layer.msg("二维码生成失败，请联系管理员！");
			});
			
		})
	</script>
</head>
<body bgcolor="#FFFFFF">
	<div id="qrcode"></div>
</body>
</html>