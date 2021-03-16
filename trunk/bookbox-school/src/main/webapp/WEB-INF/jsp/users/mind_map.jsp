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
    <!-- JavaScript Code -->
	<script type="text/javascript" src="<c:url value="/static/js/jquery-1.9.1.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/js/common.js"/>?v=<%=System.currentTimeMillis() %>"></script>
	<script type="text/javascript" src="<c:url value="/static/layer/layer.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/js/jquery.ztree.core.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/js/jquery.ztree.exedit.js"/>"></script>
	<script type="text/javascript">
		var setting = {
			view: {
				//addHoverDom: addHoverDom,
				//addDiyDom: addDiyDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false,
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
				enable: true,
				showRemoveBtn: false
			},
			callback: {
	            beforeDrag: beforeDrag,
				//beforeEditName: zTreeBeforeEditName,//进行编辑之前
				beforeRename: zTreeBeforeRename,//重命名节点之前
				onRename: zTreeOnRename

	        }
		};

		function zTreeBeforeRename(treeId, treeNode, newName) {
			
			console.log(treeId);
			console.log(newName);

		}

		function zTreeOnRename(event, treeId, treeNode, isCancel) {
// 			alert(treeNode.tId + ", " + treeNode.name);
			var spantxt = $("#" + treeNode.tId + "_span").html();
			if (spantxt.length > 9) {
				spantxt = spantxt.substring(0, 9) + "...";
				$("#" + treeNode.tId + "_span").html(spantxt);
			}
		}

		function addDiyDom(treeId, treeNode) {
			var spaceWidth = 5;
			var switchObj = $("#" + treeNode.tId + "_switch"),
			icoObj = $("#" + treeNode.tId + "_ico");
			switchObj.remove();
			icoObj.parent().before(switchObj);
			var spantxt = $("#" + treeNode.tId + "_span").html();
			if (spantxt.length > 19) {
				spantxt = spantxt.substring(0, 29) + "...";
				$("#" + treeNode.tId + "_span").html(spantxt);
			}
		}

		$(document).ready(function(){
			var str = '[{"id":1,"pId":null,"name":"根","open":true},{"id":11,"pId":1,"name":"一级1","open":true},{"id":211,"pId":11,"name":"二级","open":false},{"id":212,"pId":11,"name":"二级","open":false},{"id":12,"pId":1,"name":"一级2","open":true},{"id":221,"pId":12,"name":"二级","open":false},{"id":222,"pId":12,"name":"二级","open":false}]';
			var zNodes = JSON.parse(str);
				/**[
				{id:1, pId:0, name:"根", open:true},
				{id:11, pId:1, name:"一级1", open:true},
				{id:211, pId:11, name:"二级"},
				{id:212, pId:11, name:"二级"},
				{id:12, pId:1, name:"一级2", open:true},
				{id:221, pId:12, name:"二级"},
				{id:222, pId:12, name:"二级"}
			];**/
			
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});

		// 禁止拖拽
		function beforeDrag(treeId, treeNodes) {
	        return false;
	    }
		
		function allTree(url){
			layer.confirm('确定要提交这份思维导图吗？', {
				btn: ['确定', '取消']
			}, function() {
				layer.closeAll('dialog');
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				var node = treeObj.getNodes();
				var nodes = treeObj.transformToArray(node);
				var arr = [];
				for(var i=0;i<nodes.length;i++){
					var name = nodes[i].name;
					if (isEmpty(name)) {
						layer.msg("节点不能为空，请务必填写完整哦！"); 
						return ;
					}
					var jsonObj = {};
					jsonObj["id"] = nodes[i].id;
					jsonObj["pId"] = nodes[i].pId;
					jsonObj["name"] = nodes[i].name;
					jsonObj["open"] = nodes[i].open;
					arr.push(jsonObj);
				}
				console.log(arr);
				var bookId = $("#bookId").val();
				common.getData(url, {"data": JSON.stringify(arr), "bookId": bookId}, 
					function(data) {
	    				if (data.success) {
	    					layer.msg("思维导图创建成功！");
	    					setTimeout(function() {
	    						location.href = '<c:url value="/book/class/mind/map/'+ bookId +'"/>';
	    					}, 2000);
	    				} else {
	    					layer.msg(data.errorMsg[0]);
	    				}
	    			});
				
//	 			console.log(JSON.stringify(arr));
//	 			console.log(JSON.parse(JSON.stringify(nodes)));
			});
		}

		var newCount = 1;
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='add node' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
				return false;
			});
		};
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
		};
		
	</script>
</head>
<body bgcolor="#FFFFFF">
	<input type="hidden" id="bookId" value="${bookId}"> 
	<ul id="treeDemo" class="ztree"></ul>
	<a href="javascript:void(0);" class="_button" onclick="allTree('<c:url value="/api/addMindMap"/>')">确定提交</a>
</body>
</html>
