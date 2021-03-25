package org.spring.springboot.ao.impl;

import javax.annotation.Resource;

import org.spring.springboot.ao.SchoolAO;
import org.springframework.stereotype.Service;

import com.amall.app.abstacts.AbstractAO;
import com.amall.books.commons.dointerface.ZzSchoolService;
import com.amall.commons.page.Page;
import com.amall.commons.result.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DefaultSchoolAO extends AbstractAO implements SchoolAO {

	@Resource
	private ZzSchoolService zzSchoolService;
	
	@Override
	public Result get(String id) {
		return zzSchoolService.get(id);
	}

	@Override
	public Result getByCode(String code) {
		return null;
	}

	@Override
	public Result bookList(String schoolId, Page page) {
		return zzSchoolService.getByBookList(schoolId, page);
	}

	@Override
	public Result bookGradeList(String gradeId, Page page) {
		return zzSchoolService.getByGradeBookList(gradeId, page);
	}

	@Override
	public Result getByGradeList(String schoolId) {
		return zzSchoolService.getByGradeList(schoolId);
	}

}
