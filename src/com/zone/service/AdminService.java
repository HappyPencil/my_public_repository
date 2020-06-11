package com.zone.service;

import java.math.BigDecimal;

import com.zone.bean.Admin;
import com.zone.bean.Product;
import com.zone.bean.User;

public interface AdminService extends BaseService{

	void createUser(String user_name,String user_password,BigDecimal user_count);
	
	void deleteUser(Integer user_id);
	
	void updateUser(String user_name,String user_password,BigDecimal user_count);
	
	User selectUser(Integer user_id);
	
	Admin login(Integer admin_id, String admin_password);
	
	void createProduct(String product_name,BigDecimal product_price,Integer product_count,String product_detail);
	
	void deleteProduct(Integer product_id);
	
	void updateProduct(String product_name,BigDecimal product_price,Integer product_count,String product_detail);
	
	Product selectProduct(Integer product_id);
}
