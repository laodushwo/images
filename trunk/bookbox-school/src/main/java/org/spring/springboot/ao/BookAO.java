package org.spring.springboot.ao;

import com.amall.commons.result.Result;

public interface BookAO {
	
	Result getByIsbn(String schoolCode, String isbn);
	
}
