///*
// * Copyright 2013 The JA-SIG Collaborative. All rights reserved.
// * distributed with thi file and available online at
// */
//package org.spring.springboot.util;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.support.SqlSessionDaoSupport;
//import org.springframework.stereotype.Service;
//
///**
// * 批量操作支持
// * 
// * @author ganweibin
// * @File MybatisBatch.java Date:2015年11月11日 下午3:34:51 2015
// */
//@Service("mybatisBatch")
//public class MybatisBatch extends SqlSessionDaoSupport {
//	
//	// @Resource
////     public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
////        super.setSqlSessionFactory(sqlSessionFactory);
////	 }
//	/**
//	 * <p>
//	 * 批量操作支持,主要是根据sqlmapper里面的id和集合进行操作
//	 * </p>
//	 * 
//	 * @param statement
//	 * @param objectList
//	 */
//	public int insertBatch(String statement, List<?> objectList) {
//		if (objectList == null || objectList.isEmpty()) {
//			return 0;
//		}
//		for (Object object : objectList) {
//			getSqlSession().insert(statement, object);
//		}
//		return objectList.size();
//	}
//
//	/**
//	 * 批量更新
//	 * 
//	 * @param statement
//	 *            批量更新的Mapper 的Id
//	 * @param objectList
//	 *            被控制对象集合
//	 * @return
//	 */
//	public int updateBatch(String statement, List<?> objectList) {
//		if (objectList == null || objectList.isEmpty()) {
//			return 0;
//		}
//		for (Object object : objectList) {
//			getSqlSession().update(statement, object);
//		}
//		return objectList.size();
//	}	
//}
