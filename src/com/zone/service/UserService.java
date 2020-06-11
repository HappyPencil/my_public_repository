package com.zone.service;

import java.math.BigDecimal;
import java.util.List;

import com.zone.bean.PageBean;
import com.zone.bean.User;

public interface UserService extends BaseService{

	User register(String name,String password,BigDecimal count);
	
	User login(Integer id,String password);
	
	List<?> selectProductsByName(String name);
	
	void checkCart(Integer cart_id);
	
	void addToOrder(Integer cart_id);
	
	void reset(User user);

}
