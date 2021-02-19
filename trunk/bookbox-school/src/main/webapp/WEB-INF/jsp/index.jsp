<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>home</title>
    <!--网页标题左侧显示-->
    <link rel="icon" href="/school/assets/img/bitbug_favicon.ico" type="image/x-icon">
    <!--收藏夹显示图标-->
    <link rel="shortcut icon" href="/school/assets/img/bitbug_favicon.ico" type="image/x-icon">
    <script>
	window.onload = function() {
		if(!!window.ActiveXObject || "ActiveXObject" in window){
		      alert('您好，本系统检测到您使用的版本过低，如使用的是IE8，请更换到IE9及以上版本，如使用的是360兼容版本，请更换到360极速版本，感谢您的支持！');
		}
		
        //屏蔽键盘事件
        document.onkeydown = function (){
            var e = window.event || arguments[0];
            //F12
            if(e.keyCode == 123){
                return false;
            //Ctrl+Shift+I
            }else if((e.ctrlKey) && (e.shiftKey) && (e.keyCode == 73)){
                return false;
            //Shift+F10
            }else if((e.shiftKey) && (e.keyCode == 121)){
                return false;
            //Ctrl+U
            }else if((e.ctrlKey) && (e.keyCode == 85)){
                return false;
            }
        };
        //屏蔽鼠标右键
        document.oncontextmenu = function (){
            return false;
        }
	}
	</script>

    <style type="text/css">
    .indexImg {
/*     	opacity: .9; */
    }
    .slide-content, .slide-content>h3 {
    	color: #f2eff3;
	    letter-spacing: 3px;
	    font-family: monospace;
    }
    </style>
</head>

<body>
    <h1>Welcome On !!!</h1>
</body>

</html>
