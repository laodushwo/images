package org.spring.springboot.ao.impl;

import javax.annotation.Resource;

import org.spring.springboot.ao.DeviceAO;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.amall.admin.commons.util.Q;
import com.amall.app.abstacts.AbstractAO;
import com.amall.books.commons.dointerface.DeviceService;
import com.amall.books.commons.domain.ZzDevicePwdVO;
import com.amall.books.commons.domain.ZzDeviceVO;
import com.amall.commons.result.DefaultResult;
import com.amall.commons.result.Result;

@Service
public class DefaultDeviceAO extends AbstractAO implements DeviceAO {

	@Resource
	private DeviceService deviceService;
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;

	@Override
	public Result devMessage(String gradeCode) {
		return deviceService.devMessage(gradeCode);
	}
	
	@Override
	public Result register(String serial, String gradeCode) {
		Result result = new DefaultResult();
		if (Q.isEmpty(serial) || Q.isEmpty(gradeCode)) {
			result.setErrors("A0002", getMsg("A0002"));
			return result;
		}
		ZzDeviceVO deviceVO = new ZzDeviceVO();
		deviceVO.setSerial(serial);
		deviceVO.setGradeCode(gradeCode);
		return deviceService.zAdd(deviceVO);
	}
	
	@Override
	public Result appLogin(String serial, String pwd) {
		Result result = new DefaultResult();
		if (Q.isEmpty(pwd) || Q.isEmpty(serial)) {
			result.setErrors("A0002", getMsg("A0002"));
			return result;
		}
		ZzDevicePwdVO devicePwdVO = new ZzDevicePwdVO();
		devicePwdVO.setSerial(serial);
		devicePwdVO.setPwd(pwd);
		return deviceService.getByDevicePwd(devicePwdVO);
	}

	@Override
	public Result reportState(String serial, String schoolCode) {
		Result result = new DefaultResult();
		
		// 为防止并发量大，无法及时返还信息，这里做异步操作
//		taskExecutor.execute(new Runnable() {
//			@Override
//			public void run() {
//				DeviceVO deviceVO = new DeviceVO();
//				deviceVO.setSerial(serial);
//				deviceVO.setSchoolCode(schoolCode);
//				deviceVO.setCount(count);
//				deviceVO.setLendCount(lendCount);
//				deviceService.update(deviceVO);
//			}
//		});
		return result;
	}

	@Override
	public Result appQrcode(String gradeCode) {
		return deviceService.getZzDeviceGradeCode(gradeCode);
//		QRCodeUtil.zxingCodeCreate("123456", "F:\\qrcode", 500, "F:\\qrcode\\1.jpg");
	}

	@Override
	public Result getQRCodeUrl() {
		// url
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
//		String urs = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbc966e0536c0678f&redirect_uri=http://wx.feimread.com/mobile/wx/check/user&response_type=code&scope=snsapi_userinfo&state=${u}&connect_redirect=1#wechat_redirect";
		String appid = "wxbc966e0536c0678f";
		String callbackUrl = "http://14.120.117.72:5500/school/wx/pcAuth";
		Object urlState = System.currentTimeMillis();
		// 权限 snsapi_userinfo snsapi_base
		String scope = "snsapi_base";
		url = String.format(url, appid, callbackUrl, scope, urlState);
		return null;
	}

}
