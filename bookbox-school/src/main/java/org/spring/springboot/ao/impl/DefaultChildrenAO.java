package org.spring.springboot.ao.impl;

import javax.annotation.Resource;

import org.spring.springboot.ao.ChildrenAO;
import org.springframework.stereotype.Service;

import com.amall.app.abstacts.AbstractAO;
import com.amall.books.commons.dointerface.ZzChildrenService;
import com.amall.books.commons.domain.ZzBookMindVO;
import com.amall.books.commons.domain.ZzBookReadFeelVO;
import com.amall.books.commons.domain.ZzChildrenVO;
import com.amall.books.commons.domain.ZzUsersVO;
import com.amall.commons.result.DefaultResult;
import com.amall.commons.result.Result;

@Service
public class DefaultChildrenAO extends AbstractAO implements ChildrenAO {

	@Resource
	private ZzChildrenService zzChildrenService;

	@Override
	public Result add(ZzChildrenVO childrenVO) {
		return zzChildrenService.add(childrenVO);
	}

	@Override
	public Result update(ZzChildrenVO childrenVO) {
		return zzChildrenService.update(childrenVO);
	}

	@Override
	public Result addMindMap(ZzUsersVO userVO, String data, String bookId) {
		ZzBookMindVO bookMindVO = new ZzBookMindVO();
		bookMindVO.setUserId(userVO.getId());
		ZzChildrenVO childrenVO = userVO.getChildrenVO();
		bookMindVO.setSchoolId(childrenVO.getSchoolId());
		bookMindVO.setSchoolName(childrenVO.getSchoolVO().getName());
		bookMindVO.setGradeName(childrenVO.getGradeVO().getName());
		bookMindVO.setChildrenName(childrenVO.getName());
		bookMindVO.setBookId(bookId);
		bookMindVO.setContent(data);
		return zzChildrenService.addMindMap(bookMindVO);
	}

	@Override
	public Result findUserByClassMindMap(ZzUsersVO userVO, String bookId) {
		ZzBookMindVO bookMindVO = new ZzBookMindVO();
		ZzChildrenVO childrenVO = userVO.getChildrenVO();
		bookMindVO.setSchoolId(childrenVO.getSchoolId());
		bookMindVO.setGradeName(childrenVO.getGradeVO().getName());
		bookMindVO.setBookId(bookId);
		return zzChildrenService.getByUserByClassMindMap(bookMindVO);
	}

	@Override
	public Result addReadFeel(ZzUsersVO userVO, String content, String bookId) {
		ZzBookReadFeelVO bookReadFeelVO = new ZzBookReadFeelVO();
		bookReadFeelVO.setUserId(userVO.getId());
		ZzChildrenVO childrenVO = userVO.getChildrenVO();
		bookReadFeelVO.setSchoolId(childrenVO.getSchoolId());
		bookReadFeelVO.setSchoolName(childrenVO.getSchoolVO().getName());
		bookReadFeelVO.setGradeName(childrenVO.getGradeVO().getName());
		bookReadFeelVO.setChildrenName(childrenVO.getName());
		bookReadFeelVO.setBookId(bookId);
		bookReadFeelVO.setContent(content);
		return zzChildrenService.addReadFeel(bookReadFeelVO);
	}

	@Override
	public Result readFeelList(ZzUsersVO userVO, String bookId) {
		Result result = new DefaultResult();
		ZzBookReadFeelVO bookReadFeelVO = new ZzBookReadFeelVO();
		ZzChildrenVO childrenVO = userVO.getChildrenVO();
		if (null == childrenVO) {
			return result;
		}
		bookReadFeelVO.setSchoolId(childrenVO.getSchoolId());
		bookReadFeelVO.setGradeName(childrenVO.getGradeVO().getName());
		bookReadFeelVO.setBookId(bookId);
		return zzChildrenService.getByUserByClassReadFeel(bookReadFeelVO);
	}

	@Override
	public Result myReadFeelDel(String id) {
		return zzChildrenService.myReadFeelDel(id);
	}

	@Override
	public Result getByGradeBorrowBook(String gradeId) {
		return zzChildrenService.getByGradeBorrowBook(gradeId);
	}


}
