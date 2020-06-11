package com.zone.dao;

import com.zone.bean.User;

public interface UserDao extends BaseDao{

	Integer createUser(User user);

	void updateUser(User user);

	User selectUserById(Integer id);

	User selectUserByName(String name);
	
}
