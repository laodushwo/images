package org.spring.springboot.ao;

import com.amall.books.commons.domain.ZzChildrenVO;
import com.amall.books.commons.domain.ZzUsersVO;
import com.amall.commons.result.Result;

public interface ChildrenAO {

	Result add(ZzChildrenVO childrenVO);
	
	Result update(ZzChildrenVO childrenVO);
	
	Result addMindMap(ZzUsersVO userVO, String data, String bookId);
	
	Result findUserByClassMindMap(ZzUsersVO userVO, String bookId);
	
	Result addReadFeel(ZzUsersVO userVO, String content, String bookId);
	
	Result readFeelList(ZzUsersVO userVO, String bookId);
	
	Result myReadFeelDel(String id);
	
	Result getByGradeBorrowBook(String gradeId);
}
