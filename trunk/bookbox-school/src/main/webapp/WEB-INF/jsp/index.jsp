<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
<script type="text/javascript" src="<c:url value="/static/js/jweixin-1.2.0.js"/>"></script>
<script type="text/javascript" src="<c:url value="/static/js/jquery-1.9.1.min.js"/>"></script>

<script type="text/javascript">
$(function(){
	//添加【&connect_redirect=1】,用于防止网页授权时回调多次的问题
	window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbc966e0536c0678f&redirect_uri=http://xt.feimread.com/school/wx/check/user&response_type=code&scope=snsapi_userinfo&state=${u}&connect_redirect=1#wechat_redirect";
});
</script>
</head>
<body>
</body>
</html>
