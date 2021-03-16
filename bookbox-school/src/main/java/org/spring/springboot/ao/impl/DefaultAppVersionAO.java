package org.spring.springboot.ao.impl;

import javax.annotation.Resource;

import org.spring.springboot.ao.AppVersionAO;
import org.springframework.stereotype.Service;

import com.amall.app.abstacts.AbstractAO;
import com.amall.books.commons.dointerface.AppVersionService;
import com.amall.commons.result.Result;

@Service
public class DefaultAppVersionAO extends AbstractAO implements AppVersionAO {

	@Resource
	private AppVersionService appVersionService;

	@Override
	public Result getByIdentification(String identification) {
		return appVersionService.getByIdentification(identification);
	}
}
