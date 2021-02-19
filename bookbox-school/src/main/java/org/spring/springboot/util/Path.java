/*
 * Copyright 2015 The JA-SIG Collaborative. All rights reserved.
 * distributed with thi file and available online at
 */
package org.spring.springboot.util;

/**
 * WEB 常量
 * 
 * @author ganweibin
 * @File Path.java Date:2015年11月10日 下午3:11:13 2015
 */
public interface Path {
	String WX_JSP = "wx/";
	/**
	 * 失败页面常量
	 */
	String FAILURE = "failure";
	/**
	 * 登陆保存到SESSION的KEY
	 */
	String LOGIN_WX_FORM = "_wx_login_user";

	String REDIRECT = "u";

	String UTF8 = "utf-8";
	String RAND_WORD = "randWord";
	/**
	 * 用户名的cookie键
	 */
	static final String USERNAME_COOKIE = "____cookie______username____store____";
	/**
	 * 是否勾选的cookie键
	 */
	static final String REMEBER_CHECK = "_____check______true__________";
	/**
	 * 密码的cookie键
	 */
	static final String PASSWORD_COOKIE = "_____cookie____passwrod______store__________";
}
