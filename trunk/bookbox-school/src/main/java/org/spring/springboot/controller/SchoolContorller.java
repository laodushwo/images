package org.spring.springboot.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.spring.springboot.ao.SchoolAO;
import org.spring.springboot.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.amall.books.commons.domain.ZzUsersVO;
import com.amall.commons.page.Page;
import com.amall.commons.result.Result;

@Controller
@RequestMapping("school")
public class SchoolContorller {

	@Resource
	private SchoolAO schoolAO;

	@Autowired
	private HttpServletRequest request;
	
	private Page page = null;

	// 跳转学校书籍
	@RequestMapping(value = "school/book/page", method = RequestMethod.GET)
	public ModelAndView schoolBookPage() {
		ModelAndView andView = new ModelAndView();
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		andView.addObject("usersVO", usersVO);
		andView.setViewName("users/school_book");
		return andView;
	}
	
	// 获取学校书籍列表
	@ResponseBody
	@RequestMapping(value = "book/list", method = { RequestMethod.GET, RequestMethod.POST })
	public Result bookList(Integer curPage, Integer pageSize) {
		page = new Page(curPage,pageSize);
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		Result result = schoolAO.bookList(usersVO.getChildrenVO().getSchoolId(), page);
		return result;
	}
	
	// 普通用户 - 跳转班级书籍
	@RequestMapping(value = "grade/book/page", method = RequestMethod.GET)
	public ModelAndView gradeBookPage() {
		ModelAndView andView = new ModelAndView();
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		andView.addObject("usersVO", usersVO);
		andView.setViewName("users/grade_book");
		return andView;
	}
	
	// 管理员 - 跳转班级书籍
	@RequestMapping(value = "admin/grade/book/page/{gradeId}", method = RequestMethod.GET)
	public ModelAndView adminGradeBookPage(@PathVariable String gradeId) {
		ModelAndView andView = new ModelAndView();
		andView.addObject("gradeId", gradeId);
		andView.setViewName("users/grade_book_admin");
		return andView;
	}
	
	// 获取班级书籍列表 - 普通用户
	@ResponseBody
	@RequestMapping(value = "grade/book/list", method = { RequestMethod.GET, RequestMethod.POST })
	public Result gradeBookList(Integer curPage, Integer pageSize) {
		page = new Page(curPage,pageSize);
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		Result result = schoolAO.bookGradeList(usersVO.getChildrenVO().getGradeId(), page);
		return result;
	}
	
	// 获取班级书籍列表 - 管理员
	@ResponseBody
	@RequestMapping(value = "admin/grade/book/list", method = { RequestMethod.GET, RequestMethod.POST })
	public Result adminGradeBookList(Integer curPage, Integer pageSize, String gradeId) {
		page = new Page(curPage,pageSize);
		Result result = schoolAO.bookGradeList(gradeId, page);
		return result;
	}
	
	// 跳转班级展示列表
	@RequestMapping(value = "view/index/{gradeId}/{gradeName}", method = RequestMethod.GET)
	public ModelAndView schoolBookPage(@PathVariable String gradeId, @PathVariable String gradeName) {
		ModelAndView andView = new ModelAndView();
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		andView.addObject("usersVO", usersVO);
		andView.addObject("gradeId", gradeId);
		andView.addObject("gradeName", gradeName);
		andView.setViewName("users/view_index");
		return andView;
	}

}
