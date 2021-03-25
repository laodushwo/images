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
			common.getData(common.basePath +"/api/findTogether", {}, function(data) {
				var together = data.models.activityTogetherVO;
				if (null != together) {
					$("#activityId").val(together.id);
					$("._button").html("关闭活动");
					$("#bookId").val(together.bookId);
					$("#name").val(together.bookName);
					$("#author").val(together.bookAuthor);
					var spantxt = together.bookIntro.substring(0, 10) + "...";
					$("#intro").val(spantxt);
				} else {
					$("._button").html("开启活动");
				}
			});
			
			// 搜索书籍
			$("#name").change(function() {
				var name = $(this).val();
				if (isEmpty(name)) {
					layer.msg("请输入书籍名称"); return ;
				}
				
				common.getData(common.basePath +"/api/search/fullbook", {"name": name}, function(data) {
					if (data.success) {
						var bookList = data.models.bookList;
						if (bookList.length == 0) {
							layer.msg("书籍不存在，请确保书籍名称输入正确！");
							return ;
						}
						var bookVO = bookList[0];
						$("#bookId").val(bookVO.id);
						$("#author").val(bookVO.author);
						var spantxt = bookVO.intro.substring(0, 10) + "...";
						$("#intro").val(spantxt);
					}
				});
			});
			
			// 点击提交按钮
			$("._button").click(function() {
				var activity = $("#activityId").val();
				isEmpty(activity) ? save() : update();
			});
			
		})
		
		function save() {
			var name = $("#name").val();
			if (isEmpty(name)) {
				layer.msg("书籍名称必填"); return ;
			}
			var author = $("#author").val();
			if (isEmpty(author)) {
				layer.msg("书籍作者必填"); return ;
			}
			var intro = $("#intro").val();
			if (isEmpty(intro)) {
				layer.msg("书籍简介必填"); return ;
			}
			
			common.getData(common.basePath +"/api/addTogeter", 
				{"id": $("#bookId").val(), "name": name, "author": author, "intro": intro}, 
				function(data) {
					if (data.success) {
						layer.msg("活动开启成功，即将跳转！");
						setTimeout(function() {
							location.href = '<c:url value="/user/index"/>';
						}, 2000);
						return ;
					}
					layer.msg(data.errorMsg[0], {icon:1, time:3000, shift: 6});
			});
		}
		
		function update() {
			layer.confirm("确定要关闭活动吗？", {
				closeBtn: false,
				btn: ['非常肯定', '再考虑下']
			}, function() {
				layer.close(layer.index);
				common.getData(common.basePath +"/api/updateTogeter", {}, function(data) {
					if (data.success) {
						layer.msg("关闭成功，即将跳转！");
						setTimeout(function() {
							location.href = '<c:url value="/user/index"/>';
						}, 2000);
						return ;
					}
					layer.msg(data.errorMsg[0], {icon:1, time:3000, shift: 6});
				});
			});
		}
	</script>
</head>
<body bgcolor="#FFFFFF">
	<input type="hidden" id="activityId"/>
	<input type="hidden" id="bookId"/>
	
	<div class="head_title">
		<span class="title">共读一本书</span>
	</div>
	<ul class="sell">
    	<li>
    		<span class="title">书名<label class="xing">*</label>：</span>
    		<span><input type="text" class="input" maxlength="20" id="name" placeholder="请输入书籍名称" /></span>
    	</li>
    	<li>
    		<span class="title">作者<label class="xing">*</label>：</span>
    		<span><input type="text" class="input" id="author" placeholder="请输入作者" /></span>
    	</li>
    	<li>
    		<span class="title">简介<label class="xing">*</label>：</span>
    		<span><input type="text" class="input" id="intro" placeholder="请输入简介" /></span>
    	</li>
    	<li>
    		<span class="title">作业&nbsp;：</span>
    		<span><input type="text" class="input" value="1. 做两张思维导图" readonly="readonly" /></span>
    	</li>
    	<li>
    		<span class="title">&nbsp;&emsp;&emsp;&emsp;</span>
    		<span><input type="text" class="input" value="2. 写不少于300字的读后感" readonly="readonly" /></span>
    	</li>
    	<li>
    		<span class="title">&nbsp;&emsp;&emsp;&emsp;</span>
    		<span><input type="text" class="input" value="3. 背诵你认为最有趣的一段" readonly="readonly" /></span>
    	</li>
    </ul>
    <div class="remark">
	   	<p>一个学期精选2-4本书籍, 活动开始后2个月, 阅读课做公开的讨论: </p>
	   	<p>1. 选出10张有特点的思维导图</p>
	   	<p>2. 选出10篇读后感，进行引导性讨论</p>
	   	<p>3. 选10个小读者背诵自己认为有趣的片段</p>
    </div>
    <div class="start_activity">
		<a class="_button">开启活动</a>
	</div>
</body>
</html>