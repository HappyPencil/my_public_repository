package com.zone.serviceImp;

import java.math.BigDecimal;
import java.util.List;

import com.zone.bean.PageBean;
import com.zone.bean.Product;
import com.zone.bean.User;
import com.zone.dao.DaoFactory;
import com.zone.dao.PageBeanDao;
import com.zone.dao.ProductDao;
import com.zone.dao.UserDao;
import com.zone.service.UserService;

public class UserServiceImp implements UserService {

	public UserServiceImp() {
	}

	public User register(String name, String password, BigDecimal count) {
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user.setCount(count);
		UserDao userDaoImp = (UserDao) DaoFactory.getFactory().getDaoImp(UserDao.class);
		Integer id = null;
		if (userDaoImp.selectUserByName(name)!=null){
			return null;
		}else {
			id = userDaoImp.createUser(user);
			user.setId(id);
			return user;
		}
	}

	//UserNotExistException|PasswordException
	public User login(Integer id, String password) {
		UserDao userDaoImp = (UserDao) DaoFactory.getFactory().getDaoImp(UserDao.class);
		User user = userDaoImp.selectUserById(id);
		if ((user!=null)&&(user.getPassword().equals(password))) {
			return user;
		}else {
			return null;
		}
	}
	

	public void checkCart(Integer cart_id) {
		// TODO Auto-generated method stub
		
	}

	public void addToOrder(Integer cart_id) {
		// TODO Auto-generated method stub
		
	}

	public List<?> selectProductsByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void reset(User user) {
		UserDao Imp = (UserDao) DaoFactory.getFactory().getDaoImp(UserDao.class);
		Imp.updateUser(user);
	}
}
