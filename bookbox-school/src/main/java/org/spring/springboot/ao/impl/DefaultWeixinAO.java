package org.spring.springboot.ao.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.spring.springboot.ao.WeixinAO;
import org.spring.springboot.util.WeixinUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amall.app.abstacts.AbstractAO;
import com.amall.books.commons.dointerface.ZzUsersService;
import com.amall.books.commons.domain.ZzUsersVO;
import com.amall.commons.result.DefaultResult;
import com.amall.commons.result.Result;

@Service
public class DefaultWeixinAO extends AbstractAO implements WeixinAO {

	@Resource
	private ZzUsersService zzUsersService;
	@Resource
	private WeixinUtils weixinUtils;

	@Override
	public Result checkUser(String code) {
		logger.info("code:{}", code);
		Result result = new DefaultResult();
		
		// 根据条件获取AccessToken
		String appid = getMsg("appid");
		String secret = getMsg("secret");
		String codeStr = WeixinUtils.execute(getMsg("wx.getaccessTokenUrl", new String[] { appid, secret, code }), null, "GET");
		logger.info("codeStr={}", codeStr);
		JSONObject openstr = JSONObject.parseObject(codeStr);
		String openid = openstr.getString("openid");
		if(StringUtils.isBlank(openid)) {
			result.setErrors("U0001", getMsg("U0001"));
			return result;
		}
		
		// 根据OpenId查询用户是否在本地存在
		Result qresult = zzUsersService.getByOpenId(openid);
		logger.info("qresult=={}", JSON.toJSONString(qresult));
		ZzUsersVO usersVO = (ZzUsersVO) qresult.get("usersVO");
		if(usersVO != null) {
			result.setModel("usersVO", usersVO);	
			return result;
		}
		
		// 获取微信用户信息，然后解析插入本地
		ZzUsersVO users = new ZzUsersVO();
		users.setOpenid(openid);
		String accessToken = openstr.getString("access_token");
		String userinfo = WeixinUtils.execute(getMsg("wx.user.detail.url", new String[] { accessToken, openid }), null, "GET");
		logger.info("userinfo={}", userinfo);
		JSONObject userObject = JSONObject.parseObject(userinfo);
		users.setNickName(userObject.getString("nickname"));
		users.setHeadImage(userObject.getString("headimgurl"));
		users.setSex(userObject.getIntValue("sex"));
		Result aresult = zzUsersService.add(users);
		result.setModel("usersVO", aresult.get("usersVO"));
		return result;
	}
	
	@Override
	public Result signture(String url) {
		Result result = new DefaultResult();
		String appid = getMsg("appid");
		String secret = getMsg("secret");
		weixinUtils.sign(result, appid, secret, url);
		return result;
	}
	

}