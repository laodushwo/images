package org.spring.springboot.controller;

import javax.servlet.http.HttpServletRequest;

import org.spring.springboot.ao.ActivityAO;
import org.spring.springboot.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.amall.books.commons.domain.ZzUsersVO;
import com.amall.commons.page.Page;

@Controller
@RequestMapping("activity")
public class ActivityContorller {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ActivityAO activityAO;

	private Page page = null;
	
	// 活动首页
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView andView = new ModelAndView();
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		if (null == usersVO) {
			andView.setViewName("redirect:/wx/index");
			return andView;
		}
		andView.addObject("usersVO", usersVO);
		andView.setViewName("users/activity");
		return andView;
	}
	
	// 共读页面
	@RequestMapping(value = "read/together", method = RequestMethod.GET)
	public ModelAndView readTogether() {
		ModelAndView andView = new ModelAndView();
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		andView.addObject("usersVO", usersVO);
		andView.setViewName("users/read_together");
		return andView;
	}
	
	// 阅读排行页面 - 普通用户
	@RequestMapping(value = "read/ranking", method = RequestMethod.GET)
	public ModelAndView readRanking() {
		ModelAndView andView = new ModelAndView();
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		andView.addObject("usersVO", usersVO);
		andView.setViewName("users/read_ranking");
		return andView;
	}
	
	// 阅读排行页面 - 管理员
	@RequestMapping(value = "admin/read/ranking/{gradeId}", method = RequestMethod.GET)
	public ModelAndView adminReadRanking(@PathVariable String gradeId) {
		ModelAndView andView = new ModelAndView();
		andView.addObject("gradeId", gradeId);
		andView.setViewName("users/read_ranking_admin");
		return andView;
	}
	
	// 普通用户 - 共读活动展示页面
	@RequestMapping(value = "show/together", method = RequestMethod.GET)
	public ModelAndView showTogether() {
		ModelAndView andView = new ModelAndView();
		andView.setViewName("users/show_together");
		return andView;
	}
	
	// 管理员 - 共读活动展示页面
	@RequestMapping(value = "admin/show/together/{gradeId}", method = RequestMethod.GET)
	public ModelAndView adminShowTogether(@PathVariable String gradeId) {
		ModelAndView andView = new ModelAndView();
		andView.addObject("gradeId", gradeId);
		andView.setViewName("users/show_together");
		return andView;
	}
}
