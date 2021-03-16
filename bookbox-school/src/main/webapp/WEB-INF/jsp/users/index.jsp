<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>读者中心</title>
	<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0 user-scalable=no, width=device-width">

    <!-- CSS Code -->
    <link rel="stylesheet" href='<c:url value="/static/css/common/base.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <link rel="stylesheet" href='<c:url value="/static/css/users/index.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <!-- JavaScript Code -->
   	<script type="text/javascript" src="<c:url value="/static/js/jquery-1.9.1.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/js/common.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/layer/layer.js"/>"></script>
	<script type="text/javascript">
	$(function(){
		
		// 初始化加载热门图书-1.热门,2.最新
		pageBookList(1);
		
		// 是否显示活动按钮
		var childrenId = "${usersVO.childrenVO.id}";
		if (!isEmpty(childrenId)) {
			common.getData(common.basePath +"/api/findTogeter", {}, function(data) {
				var together = data.models.activityTogetherVO;
				if (null != together) {
					$(".activity").css("display", "block");
				}
			});
		}
		
		
		// 点击按钮切换列表
		$(".wrap label").click(function() {
			$(".wrap label").removeClass("current").addClass("other");
			$(this).removeClass("other").addClass("current");
			var id = $(this).children().attr("id");
			id == 'hot' ? pageBookList(1) : pageBookList(2);
		});
		
		// 书籍搜索
		$(".btn_search").click(function() {
			var name = $("#bookName").val();
			if (isEmpty(name)) {
				layer.msg("请输入书籍名称搜索"); return ;
			}
			location.href = '<c:url value="/book/search/book/'+ name +'"/>';
			$("#bookName").val("");
		});
		
		// 跳转活动展示页面
		$(".dbutton").click(function() {
			location.href = '<c:url value="/activity/show/together"/>';
		});
	})
	
	// 首页书籍列表
	function pageBookList(type) {
		common.getData(common.basePath + "/api/page/book/" + type, {}, function(data) {
			if (data.success) {
				var list = data.models.pageBookList;
				var html = '';
				$(list).each(function(i, obj) {
					html += '<a href="<c:url value="/book/detail/'+ obj.isbn +'"/>">';
					html += '<div class="child">';
					html += '<div class="book_image">';
					html += '<img alt="" src="'+ obj.image +'">';
					html += '<p class="book_p">'+ obj.name +'</p><p class="book_p">'+ obj.author +'</p>';
					html += '</div>';
					html += '</div>';
					html += '</a>';
				});
				$(".top100").html(html);
			}
		});
	}
	
	function validChildren(type) {
		var childrenId = "${usersVO.childrenVO.id}";
		if (isEmpty(childrenId)) {
			layer.msg("基础资料还未填写哦！"); return ;
		}
		
		var cardId = "${usersVO.childrenVO.cardId}";
		
		switch (type) {
			case 1: location.href = '<c:url value="/book/keep/file"/>'; break;
			case 2: location.href = '<c:url value="/book/mybook"/>'; break;
			case 3: location.href = '<c:url value="/school/school/book/page"/>'; break;
			case 4: location.href = '<c:url value="/school/grade/book/page"/>'; break;
			case 5: 
				if (isEmpty(cardId)) {
					layer.msg("还未绑卡，无法查看哦！"); return ;
				}
				location.href = '<c:url value="/book/booklog"/>'; break;
		}
	}
	</script>
</head>

<body bgcolor="#FFFFFF">

	<div class="search_box">
		<input type="text" class="input" placeholder="输入小朋友喜欢的书籍" id="bookName" />
		<span class="btn_search"><img class="search_img" src="<c:url value="/static/image/homepage/fdj.png"/>"></span>
	</div>
	<div class="scroll">
		<ul>
			<a onclick="validChildren(2)" href="#"><li>
    			<div><span><img src="<c:url value="/static/image/homepage/zy11.png"/>"></span><p>我的书架</p></div>
    		</li></a>
    		<a onclick="validChildren(1)" href="#"><li>
    			<div><span><img src="<c:url value="/static/image/homepage/zy11.png"/>"></span><p>读书存档</p></div>
    		</li></a>
    	</ul>
   	</div>
	<div class="clean"></div>
	
    <div class="center">
    	<ul>
    		<a onclick="validChildren(3)" href="#"><li>
    			<div><span><img src="<c:url value="/static/image/homepage/m1.png"/>"></span><p>学校图书</p></div>
    		</li></a>
			<a onclick="validChildren(4)" href="#"><li>
    			<div><span><img src="<c:url value="/static/image/homepage/m2.png"/>"></span><p>班级图书</p></div>
    		</li></a>
    		<a onclick="validChildren(5)" href="#"><li>
    			<div><span><img src="<c:url value="/static/image/homepage/m3.png"/>"></span><p>借还记录</p></div>
    		</li></a>
    	</ul>
    </div>
    
 	<div class="wrap">
	    <label class="current">
	        <span id="hot">热门图书</span>
	    </label>
	    <label class="other">
	        <span id="new">最新图书</span>
	    </label>	   
	</div>
	
    <div class="top100">
<!--     	<a href="#"> -->
<!--     		<div class="child"> -->
<!--     			<div class="book_image"> -->
<!--     				<img alt="" src="http://file.feimread.com:8080/image/detail/202004/37cde88b-3d1f-4ef5-b2b6-478194704e26.jpg"> -->
<!--     				<p class="book_p">老铁是怎么炼成的</p><p class="book_p">很哇塞的妹子</p> -->
<!--     			</div> -->
<!--     		</div> -->
<!--     	</a> -->
<!--     	<a href="#"> -->
<!--     		<div class="child"> -->
<!--     			<div class="book_image"> -->
<!--     				<img alt="" src="http://file.feimread.com:8080/image/detail/202004/37cde88b-3d1f-4ef5-b2b6-478194704e26.jpg"> -->
<!--     				<p class="book_p">老铁是怎么炼成的</p><p class="book_p">很哇塞的妹子</p> -->
<!--     			</div> -->
<!--     		</div> -->
<!--     	</a> -->
<!--     	<a href="#"> -->
<!--     		<div class="child"> -->
<!--     			<div class="book_image"> -->
<!--     				<img alt="" src="http://file.feimread.com:8080/image/detail/202004/37cde88b-3d1f-4ef5-b2b6-478194704e26.jpg"> -->
<!--     				<p class="book_p">老铁是怎么炼成的</p><p class="book_p">很哇塞的妹子</p> -->
<!--     			</div> -->
<!--     		</div> -->
<!--     	</a> -->
<!--     	<a href="#"> -->
<!--     		<div class="child"> -->
<!--     			<div class="book_image"> -->
<!--     				<img alt="" src="http://file.feimread.com:8080/image/detail/202004/37cde88b-3d1f-4ef5-b2b6-478194704e26.jpg"> -->
<!--     				<p class="book_p">老铁是怎么炼成的</p><p class="book_p">很哇塞的妹子</p> -->
<!--     			</div> -->
<!--     		</div> -->
<!--     	</a> -->
<!--     	<a href="#"> -->
<!--     		<div class="child"> -->
<!--     			<div class="book_image"> -->
<!--     				<img alt="" src="http://file.feimread.com:8080/image/detail/202004/37cde88b-3d1f-4ef5-b2b6-478194704e26.jpg"> -->
<!--     				<p class="book_p">老铁是怎么炼成的</p><p class="book_p">很哇塞的妹子</p> -->
<!--     			</div> -->
<!--     		</div> -->
<!--     	</a> -->
<!--     	<a href="#"> -->
<!--     		<div class="child"> -->
<!--     			<div class="book_image"> -->
<!--     				<img alt="" src="http://file.feimread.com:8080/image/detail/202004/37cde88b-3d1f-4ef5-b2b6-478194704e26.jpg"> -->
<!--     				<p class="book_p">老铁是怎么炼成的</p><p class="book_p">很哇塞的妹子</p> -->
<!--     			</div> -->
<!--     		</div> -->
<!--     	</a> -->
<!--     	<a href="#"> -->
<!--     		<div class="child"> -->
<!--     			<div class="book_image"> -->
<!--     				<img alt="" src="http://file.feimread.com:8080/image/detail/202004/37cde88b-3d1f-4ef5-b2b6-478194704e26.jpg"> -->
<!--     				<p class="book_p">老铁是怎么炼成的</p><p class="book_p">很哇塞的妹子</p> -->
<!--     			</div> -->
<!--     		</div> -->
<!--     	</a> -->
<!--     	<a href="#"> -->
<!--     		<div class="child"> -->
<!--     			<div class="book_image"> -->
<!--     				<img alt="" src="http://file.feimread.com:8080/image/detail/202004/37cde88b-3d1f-4ef5-b2b6-478194704e26.jpg"> -->
<!--     				<p class="book_p">老铁是怎么炼成的</p><p class="book_p">很哇塞的妹子</p> -->
<!--     			</div> -->
<!--     		</div> -->
<!--     	</a> -->
<!--     	<a href="#"> -->
<!--     		<div class="child"> -->
<!--     			<div class="book_image"> -->
<!--     				<img alt="" src="http://file.feimread.com:8080/image/detail/202004/37cde88b-3d1f-4ef5-b2b6-478194704e26.jpg"> -->
<!--     				<p class="book_p">老铁是怎么炼成的</p><p class="book_p">很哇塞的妹子</p> -->
<!--     			</div> -->
<!--     		</div> -->
<!--     	</a> -->
    </div>
    
    <div class="activity">
		<button class="dbutton">活动</button>
	</div>
</body>
</html>