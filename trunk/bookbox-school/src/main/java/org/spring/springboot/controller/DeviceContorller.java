package org.spring.springboot.controller;

import javax.servlet.http.HttpServletRequest;

import org.spring.springboot.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.amall.books.commons.domain.ZzUsersVO;

@Controller
@RequestMapping("device")
public class DeviceContorller {

	@Autowired
	private HttpServletRequest request;
	
	// 生成设备登录二维码
	@RequestMapping(value = "qrcode", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView andView = new ModelAndView();
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		if (null == usersVO) {
			andView.setViewName("redirect:/wx/index");
			return andView;
		}
		andView.addObject("usersVO", usersVO);
		andView.setViewName("users/device_login_qrcode");
		return andView;
	}
	
}
