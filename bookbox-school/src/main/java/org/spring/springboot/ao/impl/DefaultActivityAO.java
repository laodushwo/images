package org.spring.springboot.ao.impl;

import javax.annotation.Resource;

import org.spring.springboot.ao.ActivityAO;
import org.springframework.stereotype.Service;

import com.amall.app.abstacts.AbstractAO;
import com.amall.books.commons.dointerface.ActivityService;
import com.amall.books.commons.domain.ZzActivityTogetherVO;
import com.amall.commons.result.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DefaultActivityAO extends AbstractAO implements ActivityAO {

	@Resource
	private ActivityService activityService;

	@Override
	public Result addTogeter(String userId, String gradeId, String id, String name, String author, String intro) {
		ZzActivityTogetherVO tVO = new ZzActivityTogetherVO();
		tVO.setGradeId(gradeId);
		tVO.setBookId(id);
		tVO.setBookName(name);
		tVO.setBookAuthor(author);
		tVO.setBookIntro(intro);
		tVO.setStartUserId(userId);
		return activityService.addTogether(tVO);
	}

	@Override
	public Result updateTogether(String gradeId) {
		return activityService.updateTogether(gradeId);
	}

	@Override
	public Result findTogether(String gradeId) {
		return activityService.getByGradeTogether(gradeId);
	}
	
	

}
