package com.zone.serviceImp;

import java.math.BigDecimal;

import com.zone.bean.Admin;
import com.zone.bean.Product;
import com.zone.bean.User;
import com.zone.dao.AdminDao;
import com.zone.dao.DaoFactory;
import com.zone.service.AdminService;

public class AdminServiceImp implements AdminService {

	public AdminServiceImp() {
		// TODO Auto-generated constructor stub
	}

	public void createUser(String user_name, String user_password, BigDecimal user_count) {
		// TODO Auto-generated method stub
		
	}

	public void deleteUser(Integer user_id) {
		// TODO Auto-generated method stub
		
	}

	public void updateUser(String user_name, String user_password, BigDecimal user_count) {
		// TODO Auto-generated method stub
		
	}

	public User selectUser(Integer user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void createProduct(String product_name, BigDecimal product_price, Integer product_count,
			String product_detail) {
		// TODO Auto-generated method stub
		
	}

	public void deleteProduct(Integer product_id) {
		// TODO Auto-generated method stub
		
	}

	public void updateProduct(String product_name, BigDecimal product_price, Integer product_count,
			String product_detail) {
		// TODO Auto-generated method stub
		
	}

	public Product selectProduct(Integer product_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Admin login(Integer admin_id, String admin_password) {
		AdminDao adminDaoImp = (AdminDao) DaoFactory.getFactory().getDaoImp(AdminDao.class);
		Admin admin = adminDaoImp.selectAdminById(admin_id);
		if (admin != null) {
			if (admin.getPassword().equals(admin_password)) {
				return admin;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}



}
