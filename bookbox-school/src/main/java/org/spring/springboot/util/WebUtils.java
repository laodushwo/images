/*
 * Copyright 2015 The JA-SIG Collaborative. All rights reserved.
 * distributed with thi file and available online at
 */
package org.spring.springboot.util;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.amall.books.commons.domain.ZzUsersVO;

/**
 * 
 * @author ganweibin
 * @File WebUtils.java Date:2015年12月23日 上午11:26:52 2015
 */
public final class WebUtils {

	/**
	 * 
	 * @param request
	 * @return
	 */
	private static SessionForm getSessionForm(HttpServletRequest request) {
		return (SessionForm) request.getSession().getAttribute(Path.LOGIN_WX_FORM);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static ZzUsersVO getUsers(HttpServletRequest request) {
		SessionForm form = getSessionForm(request);
		if (form != null) {
			return form.getUsers();
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static String getUsername(HttpServletRequest request) {
		ZzUsersVO user = getUsers(request);
		if (user != null) {
			return user.getUsername();
		}
		return null;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserId(HttpServletRequest request) {
		ZzUsersVO user = getUsers(request);
		if (user != null) {
			return user.getId();
		}
		return null;
	}

	/**
	 * 
	 * @param request
	 * @param form
	 */
	public static void addUser(HttpServletRequest request, SessionForm form) {
		HttpSession session = request.getSession();
		session.setAttribute(Path.LOGIN_WX_FORM, form);
	}

	/**
	 * 
	 * @param request
	 */
	public static void removeUser(HttpServletRequest request) {
		request.getSession().removeAttribute(Path.LOGIN_WX_FORM);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static String getRole(HttpServletRequest request) {
		SessionForm form = getSessionForm(request);
		if (form != null) {
			return form.getRole();
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
//	public static List<String> getAllList(HttpServletRequest request) {
//		SessionForm form = getSessionForm(request);
//		if (form != null) {
//			return form.getOwerList();
//		} else {
//			return Collections.emptyList();
//		}
//	}

	/**
	 * 
	 * @param response
	 * @param content
	 */
	static public void write(HttpServletResponse response, String content) {
		PrintWriter out = null;
		try {
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			out = response.getWriter();
			out.write(content);
		} catch (Exception e) {
			// TODO
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
	
	/**
	 * 转换数据（值是Object->String）
	 * 
	 * @param source 源
	 * @param des 目标
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("rawtypes")
	public static void mapObjectToMapString(Map<String, Object> source, Map<String, String> des){
		for (Iterator iter = source.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) source.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			des.put(name, valueStr);
		}
	}
	@SuppressWarnings("rawtypes")
	public static Map<String, String> mapObjectToMapString(Map<String, Object> source){
		 Map<String, String> map = new HashMap<String, String>();
		for (Iterator iter = source.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) source.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			map.put(name, valueStr);
		}
		return map;
	}
	/**
	 * 把字符串转成map
	 * <pre>
	 * 将key=value&key=value.....这样的字符串转成map
	 * </pre>
	 * @param source
	 * @param map
	 */
	public static void stringToMap(String source, Map<String, String> map) {
		String[] strArray = source.split("&");
		String[] tempArray = null;
		for (String str : strArray) {
			tempArray = str.split("=");
			map.put(tempArray[0], tempArray[1]);
		}
	}
	/**
	 * 获取客户端的IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if(ip.indexOf(",") != -1) {
			ip = ip.split(",")[0];
		}
		return ip;
	}
}
