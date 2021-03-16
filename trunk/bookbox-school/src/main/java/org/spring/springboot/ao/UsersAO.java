package org.spring.springboot.ao;

import com.amall.books.commons.domain.ZzChildrenVO;
import com.amall.books.commons.domain.ZzUsersVO;
import com.amall.commons.result.Result;

public interface UsersAO {

	Result get(ZzUsersVO usersVO);

	Result detail(ZzUsersVO usersVO);
	
	Result getUserSchool(String userId);
	
	Result getChildren(ZzChildrenVO childrenVO);
}
