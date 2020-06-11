package com.zone.dao;

import java.util.List;

import com.zone.bean.PageBean;

public interface PageBeanDao extends BaseDao {

	<T> PageBean getPageBean(Class beanClass,Integer recordCount,Integer pageID);
	
	//<T> Integer create(Class<T> bean);
	
	void delete(Class beanClass,Integer id);
	
	//<T> T reset(Class beanClass);
	
	<T> T selectByID(Class beanClass,Integer id);
	
	<T> List<T> selectByName(Class beanClass,String name);
}
