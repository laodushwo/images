<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>书籍搜索</title>
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">

    <!-- CSS Code -->
    <link rel="stylesheet" href='<c:url value="/static/css/common/mescroll.min.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <link rel="stylesheet" href='<c:url value="/static/css/common/base.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <link rel="stylesheet" href='<c:url value="/static/css/users/search_book.css"/>?v=<%=System.currentTimeMillis() %>' type="text/css">
    <!-- JavaScript Code -->
    <script type="text/javascript" src="<c:url value="/static/js/jquery-1.9.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/static/js/mescroll.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/js/common.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/layer/layer.js"/>"></script>
	<script type="text/javascript">
	var mescroll;
	$(function(){
		
		var searchName = "${searchName}";
		if (!isEmpty(searchName)) {
			$("#bookName").val(searchName);
		}
		
		$(".btn_search").click(function(){
			var name = $("#bookName").val();
			if (isEmpty(name)) {
				layer.msg("请输入书籍名称搜索"); return ;
			}
			var page = {num: 0, size: 100};
			upCallback(page);
		});
		
		mescroll = new MeScroll("mescroll", { //第一个参数"mescroll"对应上面布局结构div的id (1.3.5版本支持传入dom对象)
	   		//如果您的下拉刷新是重置列表数据,那么down完全可以不用配置,具体用法参考第一个基础案例
	   		//解析: down.callback默认调用mescroll.resetUpScroll(),而resetUpScroll会将page.num=1,再触发up.callback
			down: {
				//callback: downCallback //下拉刷新的回调,别写成downCallback(),多了括号就自动执行方法了
				use: false
			},
			up: {
				callback: upCallback, //上拉加载的回调
				//以下是一些常用的配置,当然不写也可以的.
				page: {
					num: 0, //当前页 默认0,回调之前会加1; 即callback(page)会从1开始
					size: 100 //每页数据条数,默认10
				},
				htmlNodata: '<p class="upwarp-nodata">-- 已经到了最底了 --</p>',
				noMoreSize: 5, //如果列表已无数据,可设置列表的总数量要大于5才显示无更多数据;
						//避免列表数据过少(比如只有一条数据),显示无更多数据会不好看
						//这就是为什么无更多数据有时候不显示的原因.
				toTop: {
					//回到顶部按钮
					src: "<c:url value='/static/image/common/mescroll-totop.png'/>", //图片路径,默认null,支持网络图
					offset: 1000 //列表滚动1000px才显示回到顶部按钮	
				},
				empty: {
					//列表第一页无任何数据时,显示的空提示布局; 需配置warpId才显示
					warpId:	"content", //父布局的id (1.3.5版本支持传入dom元素)
					icon: "<c:url value='/static/image/common/mescroll-empty.png'/>", //图标,默认null,支持网络图
					tip: "暂无相关数据~" //提示
				},
				lazyLoad: {
	        			use: true, // 是否开启懒加载,默认false
	        			attr: 'imgurl' // 标签中网络图的属性名 : <img imgurl='网络图  src='占位图''/>
	        		}
			}
		});
	});
	
	function upCallback(page) {
		var name = $("#bookName").val();
		common.getData(common.basePath +"/api/search/book", {"name": name}, function(data){
			if(data.success){
				var curPageData = data.models.bookList;
				var totalPage = 100;
				mescroll.endByPage(curPageData.length, totalPage, null);
				processList(curPageData);
			}
		});
	}
	function processList(bookList){
		$("#mescroll ul").append("");
		var html ='';
		$(bookList).each(function(i, obj) {
        	html +='<a href="<c:url value="/book/detail/'+ obj.isbn +'"/>"><li>';
			html +='<div class="rigth_box">';
        	html +='<div class="book_name">'+ obj.name +'</div>';
        	html +='<p class="time">'+ obj.author +'</p>';
       		html +='</div>';
       		html +='</li></a>';
		});
		var ul = $("#mescroll ul").html();
		if(ul == undefined){
			$("#mescroll").append("<ul></ul>");
		}
		$("#mescroll ul").append(html);
	}
	</script>
</head>

<body bgcolor="#FFFFFF">
	<div class="search_box">
		<input type="text" class="input" placeholder="输入小朋友喜欢的书籍" id="bookName" />
		<span class="btn_search"><img class="search_img" src="<c:url value="/static/image/homepage/fdj.png"/>"></span>
	</div>
    <div id="content">
		<div id="mescroll"  class="_main_section mescroll">
	        <ul class="school_book">
	        </ul>    
	    </div>
    </div>
</body>
</html>