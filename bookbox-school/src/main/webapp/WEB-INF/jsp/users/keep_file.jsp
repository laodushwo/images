<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>读书存档</title>
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">

    <!-- CSS Code -->
    <link rel="stylesheet" href='<c:url value="/static/css/common/base.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <link rel="stylesheet" href='<c:url value="/static/css/users/keep_file.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    
    <!-- JavaScript Code -->
	<script type="text/javascript" src="<c:url value="/static/js/jquery-1.9.1.min.js"/>"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="<c:url value="/static/js/common.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/layer/layer.js"/>"></script>

	<script type="text/javascript">
		$(function(){
			$.ajax({
 				url: common.basePath + "/wx/signture",
	 		  	data: {
	 			  	'url':location.href.split('#')[0]
	 		  	},
		 		success: function(json) {
		 			if(json.success){
		 				wx.config({
	 						debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。  
	 						appId: json.models.appId, // 必填，公众号的唯一标识  
	 						timestamp: ""+json.models.timestamp, // 必填，生成签名的时间戳  
	 						nonceStr: json.models.nonceStr, // 必填，生成签名的随机串  
	 						signature: json.models.signature, // 必填，签名，见附录1  
	 						jsApiList: [
 								"scanQRCode", "chooseWXPay" // 微信扫一扫接口
 							]
	 					});
		 				wx.ready(function(){
	 				        document.querySelector("#qrIsbnCode").onclick = function () {
	 				            wx.scanQRCode({
	 				                needResult: 1,
	 				                desc: "scanQRCode desc",
	 				                scanType : ["checkJsApi", "qrCode", "barCode" ], // 可以指定扫二维码还是一维码，默认二者都有 *
	 				                success: function (res) {
	 				                    var code = res.resultStr;
	 				                	bind(code);
	 				                }
	 				            });
	 				        }
	 					});
		 				wx.error(function(res){});
		 			} 
		 		}
			});
		})
		
		function bind(code){
			var url = common.basePath + "/api/qrcode/keep/"+code;
			$.ajax({
	 		  	url: url,
	 		  	success: function(json) {
	 			  	if (json.success) {
	 				  	layer.msg("存档成功");
	 				  	$("#isbn").val("");
	 			  	} else {
	 				  	layer.msg(json.errorMsg[0]);
	 			  	}
	 		  	}
		 	});
		}
		
		function inp() {
			var isbn = $("#isbn").val();
			if (isEmpty(isbn)) {
				 layer.msg("亲, 书籍编号不能为空哦！");
				 return ;
			}
			if (isbn.length < 11) {
				layer.msg("书籍编号长度不对哦");
				return ;
			}
			bind(isbn);
		}
	</script>
</head>
<body class="body">
	<div class="head">
   		<span><img src="<c:url value="/static/image/homepage/psgl.png"/>"><p>读书存档</p></span>
	</div>	
	<div class="tip">
		<span>请将手机镜头对准书本背后的ISBN条形码，或者输入13位数的号码</span>
	</div>
	<div class="content">
		<div class="l_qr">
			<img src="<c:url value="/static/image/homepage/psgl.png"/>">
			<p><span class="button qr" id="qrIsbnCode">扫条形码</span></p>
		</div>
		<div class="r_qr">
			<input type="text" class="input" maxlength="13" placeholder="请输入13位数的号码" id="isbn" oninput="value=value.replace(/[^\d]/g,'')" />
			<p><a class="button inp" onclick="inp()" >输入数字</a></p>
		</div>
	</div>
</body>
</html>