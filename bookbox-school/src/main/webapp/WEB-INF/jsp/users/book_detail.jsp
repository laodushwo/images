<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>图书详情</title>
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <!-- CSS Code -->
    <link rel="stylesheet" href='<c:url value="/static/css/common/base.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <link rel="stylesheet" href='<c:url value="/static/css/users/book_info.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <!-- JavaScript Code -->
	<script type="text/javascript" src="<c:url value="/static/js/jquery-1.9.1.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/js/common.js"/>?v=<%=System.currentTimeMillis() %>"></script>
	<script type="text/javascript" src="<c:url value="/static/layer/layer.js"/>"></script>
	<script type="text/javascript">
		$(function(){
			var childrenId = "${usersVO.childrenVO.id}";
			if (isEmpty(childrenId)) {
				// 如果未填写基础资料，则不能看到思维导图和读后感
				$(".mind_map, .read_feel, .xdhg").css("display", "none");
			}
			
			// 判断用户是否填写过思维导图
			var bookMindId = $("#bookMindId").val();
			if (!isEmpty(bookMindId)) {
				$(".create").css("display", "none");
			} 
			// 判断用户是否填写过读后感
			var bookReadFeelId = $("#bookReadFeelId").val();
			if (!isEmpty(bookReadFeelId)) {
				$(".xdhg").css("display", "none");
			} 
			
			var bookId = $("#bookId").val();
			// 跳转查看所有思维导图
			$(".link").click(function() {
				location.href = '<c:url value="/book/class/mind/map/'+ bookId +'"/>';
			});
			// 创建思维导图
			$(".create").click(function() {
				location.href = '<c:url value="/book/mind/map/'+ bookId +'"/>';
			});
			
			// 点击写读后感按钮的显示/隐藏
			$("._button").click(function() {
				var _pc = $(".pl_content");
				$("#intro").val("");
				if (_pc.css("display") == 'none') {
					_pc.slideDown();
			    } else {
			    	_pc.slideUp();
			    }
			});
			
			// 初始化读后感列表
			loadReadFeel(bookId);
			
			// 点击提交，添加读后感
			$(".tj").click(function() {
				var intro = $("#intro").val();
				if (intro == "") {
					layer.msg("读后感不能为空哦！", {icon:1, time:3000, shift: 6});
					return ;
				}
				if (intro.length < 5) {
					layer.msg("感悟太少，请尽情发挥哦！", {icon:1, time:3000, shift: 6});
					return ;
				}
				common.getData(common.basePath + "/api/addReadFeel", {"bookId": bookId, "content": intro}, function(data) {
					if (data.success) {
						layer.msg("提交成功！");
						$(".pl_content, .zwpl").css("display", "none");
						location.reload();
					} else {
						layer.msg(data.errorMsg[0], {icon:1, time:3000, shift: 6});
					}
				});
			});
		})
		
		function maxLimit() {
			if($("#intro").val().length > 200){
	            var num = $("#intro").val().substr(0, 200);
	            $("#intro").val(num);
	            layer.msg("读后感不能超过200字！");
	        }
		}
		
		function loadReadFeel(bookId) {
			common.getData(common.basePath + "/api/readFeelList", {"bookId": bookId}, function(data) {
				if (data.success) {
					var list = data.models.bookReadFeelList;
					if (list == undefined) {
						return ;
					}
					if (list.length == 0) {
						$(".zwpl").css("display", "block");
						return ;
					}
					$(".radio_one").html("");
					var html = '';
					var myUserId = "${usersVO.id}";
					$(list).each(function(i, obj) {
						html += '<li class="article_li">';
						// 如果是自己的读后感，则可以删除
						if (obj.userId == myUserId) {
							html += '<img class="imgDel" src="<c:url value="/static/image/homepage/AA.jpg"/>" onclick="delMyReadFeel(\''+ obj.id +'\');"/>';
						}
						html += '<div>';
						html += '&nbsp;';
						html += '<img id="head_image" width="50%" height="100%" src="'+ obj.headImage +'" />';
						html += '&nbsp;';
						html += '<span class="nn">'+ obj.childrenName +'</span>';
						html += '&nbsp;';
						html += '<span class="cd">'+ common.timeStamp2String(obj.gmtCreate, 12) +'</span>';
						html += '&nbsp;';
						html += '</div>';
						html += '<div class="pl_contnet">'+ obj.content +'</div>';
						html += '</li>';
					});
					$(".radio_one").html(html);
				}
			});
		}
		
		function delMyReadFeel(id) {
			layer.confirm("确定要删除吗？", {
				closeBtn: false,
				btn: ['非常肯定', '再考虑下']
			}, function() {
				layer.close(layer.index);
				common.getData( common.basePath +"/api/myReadFeelDel", {"id": id}, function(data){
					if (data.success) {
						layer.msg("删除成功");
						setTimeout(function() {
							window.location.reload();
						}, 1000);
					}
				}); 
			});
		}
	</script>
</head>
<body bgcolor="#FFFFFF">
	<input type="hidden" id="bookId" value="${bookVO.id}"> 
	<input type="hidden" id="bookMindId" value="${bookMindVO.id}"> 
	<input type="hidden" id="bookReadFeelId" value="${bookReadFeelVO.id}"> 
	
	<div class="book_title">
		<p class="book_name">${bookVO.name}</p>
		<p class="book_author">${bookVO.author}</p>
		<p class="intro">简介：</p>
		<p class="intro_content">${bookVO.intro}</p>
	</div>
	<div class="hr"></div>
	
	<div class="mind_map">
		<p class="bold">阅读</p>
		<a href="javascript:void(0);" class="link">
   			<div><span><img src="<c:url value="/static/image/homepage/swdt.png"/>"></span><p>查看所有</p></div>
   		</a>
   		<a href="javascript:void(0);" class="create">
   			<span>创建思维导图</span>
   		</a>
	</div>	
	
	<div class="hr"></div>		

	<div class="read_feel">
		<p class="bold">读后感</p>
	 	<div class="tab-content tab-content-1">
			<div class="pl_content">
				<div class="pl_r"><input type="button" class="tj" value="提交"/></div>
		    	<textarea cols="50%" rows="3" id="intro" class="intro" placeholder="请写出你读完此书后想法和观点" onPaste="maxLimit()" onKeyUp="maxLimit()" oninput="maxLimit()"></textarea>
	    	</div>
	    	<div class="zwpl">
    			暂无内容, 赶紧抢沙发哦！
    		</div>
	    	<ul class="radio_one">
<!-- 	    		<li class="article_li"> -->
<!--    					<div> -->
<!--    						&nbsp; -->
<!-- 						<img id="head_image" width="50%" height="100%" src="http://file.feimread.com:8080/image/file/headimage/202006/4ee11301-b6a7-47f5-94d5-4e07a40fdba7.png" /> -->
<!-- 						&nbsp; -->
<%-- 						<span class="nn">凡人</span> --%>
<!-- 						&nbsp; -->
<%-- 						<span class="cd">2020.08.20</span> --%>
<!-- 						&nbsp; -->
<!--    					</div> -->
<!--    					<div class="pl_contnet">水电费第三方第三方士大夫士大夫沙发士大夫大是否打赏法发顺啥飞洒发沙发上飞洒发生法师法师法师发大水发丰沙发沙发沙发沙发阿是发送到发送到发送到发沙发士大夫大厦是打发士大夫大沙发所发生的</div> -->
<!--    				</li> -->
<!--    				<li class="article_li"> -->
<!--    					<div> -->
<!--    						&nbsp; -->
<!-- 						<img id="head_image" width="50%" height="100%" src="http://file.feimread.com:8080/image/file/headimage/202006/4ee11301-b6a7-47f5-94d5-4e07a40fdba7.png" /> -->
<!-- 						&nbsp; -->
<%-- 						<span class="nn">凡人</span> --%>
<!-- 						&nbsp; -->
<%-- 						<span class="cd">2020.08.20</span> --%>
<!-- 						&nbsp; -->
<!--    					</div> -->
<!--    					<div class="pl_contnet">水电费第三方第三方士大夫士大夫沙发士大夫大是否打赏法发顺丰沙发沙发沙发沙发阿是发送到发送到发送到发沙发士大夫大厦是打发士大夫大沙发所发生的</div> -->
<!--    				</li> -->
	    	</ul>
		</div>
	</div>	
	
	<div class="xdhg">
		<a class="_button">写读后感</a>
	</div>
</body>
</html>
