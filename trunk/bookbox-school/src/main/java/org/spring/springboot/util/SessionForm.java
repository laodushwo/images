/*
 * Copyright 2015 The JA-SIG Collaborative. All rights reserved.
 * distributed with thi file and available online at
 */
package org.spring.springboot.util;

import java.io.Serializable;

import com.amall.books.commons.domain.UsersVO;

/**
 * 
 * @author ganweibin
 * @File SessionForm.java Date:2015年12月23日 上午11:31:08 2015
 */
public class SessionForm implements Serializable {
	private static final long serialVersionUID = -5462725180167952925L;
	private UsersVO usersVO;
	private String role;

	public SessionForm(UsersVO usersVO, String role) {
		super();
		this.usersVO = usersVO;
		this.role = role;
	}

	public UsersVO getUsers() {
		return usersVO;
	}

	public void setUsers(UsersVO usersVO) {
		this.usersVO = usersVO;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
