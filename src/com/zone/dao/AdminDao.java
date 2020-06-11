package com.zone.dao;

import com.zone.bean.Admin;

public interface AdminDao extends BaseDao{

	void createAdmin(Admin admin);
	
	void deleteAdmin(Admin admin);
	
	void updateAdmin(Admin admin);
	
	Admin selectAdminById(Integer id);
}
