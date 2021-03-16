<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>思维导图</title>
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <!-- CSS Code -->
    <link rel="stylesheet" href='<c:url value="/static/css/common/base.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <link rel="stylesheet" href="<c:url value="/static/css/ztree/bootstrapStyle.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <link rel="stylesheet" href='<c:url value="/static/css/users/class_mind_map.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <!-- JavaScript Code -->
	<script type="text/javascript" src="<c:url value="/static/js/jquery-1.9.1.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/js/common.js"/>?v=<%=System.currentTimeMillis() %>"></script>
	<script type="text/javascript" src="<c:url value="/static/layer/layer.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/js/jquery.ztree.core.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/js/jquery.ztree.exedit.js"/>"></script>
	<script type="text/javascript">
		$(function() {
			common.getData(common.basePath +"/api/findUserByClassMindMap", {"bookId": $("#bookId").val()}, function(data){
				if(data.success){
					var bookMindList = data.models.bookMindList;
					processList(bookMindList);
				}
			});
		})
		
		function processList(bookMindList){
			var html ='';
			$(bookMindList).each(function(i, obj){
				var content = obj.content;
				var id = "d" + i;
				html +='<input type="hidden" id="'+ id +'" value=\''+ content +'\' />';
				html +='<a href="javascript:void(0);" onclick="showMindMap(\''+ id +'\')" >';
				html +='<div class="children"><img src="<c:url value="/static/image/homepage/m1.png"/>"><p class="name">'+ obj.childrenName +'</p></div>';
	        	html +='</a>';
			});
			$(".content").append(html);
		}
		
		function showMindMap(id) {
			var content = $("#"+id).val();
			layer.open({
	    		type: 1,
	    		title: '',
	    		shadeClose: true,
	    		shade: 0.3,
	    		maxmin: false,
	    		area: ['90%', '90%'],
	    		content: $("#read-rank-date-main"),
	    		yes:function(index, layero){}
	    	});
			
			var setting = {
				view: {
					selectedMulti: false
				},
				check: {
					enable: true
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				edit: {
					enable: false,
					showRemoveBtn: false
				},
				callback: {
		            beforeDrag: beforeDrag
		        }
			};
			
			var zNodes = JSON.parse(content);
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		}
		
		// 禁止拖拽
		function beforeDrag(treeId, treeNodes) {
	        return false;
	    }
	</script>
</head>
<body bgcolor="#FFFFFF">
	<input type="hidden" id="bookId" value="${bookId}"> 

	<div class="content">
<!--     	<a href="#"> -->
<!--    			<div class="children"> -->
<%--    				<img src="<c:url value="/static/image/homepage/m1.png"/>"> --%>
<!--    				<p class="name">张三</p> -->
<!--    			</div> -->
<!--     	</a> -->
<!--     	<a href="#"> -->
<!--    			<div class="children"> -->
<%--    				<img src="<c:url value="/static/image/homepage/m1.png"/>"> --%>
<!--    				<p class="name">张三</p> -->
<!--    			</div> -->
<!--     	</a> -->
<!--     	<a href="#"> -->
<!--    			<div class="children"> -->
<%--    				<img src="<c:url value="/static/image/homepage/m1.png"/>"> --%>
<!--    				<p class="name">张三</p> -->
<!--    			</div> -->
<!--     	</a> -->
<!--     	<a href="#"> -->
<!--    			<div class="children"> -->
<%--    				<img src="<c:url value="/static/image/homepage/m1.png"/>"> --%>
<!--    				<p class="name">张三</p> -->
<!--    			</div> -->
<!--     	</a> -->
<!--     	<a href="#"> -->
<!--    			<div class="children"> -->
<%--    				<img src="<c:url value="/static/image/homepage/m1.png"/>"> --%>
<!--    				<p class="name">张三</p> -->
<!--    			</div> -->
<!--     	</a> -->
<!--     	<a href="#"> -->
<!--    			<div class="children"> -->
<%--    				<img src="<c:url value="/static/image/homepage/m1.png"/>"> --%>
<!--    				<p class="name">张三</p> -->
<!--    			</div> -->
<!--     	</a> -->
<!--     	<a href="#"> -->
<!--    			<div class="children"> -->
<%--    				<img src="<c:url value="/static/image/homepage/m1.png"/>"> --%>
<!--    				<p class="name">张三</p> -->
<!--    			</div> -->
<!--     	</a> -->
    </div>
    
    <div id="read-rank-date-main" style="display: none;">
       	<ul id="treeDemo" class="ztree"></ul>
	</div>
</body>
</html>
