package org.spring.springboot.ao;

import com.amall.commons.result.Result;

public interface WeixinAO {

	/**
	 * 检查用户是否存在（存在就不管，不存在就添加一个用户）
	 * 
	 * @param code
	 * @return
	 */
	Result checkUser(String code);
	
	/**
	 * 获取配置信息
	 * 
	 * @return
	 */
	Result signture(String url);

}
