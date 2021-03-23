package org.spring.springboot.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.spring.springboot.ao.UsersAO;
import org.spring.springboot.util.CookieUtils;
import org.spring.springboot.util.Path;
import org.spring.springboot.util.SessionForm;
import org.spring.springboot.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.amall.books.commons.domain.ZzChildrenVO;
import com.amall.books.commons.domain.ZzSchoolAdminVO;
import com.amall.books.commons.domain.ZzUsersVO;
import com.amall.commons.result.Result;

@Controller
@RequestMapping("user")
public class UsersContorller {

	@Resource
	private UsersAO usersAO;
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView andView = new ModelAndView();
		ZzUsersVO users = WebUtils.getUsers(request);
		Result result = usersAO.get(users);
		ZzUsersVO usersVO = (ZzUsersVO) result.get("usersVO");
		ZzUsersVO gradeUsersVO = (ZzUsersVO) result.get("gradeUsersVO");
		ZzSchoolAdminVO schoolAdminVO = (ZzSchoolAdminVO) result.get("schoolAdminVO");
//		String role = "";
		// 班级管理员
		if (null != gradeUsersVO) {
			usersVO.setOutId(gradeUsersVO.getOutId());
			usersVO.setOutType(gradeUsersVO.getOutType());
//			role = "grade";
		}
		andView.setViewName("users/index");
		loginAfter(usersVO);
//		andView.addObject("role", role);
		andView.addObject("schoolAdminVO", schoolAdminVO); // 学校管理员
		andView.addObject("usersVO", usersVO);
		request.setAttribute("usersVO", usersVO);
		return andView;
	}

	// 临时方法
	private void loginAfter(ZzUsersVO usersVO) {
		request.getSession().setAttribute("openid", usersVO.getOpenid());
		CookieUtils.writeCookie(response, Path.LOGIN_WX_FORM, request.getSession().getId(), 7 * 24 * 60 * 60);
		WebUtils.addUser(request, new SessionForm(usersVO, usersVO.getOpenid()));
	}
	
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public ModelAndView detail() {
		ModelAndView andView = new ModelAndView();
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		if (null == usersVO) {
			andView.setViewName("redirect:/wx/index");
			return andView;
		}
		Result result = usersAO.detail(usersVO);
		andView.addObject("cardVO", result.get("cardVO"));
		andView.addObject("usersVO", result.get("usersVO"));
		// 根据用户ID获取小孩信息以及相应数据
		Result childrenResult = usersAO.getUserSchool(usersVO.getId());
		ZzChildrenVO childrenVO = (ZzChildrenVO) childrenResult.get("childrenVO");
		if (null != childrenVO) {
			Result result1 = usersAO.getChildren(childrenVO);
			if (result.isSuccess()) {
				andView.addObject("childrenVO", result1.get("childrenVO"));
			}
		} else {
			andView.addObject("childrenVO", new ZzChildrenVO());
		}
		// 跳转基础资料
		andView.setViewName("users/basic_data");
		return andView;
	}
	
	@RequestMapping(value = "school/admin/{schoolId}/{schoolName}", method = RequestMethod.GET)
	public ModelAndView schoolAdmin(@PathVariable String schoolId, @PathVariable String schoolName) {
		ModelAndView andView = new ModelAndView();
		andView.addObject("schoolId", schoolId);
		andView.addObject("schoolName", schoolName);
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		andView.addObject("usersVO", usersVO);
		andView.setViewName("users/school_admin");
		return andView;
	}
	
}
