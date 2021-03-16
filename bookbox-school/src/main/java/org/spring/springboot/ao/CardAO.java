package org.spring.springboot.ao;

import com.amall.commons.result.Result;

public interface CardAO {

	/**
	 * 根据学校编号获取所有卡号
	 * 
	 * @param gradeCode
	 * @return
	 */
	Result synCard(String gradeCode);
	
}
