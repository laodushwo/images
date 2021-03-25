package org.spring.springboot.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.spring.springboot.ao.ChildrenAO;
import org.spring.springboot.ao.UsersAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.amall.books.commons.domain.ChildrenVO;

@Controller
@RequestMapping("children")
public class ChildrenController {
	@Resource
	private ChildrenAO childrenAO;
	@Resource
	private UsersAO usersAO;
	@Autowired
	private HttpServletRequest request;

	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable String id) {
		ModelAndView andView = new ModelAndView();
//		Result result = childrenAO.get(id);
//		if (result.isSuccess()) {
//			andView.addObject("locationList", result.get("locationList"));
//			andView.addObject("cityLocationList", result.get("cityLocationList"));
//			andView.addObject("areaLocationList", result.get("areaLocationList"));
//			andView.addObject("schoolList", result.get("schoolList"));
//			andView.addObject("childrenVO", result.get("childrenVO"));
//			andView.setViewName("users/children_add");
//		}
		return andView;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public ModelAndView edit(ChildrenVO childrenVO) {
		ModelAndView andView = new ModelAndView();
//		UsersVO usersVO = WebUtils.getUsers(request);
//		childrenVO.setCardId(usersVO.getChildrenVO().getCardId());
//		Result result = childrenAO.update(childrenVO);
//		if (result.isSuccess()) {
//			andView.setViewName("redirect:/user/index");
//		} else {
//			andView.addObject("childrenVO", childrenVO);
//			andView.setViewName("users/detail");
//		}
		return andView;
	}
	
}
