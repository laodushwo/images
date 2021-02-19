/*
 * Copyright 2013 The JA-SIG Collaborative. All rights reserved.
 * distributed with this file and available online at
 * http://www.etong.com/
 */
package org.spring.springboot.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author ganweibin
 * @File CookieUtils.java Date:2015年11月13日 下午7:24:52 2015
 */
public class CookieUtils {
	/**
	 * 
	 * @param response
	 * @param key
	 * @param value
	 * @param times
	 */
	public static void writeCookie(HttpServletResponse response, String key, String value, int times) {
		if (value == null) {
			throw new RuntimeException("write cookie value is null error.");
		} else {
//			String b = Base64.encodeBase64String(value.getBytes());
			Cookie cookie = new Cookie(key, value);
			cookie.setMaxAge(times);
			response.addCookie(cookie);
		}
	}

	/**
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String readCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie != null && cookie.getName().equalsIgnoreCase(key)) {
//					return new String(Base64.decodeBase64(cookie.getValue().getBytes()));
					return new String(cookie.getValue().getBytes());
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param response
	 * @param key
	 */
	public static void deleteCookie(HttpServletResponse response, String key) {
		Cookie cookie = new Cookie(key, null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

}
