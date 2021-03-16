package org.spring.springboot.ao.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.spring.springboot.ao.UsersAO;
import org.springframework.stereotype.Service;

import com.amall.app.abstacts.AbstractAO;
import com.amall.books.commons.dointerface.ZzBookLogService;
import com.amall.books.commons.dointerface.ZzCardService;
import com.amall.books.commons.dointerface.ZzChildrenService;
import com.amall.books.commons.dointerface.ZzSchoolService;
import com.amall.books.commons.dointerface.ZzUsersService;
import com.amall.books.commons.domain.ZzCardVO;
import com.amall.books.commons.domain.ZzChildrenVO;
import com.amall.books.commons.domain.ZzGradeVO;
import com.amall.books.commons.domain.ZzSchoolVO;
import com.amall.books.commons.domain.ZzUsersVO;
import com.amall.commons.result.DefaultResult;
import com.amall.commons.result.Result;

@Service
public class DefaultUsersAO extends AbstractAO implements UsersAO {

	@Resource
	private ZzUsersService zzUsersService;
	@Resource
	private ZzCardService zzCardService;
	@Resource
	private ZzBookLogService zzBookLogService;
	@Resource
	private ZzChildrenService zzChildrenService;
	@Resource
	private ZzSchoolService zzSchoolService;

	@Override
	public Result get(ZzUsersVO usersVO) {
		Result result = new DefaultResult();
		// 暂时固定
//		result = zzUsersService.get(usersVO.getId());
		result = zzUsersService.get("123e45b89c9d4e5b9684e5072232kkkk");
		usersVO = (ZzUsersVO) result.get("usersVO");
		
		// 检查用户的角色，如果是普通用户，则跳转到用户首页，如果是班级老师，则跳转老师管理首页
		Result qResult = zzUsersService.checkMobileAdmin(usersVO.getId());
		result.setModel("gradeUsersVO", qResult.get("gradeUsersVO"));
		ZzChildrenVO childrenVO = usersVO.getChildrenVO();
		
		// 如果用户对象里面，小孩对象不为空，则判断小孩绑定的卡和学校是否有相应的数据
		if (null != childrenVO) {
			ZzCardVO cardVO = childrenVO.getCardVO();
			if (null == cardVO) {
				Result qcResult = zzCardService.get(childrenVO.getCardId());
				if (qcResult.isSuccess()) {
					ZzCardVO card = (ZzCardVO) qcResult.get("cardVO");
					childrenVO.setCardVO(card);
				}
				usersVO.setChildrenVO(childrenVO);
			}
			ZzSchoolVO schoolVO = childrenVO.getSchoolVO();
			if (null == schoolVO) {
				Result qscResult = zzSchoolService.get(childrenVO.getSchoolId());
				if (result.isSuccess()) {
					schoolVO = (ZzSchoolVO) qscResult.get("schoolVO");
					childrenVO.setSchoolVO(schoolVO);
					usersVO.setChildrenVO(childrenVO);
				}
			}
			result.setModel("usersVO", usersVO);
			return result;
		}
		
		// 如用户对象里面没小孩，则查询，确认没小孩则直接返回
		Result qresult = zzChildrenService.getByUserId(usersVO.getId());
		childrenVO = (ZzChildrenVO) qresult.get("childrenVO");
		if (null == childrenVO) {
			result.setModel("usersVO", usersVO);
			return result;
		}
		
		// 有小孩判断卡和学校
		Result qvipResult = zzCardService.get(childrenVO.getCardId());
		ZzCardVO cardVO = (ZzCardVO) qvipResult.get("cardVO");
		childrenVO.setCardVO(cardVO);
		ZzSchoolVO schoolVO = childrenVO.getSchoolVO();
		if (null == schoolVO) {
			Result qscResult = zzSchoolService.get(childrenVO.getSchoolId());
			schoolVO = (ZzSchoolVO) qscResult.get("schoolVO");
			childrenVO.setSchoolVO(schoolVO);
			usersVO.setChildrenVO(childrenVO);
		}
		usersVO.setChildrenVO(childrenVO);
		result.setModel("usersVO", usersVO);
		return result;
	}
	
	@Override
	public Result detail(ZzUsersVO usersVO) {
		Result result = new DefaultResult();
		result = zzUsersService.get(usersVO.getId());
		ZzChildrenVO childrenVO = usersVO.getChildrenVO();
		if (childrenVO == null) {
			return result;
		}
		String cardId = childrenVO.getCardId();
		if (StringUtils.isBlank(cardId)) {
			return result;
		}
		Result cresult = zzCardService.get(usersVO.getChildrenVO().getCardId());
		if (cresult.isSuccess()) {
			result.setModel("cardVO", cresult.get("cardVO"));
		}
		return result;
	}

	@Override
	public Result getUserSchool(String userId) {
		return zzChildrenService.getByUserId(userId);
	}

	@Override
	public Result getChildren(ZzChildrenVO childrenVO) {
		Result result = new DefaultResult();
//		Result result = zzChildrenService.get(childrenId);
//		ZzChildrenVO childrenVO = (ZzChildrenVO) result.get("childrenVO");
//		if (null != childrenVO) {
		Result qschoolResult = zzSchoolService.getByGradeId(childrenVO.getGradeId());
		ZzGradeVO gradeVO = (ZzGradeVO) qschoolResult.get("gradeVO");
		childrenVO.setGradeCode(gradeVO.getCode());
		childrenVO.setGradeVO(gradeVO);
		result.setModel("childrenVO", childrenVO);
//		}
		return result;
	}

}
