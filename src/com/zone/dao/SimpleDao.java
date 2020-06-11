package com.zone.dao;

import java.util.List;

public interface SimpleDao extends BaseDao {

	Integer create(Class bean);
	
	void delete(Class bean);
	
	<T> T reset(Class bean);
	
	<T> T selectByID(Integer id);
	
	<T> List<T> selectByName(String name);
	
}
