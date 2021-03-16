package org.spring.springboot.ao.impl;

import javax.annotation.Resource;

import org.spring.springboot.ao.CardAO;
import org.springframework.stereotype.Service;

import com.amall.app.abstacts.AbstractAO;
import com.amall.books.commons.dointerface.ZzCardService;
import com.amall.commons.result.Result;

@Service
public class DefaultCardAO extends AbstractAO implements CardAO {

	@Resource
	private ZzCardService zzCardService;

	@Override
	public Result synCard(String gradeCode) {
		return zzCardService.getByGradeCode(gradeCode);
	}
	
}
