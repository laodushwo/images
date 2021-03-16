package org.spring.springboot.ao;

import com.amall.commons.result.Result;

public interface AppVersionAO {

	/**
	 * 获取单条记录
	 * 
	 * @param identification
	 * @return
	 */
	Result getByIdentification(String identification);
}
