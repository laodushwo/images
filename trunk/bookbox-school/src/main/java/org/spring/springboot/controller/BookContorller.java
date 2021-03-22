package org.spring.springboot.controller;

import javax.servlet.http.HttpServletRequest;

import org.spring.springboot.ao.BookAO;
import org.spring.springboot.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.amall.books.commons.domain.ZzUsersVO;
import com.amall.commons.result.Result;

@Controller
@RequestMapping("book")
public class BookContorller {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private BookAO bookAO;
	
	// 热门书籍
	@RequestMapping(value = "hot", method = RequestMethod.GET)
	public ModelAndView hot() {
		ModelAndView andView = new ModelAndView();
		andView.setViewName("users/book_hot_list");
		return andView;
	}
	
	// 最新书籍
	@RequestMapping(value = "newest", method = RequestMethod.GET)
	public ModelAndView newest() {
		ModelAndView andView = new ModelAndView();
		andView.setViewName("users/book_newest_list");
		return andView;
	}

	// 书籍详情
	@RequestMapping(value = "detail/{isbn}", method = RequestMethod.GET)
	public ModelAndView detail(@PathVariable String isbn) {
		ModelAndView andView = new ModelAndView();
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		if (null == usersVO) {
			andView.setViewName("redirect:/wx/index");
			return andView;
		}
		Result result = bookAO.getByBookDetail(usersVO.getId(), isbn);
		andView.addObject("bookVO", result.get("bookVO"));
		andView.addObject("bookMindVO", result.get("bookMindVO"));
		andView.addObject("bookReadFeelVO", result.get("bookReadFeelVO"));
		andView.addObject("usersVO", usersVO);
		andView.setViewName("users/book_detail");
		return andView;
	}
	
	// 借还记录
	@RequestMapping(value = "booklog", method = RequestMethod.GET)
	public ModelAndView booklog() {
		ModelAndView andView = new ModelAndView();
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		andView.addObject("usersVO", usersVO);
		andView.setViewName("users/booklog_list");
		return andView;
	}
	
	// 我的书架
	@RequestMapping(value = "mybook", method = RequestMethod.GET)
	public ModelAndView mybook() {
		ModelAndView andView = new ModelAndView();
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		if (null == usersVO) {
			andView.setViewName("redirect:/wx/index");
			return andView;
		}
		andView.addObject("usersVO", usersVO);
		andView.setViewName("users/my_book");
		return andView;
	}
	
	// 读书存档
	@RequestMapping(value = "keep/file", method = RequestMethod.GET)
	public ModelAndView keepFile() {
		ModelAndView andView = new ModelAndView();
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		andView.addObject("usersVO", usersVO);
		andView.setViewName("users/keep_file");
		return andView;
	}
	
	// 首页书籍搜索
	@RequestMapping(value = "search/book/{name}", method = RequestMethod.GET)
	public ModelAndView searchBook(@PathVariable String name) {
		ModelAndView andView = new ModelAndView();
		andView.addObject("searchName", name);
		andView.setViewName("users/search_book");
		return andView;
	}
	
	// 跳转班级思维导图列表
	@RequestMapping(value = "class/mind/map/{bookId}", method = RequestMethod.GET)
	public ModelAndView classMindMap(@PathVariable String bookId) {
		ModelAndView andView = new ModelAndView();
		andView.addObject("bookId", bookId);
		andView.setViewName("users/class_mind_map");
		return andView;
	}
	
	// 跳转个人思维导图
	@RequestMapping(value = "mind/map/{bookId}", method = RequestMethod.GET)
	public ModelAndView mindMap(@PathVariable String bookId) {
		ModelAndView andView = new ModelAndView();
		andView.addObject("bookId", bookId);
		andView.setViewName("users/mind_map");
		return andView;
	}
	
	// 上下架书籍清单
	@RequestMapping(value = "grade/book/ud", method = RequestMethod.GET)
	public ModelAndView gradeBook() {
		ModelAndView andView = new ModelAndView();
		andView.setViewName("users/grade_book_ud");
		return andView;
	}
	
	// 上下架漂流清单
	@RequestMapping(value = "grade/drift/ud", method = RequestMethod.GET)
	public ModelAndView gradeDrift() {
		ModelAndView andView = new ModelAndView();
		andView.setViewName("users/grade_drift_ud");
		return andView;
	}
}
