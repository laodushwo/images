package org.spring.springboot.ao;

import com.amall.commons.result.Result;

public interface DeviceAO {
	
	/**
	 * 根据班级编号查询信息
	 * 
	 * @param gradeCode
	 * @return
	 */
	Result devMessage(String gradeCode);
	
	/**
	 * 设备注册
	 * 
	 * @param serial
	 * @param gradeCode
	 * @return
	 */
	Result register(String serial, String gradeCode);
	
	/**
	 * 设备登录
	 * 
	 * @param serial
	 * @param pwd
	 * @return
	 */
	Result appLogin(String serial, String pwd);
	
	/**
	 * 生成二维码
	 * 
	 * @param gradeCode
	 * @return
	 */
	Result appQrcode(String gradeCode);
	
	/**
	 * 微信二维码
	 * 
	 * @return
	 */
	Result getQRCodeUrl();

	/**
	 * 定时上报设备信息
	 * 
	 * @param serial
	 * @param gradeCode
	 * @return
	 */
	Result reportState(String serial, String gradeCode);
}
