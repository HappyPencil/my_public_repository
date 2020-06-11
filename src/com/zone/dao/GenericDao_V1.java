package com.zone.dao;

import java.util.List;

public interface GenericDao_V1<T>{

	List<T> selectLimit();
	
	T selectByID(Integer id,Class bean);
	
	T selectByName(String name,Class bean);
	
	void deleteByID(Integer id,Class bean);
	
	Integer createBea(T t,Class bean);
	
	void update(T t,Class bean);
	
}
