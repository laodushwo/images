function CommonClass() {
//	this.homeurl = "http://192.168.3.15:5500"; // 本地：自动获取网页地址
	this.homeurl = "http://xt.feimread.com";    // 线上地址
	this.imageUrl = getImageUrlServer();
	this.imgUrl = this.imageUrl;
	this.basePath="/school";
	
	// 截取url参数
	this.getQueryString = function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}
	
	function getImageUrlServer(){
		return "http://file.feimread.com:8080/image";
	}
	
	function getRootPath() {
		// 获取当前网址，如： http://localhost:8080/ems/Pages/Basic/Person.jsp
		var curWwwPath = window.document.location.href;
		// 获取主机地址之后的目录，如： /ems/Pages/Basic/Person.jsp
		var pathName = window.document.location.pathname;
		var pos = curWwwPath.indexOf(pathName);
		// 获取主机地址，如： http://localhost:8080
		var localhostPath = curWwwPath.substring(0, pos);
		// 获取带"/"的项目名，如：/ems
		var projectName = pathName
				.substring(0, pathName.substr(1).indexOf('/') + 1);
		return (localhostPath + projectName);
	}
	
	function getHostPath() {
		// 获取当前网址，如： http://localhost:8080/ems/Pages/Basic/Person.jsp
		var curWwwPath = window.document.location.href;
		// 获取主机地址之后的目录，如： /ems/Pages/Basic/Person.jsp
		var pathName = window.document.location.pathname;
		var pos = curWwwPath.indexOf(pathName);
		// 获取主机地址，如： http://localhost:8080
		var localhostPath = curWwwPath.substring(0, pos);
		// 获取带"/"的项目名，如：/ems
		return localhostPath;
	}

	// 过滤HTML标签
	this.removeHTMLTag = function(str) {
		str = str.replace(/<\/?[^>]*>/g, ''); // 去除HTML tag
		str = str.replace(/[ | ]*\n/g, '\n'); // 去除行尾空白
		str = str.replace(/\n[\s| | ]*\r/g, '\n'); // 去除多余空行
		str = str.replace(/&nbsp;/ig, '');// 去掉&nbsp;
		return str;
	}
	//金额格式
    this.moneyFormat = function(val){
   		if(val == "" || val == null || val == 0){
           return "0.00";
        }
        var value=Math.round(parseFloat(val)*100)/100;
        var xsd=value.toString().split(".");
        if(xsd.length == 1){
             value=value.toString()+".00";
             return value;
        }
        if(xsd.length>1){
          	if(xsd[1].length<2){
               value=value.toString()+"0";
             }
             return value;
        }
      }

	// 获取当天时间
	this.GetDateT = function() {
		var d, s;
		d = new Date();
		s = d.getYear() + "-"; // 取年份
		s = s + (d.getMonth() + 1) + "-";// 取月份
		s += d.getDate() + " "; // 取日期
		s += d.getHours() + ":"; // 取小时
		s += d.getMinutes() + ":"; // 取分
		s += d.getSeconds(); // 取秒
		return (s);
	}
	
	// 获取当天时间
	this.getNowDateT = function() {
		var d, s;
		d = new Date();
		s = d.getYear() + ""; // 取年份
		s = s + (d.getMonth() + 1) + "";// 取月份
		s += d.getDate() + ""; // 取日期
		s += d.getHours() + ""; // 取小时
		s += d.getMinutes() + ""; // 取分
		s += d.getSeconds(); // 取秒
		return (s);
	}

	// JSON时间转换成字符串
	this.toStringFromJsonData = function(obj) {
		function tostring(data) {
			if (data <= 9) {
				data = "0" + data;
			}
			return data;
		}
		var year = tostring(obj.year + 1900);
		var month = tostring(obj.month + 1);
		var day = tostring(obj.date);
		var shi = tostring(obj.hours);
		var fen = tostring(obj.minutes);
		var miao = tostring(obj.seconds);
		return year + "-" + month + "-" + day + " " + shi + ":" + fen + ":"	+ miao;
	}

	// json返回时间格式
	this.FormatTime = function(obj, style) {
		function tostring(data) {
			if (data <= 9) {
				data = "0" + data;
			}
			return data;
		}

		var year = tostring(obj.year + 1900);
		var month = tostring(obj.month + 1);
		var day = tostring(obj.date);
		var shi = tostring(obj.hours);
		var fen = tostring(obj.minutes);
		var miao = tostring(obj.seconds);

		switch (style) {
		case 1:
			return year + "-" + month + "-" + day + " " + shi + ":" + fen + ":"	+ miao;
			break;
		case 2:
			return year + "-" + month + "-" + day;
			break;
		case 3:
			return year + "/" + month + "/" + day + " " + shi + ":" + fen + ":"	+ miao;
		case 4:
			return month + "-" + day + " " + shi + ":" + fen;
			break;
		}
	}
	
	//在Jquery里格式化Date日期时间数据
	this.timeStamp2String = function(time, style){
	    var datetime = new Date();
	    datetime.setTime(time);
	    var year = datetime.getFullYear();
	    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
	    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
	    
	    switch (style) {
		case 1:
			return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":"	+ second;
			break;
		case 2:
			return year + "-" + month + "-" + date;
			break;
		case 3:
			return year + "/" + month + "/" + date + " " + hour + ":" + minute + ":"	+ second;
			break;
		case 4:
			return month + "-" + date + " " + hour + ":" + minute;
			break;
		case 5:
			return year + "/" + month + "/" + date;
			break;
		case 6:
			return month + "/" + date;
			break;
		case 7:
			return month + "月" + date + "日";
			break;
		case 8:
			return year + "年" + month + "月";
			break;
		case 9:
			return month + "月" + date + "日" + " " + hour + "时" + minute + "分";
			break;
		case 10:
			return year + "" + month + "" + date;
			break;
		case 11:
			return year + "年" + month + "月" + date + "日";
			break;
		case 12:
			return year + "." + month + "." + date;
			break;
		case 13:
			return year + "" + month + "" + date;
			break;
		case 14:
			return hour + ":" + minute;
			break;
		}
	    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
	}

	// 不能重复点击2次 true代码可以点击， false不可点击
	var t1 = null;
	this.onclickt = function(time) {
		if (t1 == null) {
			t1 = new Date().getTime();
			return true;
		}
		var t2 = new Date().getTime();
		if (time != undefined) {
			if (t2 - t1 < time) {
				return false;
			} else {
				t1 = t2;
				return true;
			}
		} else {
			if (t2 - t1 < 500) {
				// t1 = t2;
				return false;
			} else {
				t1 = t2;
				return true;
			}
		}
	}

	this.btnback = function() {
		if (Common.onclickt() == false) {
			return;
		}
		window.history.go(-1);
	}

	this.btnbackImmediately = function() {
		window.history.go(-1);
	}

	this.setBackGroundColor = function(obj, topColor, bottomColor) {
		var explorer = window.navigator.userAgent;
		/* Safari 5.1+, Chrome 10+ */
		if ((explorer.indexOf("Safari") >= 0)
				|| (explorer.indexOf("Chrome") >= 0)) {
			obj.css({
				"background" : "-webkit-linear-gradient(top, " + topColor + ","
						+ bottomColor + ")"
			});
		} else if (explorer.indexOf("Firefox") >= 0) { /* Firefox 3.6+ */
			obj.css({
				"background" : "-moz-linear-gradient(top, " + topColor + ","
						+ bottomColor + ")"
			});
		} else if (explorer.indexOf("Opera") >= 0) { /* Opera 11.10+ */
			obj.css({
				"background" : "-o-linear-gradient(top, " + topColor + ","
						+ bottomColor + ")"
			});
		} else {

			if (Common.whatBrowser().ios) {
				obj.css({
					"background" : "-webkit-linear-gradient(top, " + topColor
							+ "," + bottomColor + ")"
				});
			} else {
				obj.css({
					"background" : bottomColor
				});
			}
		}
	}

	// 计算两点距离
	this.getDistance = function(coords_A, coords_B) {
		if (coords_A.latitude == 0 || coords_A.longitude == 0
				|| coords_B.latitude == 0 || coords_B.longitude == 0)
			return "未知";
		if (coords_A.latitude - coords_B.latitude < 1
				|| coords_A.longitude - coords_B.longitude < 1) {
			var s = Math
					.round(Math.sqrt(Math.pow(coords_A.latitude
							- coords_B.latitude, 2)
							+ Math.pow(coords_A.longitude - coords_B.longitude,
									2)) * 3600 * 30.92);
			return s < 5 ? "就在您眼前" : s < 1000 ? (s + "m") : (Math
					.round(s / 1000) + "km");
		}
		// 东莞市太小----
		return "";
	}
	
	this.whatBrowser = function() {
		var u = window.navigator.userAgent, app = window.navigator.appVersion;
		return {
			// IE内核
			trident : u.indexOf('Trident') > -1,
			// opera内核
			presto : u.indexOf('Presto') > -1,
			// 苹果、谷歌WebKits内核
			webKit : u.indexOf('AppleWebKit') > -1,
			// 火狐内核
			gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,
			// 是否为移动终端
			mobile : !!u.match(/AppleWebKit.*Mobile.*/),
			// ios终端
			ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
			// android终端
			android : u.indexOf('Android') > -1,
			// iPhone
			iPhone : u.indexOf('iPhone') > -1,
			// iPod
			iPod : u.indexOf('iPod') > -1,
			// iPad
			iPad : u.indexOf('iPad') > -1,
			// 是否web应该程序，没有头部与底部
			webApp : u.indexOf('Safari') == -1,
			//是否微信内置浏览器
			weixin : u.toLowerCase().match(/MicroMessenger/i) == 'micromessenger'
		};
	};


	
	this.transformContent = function(content) {
		content = content.replace(/<img.*>.*<\/img>/ig,'');
		content = content.replace(/<img.+?>/ig,'');
		content = content.replace(/&nbsp;/g,'');
		content = content.replace(/<br>/g, ' ');
		return content;
	};

	this.getExplorer = function() {
		var explorer = window.navigator.userAgent;
		// ie
		if (explorer.indexOf("MSIE") >= 0) {
			return "MSIE";
		}
		// firefox
		else if (explorer.indexOf("Firefox") >= 0) {
			return "Firefox";
		}
		// Chrome
		else if (explorer.indexOf("Chrome") >= 0) {
			return "Chrome";
		}
		// Opera
		else if (explorer.indexOf("Opera") >= 0) {
			return "Opera";
		}
		// Safari
		else if (explorer.indexOf("Safari") >= 0) {
			return "Safari";
		}
		return "";
	};


	//判断是否是网址
	this.isURL=function(str_url,callback){ 
			str = str_url;
			str = str.match(/(http|https):\/\/.+/); 
			if (str == null){ 
				 callback(false);  
			}else{ 
				callback(true); 
			} 
     }
	

	
	//生成网页二微码
	this.getCode=function(div,url,width,height){
		div.qrcode({
			text : url,
			width : width,
			height : height
		});
	}
	
	//画布转图像.
	this.convertCanvasToImage=function(canvas) {
		var image = new Image();
		image.src = canvas.toDataURL("image/png");
		return image;
	}
	
	//设置 cookie
	this.setCookie=function(name,value,days)	{ 
	    //var Days = 30; 
	    var exp = new Date(); 
	    exp.setTime(exp.getTime() + days*24*60*60*1000); 
	    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
	} 

	//读取cookie 
	 this.getCookie = function(name){
	    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	 
	    if(arr=document.cookie.match(reg))
	 
	        return unescape(arr[2]); 
	    else 
	        return null; 
	}
	this.getData = function(url, data, callback){
		$.ajax(url,{
			data:data,
			dataType:'json',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			timeout:10000,//超时时间设置为10秒；
			success:function(result){
				callback(result);
			}
		});
	}
}	
var common = new CommonClass();

/**
 * 弹出消息提示框，采用浏览器布局，位于整个页面中央，默认显示3秒
 * 后面的消息会覆盖原来的消息
 * @param message：待显示的消息
 * @param type：消息类型，0：错误消息，1：成功消息
 */
function showMessage(message, type) {
    $(".showMessage").remove();//如果元素已经存在，则先删除它。（不存在也不影响 remove）
    var messageJQ = $("<div class='showMessage'>" + message + "</div>");
    if (type == 0) {
        messageJQ.addClass("showMessageError");
    } else if (type == 1) {
        messageJQ.addClass("showMessageSuccess");
    }
    /**先将元素隐藏到页面，然后以600秒的速度下拉显示出来*/
    messageJQ.hide().appendTo("body").slideDown(600);
    /**3秒之后自动删除生成的元素*/
    window.setTimeout(function () {
        messageJQ.remove();
    }, 3000);
}
function loading(type) {
	$(".message_box").remove();//如果元素已经存在，则先删除它。（不存在也不影响 remove）
	var messageJQ = $('<div class="message_box"><div class="box1"><img alt="" src="'+common.homeurl+common.basePath+'/static/image/user/loading_data.gif"/></div></div>');
	/**先将元素隐藏到页面，然后以600秒的速度下拉显示出来*/
	if(type == 1){
		messageJQ.appendTo("body");
	}else if(type == 2){
		$(".message_box").hide();
	}
//	messageJQ.hide().appendTo("body").slideDown(600);
	/**3秒之后自动删除生成的元素*/
//	window.setTimeout(function () {
//		messageJQ.remove();
//	}, 3000);
}

//JS转换时间戳为“刚刚”、“1分钟前”、“2小时前”“1天前”等格式
var minute = 1000 * 60;
var hour = minute * 60;
var day = hour * 24;
var halfamonth = day * 15;
var month = day * 30;
function getDateDiff(dateTimeStamp) {
    //若你得到的时间格式不是时间戳，可以使用下面的JavaScript函数把字符串转换为时间戳, 本函数的功能相当于JS版的strtotime：
    var idata = Date.parse(dateTimeStamp.replace(/-/gi,"/"));  //js函数代码：字符串转换为时间
    var now = new Date().getTime();
    var diffValue = now - idata;
    if (diffValue < 0) {
        //若日期不符则弹出窗口告之
        //alert("结束日期不能小于开始日期！");
    }
    var monthC = diffValue / month;
    var weekC = diffValue / (7 * day);
    var dayC = diffValue / day;
    var hourC = diffValue / hour;
    var minC = diffValue / minute;
    if (monthC >= 1) {
        result = parseInt(monthC) + "个月前";
    }
    else if (weekC >= 1) {
        result = parseInt(weekC) + "周前";
    }
    else if (dayC >= 1) {
        result = parseInt(dayC) + "天前";
    }
    else if (hourC >= 1) {
        result = parseInt(hourC) + "个小时前";
    }
    else if (minC >= 1) {
        result = parseInt(minC) + "分钟前";
    } else
        result = "刚刚";
    return result;
}

/**
 * JS获取距当前时间差
 * 
 * @param int time JS毫秒时间戳
 *
 */
function get_time_diff(time) {
    var diff = '';
    var time_diff = new Date().getTime() - time;
    // 计算相差天数  
    var days = Math.floor(time_diff / (24 * 3600 * 1000));
    if (days > 0) {
        diff += days; // + '天';
    }
//    // 计算相差小时数  
//    var leave1 = time_diff % ( 24 * 3600 * 1000); 
//    var hours = Math.floor(leave1 / (3600 * 1000));
//    if (hours > 0) {
//        diff += hours + '小时';
//    } else {
//        if (diff !== '') {
//            diff += hours + '小时';
//        }
//    }
//    // 计算相差分钟数  
//    var leave2 =leave1 % (3600 * 1000);
//    var minutes = Math.floor(leave2 / (60 * 1000));
//    if (minutes > 0) {
//        diff += minutes + '分';
//    } else {
//        if (diff !== '') {
//            diff += minutes + '分';
//        }
//    }
//    // 计算相差秒数  
//    var leave3 = leave2%(60*1000);
//    var seconds = Math.round(leave3/1000);
//    if (seconds > 0) {
//        diff += seconds + '秒';
//    } else {
//        if (diff !== '') {
//            diff += seconds + '秒';
//        }
//    }
    
    return diff;
}

//判断字符串是否为空
function isEmpty(obj) {
    if (typeof obj == "undefined" || obj == null || obj == "") {
        return true;
    } else {
        return false;
    }
}