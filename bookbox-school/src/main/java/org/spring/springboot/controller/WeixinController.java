package org.spring.springboot.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.spring.springboot.ao.WeixinAO;
import org.spring.springboot.util.CookieUtils;
import org.spring.springboot.util.Path;
import org.spring.springboot.util.SessionForm;
import org.spring.springboot.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.amall.books.commons.domain.ZzUsersVO;
import com.amall.commons.result.Result;

@Controller
@RequestMapping("wx")
public class WeixinController {

	@Autowired
	private WeixinAO weixinAO;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(String u) {
		System.out.println("index-u==" +u);
		request.setAttribute("u", u);
		return "index";
		
	}
	
	@RequestMapping(value = "check/user", method = RequestMethod.GET)
	public ModelAndView checkUser(String code) {
		ModelAndView modelAndView = new ModelAndView();
		ZzUsersVO user = WebUtils.getUsers(request);
		if(user == null || StringUtils.isBlank(user.getNickName())) {
			Result result = weixinAO.checkUser(code);
			if(result.isSuccess()) {
				ZzUsersVO usersVO = (ZzUsersVO) result.get("usersVO");
				modelAndView.addObject("usersVO", usersVO);
				loginAfter(usersVO);
			} else {
				modelAndView.setViewName("redirect:/wx/index");
				return modelAndView;
			}
		} else {
			modelAndView.addObject("usersVO", user);
			
		}
		String u = request.getParameter("state");
		if(StringUtils.isNotBlank(u) && !"${u}".equals(u)) {
			modelAndView.setViewName("redirect:"+u);
			return modelAndView;
		}
		modelAndView.setViewName("redirect:/user/index");
		return modelAndView;
		
	}

	private void loginAfter(ZzUsersVO usersVO) {
		request.getSession().setAttribute("openid", usersVO.getOpenid());
		CookieUtils.writeCookie(response, Path.LOGIN_WX_FORM, request.getSession().getId(), 7 * 24 * 60 * 60);
		WebUtils.addUser(request, new SessionForm(usersVO, usersVO.getOpenid()));
	}

	@ResponseBody
	@RequestMapping(value = "signture", method = { RequestMethod.POST, RequestMethod.GET })
	public Result signture(String url) {
		return weixinAO.signture(url);
	}
}
