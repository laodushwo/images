package org.spring.springboot.ao;

import com.amall.commons.page.Page;
import com.amall.commons.result.Result;

public interface SchoolAO {

	/**
	 * 获取一个学校信息
	 * 
	 * @param id
	 * @return
	 */
	Result get(String id);
	/**
	 * 根据学校编号查询学校信息
	 * 
	 * @param code
	 * @return
	 */
	Result getByCode(String code);
	
	/**
	 * 根据学校ID分页查询所有在架书籍(在架和借出的)
	 * 
	 * @param schoolId
	 * @param page
	 * @return
	 */
	Result bookList(String schoolId, Page page);
	
	/**
	 * 根据班级ID分页查询所有在架书籍(在架和借出的)
	 * 
	 * @param gradeId
	 * @param page
	 * @return
	 */
	Result bookGradeList(String gradeId, Page page);
	
	/**
	 * 根据学校ID查询所有班级
	 * 
	 * @param schoolId
	 * @return
	 */
	Result getByGradeList(String schoolId);

}
