package org.spring.springboot.ao;

import com.amall.commons.page.Page;
import com.amall.commons.result.Result;

public interface BookAO {
	
	Result getByIsbn(String schoolCode, String isbn);
	
	Result bookUpDown(String gradeCode, String isbn, int type, int udType, String location);
	
	Result operate(String phcode, String isbn, int type, String gmtOperate);
	
	Result getByBookDetail(String userId, String isbn);
	
	Result bookLogList(String userId, Page page);
	
	Result qrcodeKeep(String userId, String isbn);
	
	Result mybook(String userId, Page page);
	
	Result getByBookName(String name);
	
	Result getByBookFullName(String name);
	
	Result gradeBookUd(String gradeCode, Page page);
	
	Result gradeDriftUd(String gradeCode, Page page);
	
	Result pageBook(int type);
	
	Result bookHotOrNew(int type, Page page);
	
}
