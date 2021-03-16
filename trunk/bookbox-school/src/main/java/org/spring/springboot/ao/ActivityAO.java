package org.spring.springboot.ao;

import com.amall.commons.result.Result;

public interface ActivityAO {
	
	Result addTogeter(String userId, String gradeId, String id, String name, String author, String intro);
	
	Result updateTogether(String gradeId);
	
	Result findTogether(String gradeId);
	
}
