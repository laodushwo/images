package org.spring.springboot.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.spring.springboot.ao.BookAO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amall.commons.result.Result;

@RestController
@RequestMapping("api")
public class ApiController {

	@Resource
	private BookAO bookAO;
	@Resource
	private HttpServletRequest request;
	
	//根据查询书籍
	@RequestMapping(value = "book/{schoolCode}/{isbn}", method = RequestMethod.GET)
	public Result index(@PathVariable String schoolCode, @PathVariable String isbn) {
		return bookAO.getByIsbn(schoolCode, isbn);
	}
	
}
