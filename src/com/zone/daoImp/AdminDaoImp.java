package com.zone.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.zone.bean.Admin;
import com.zone.bean.User;
import com.zone.dao.AdminDao;
import com.zone.jdbc.JdbcTools;

public class AdminDaoImp implements AdminDao {

	public AdminDaoImp() {
	}

	public void createAdmin(Admin admin) {
		
	}

	public void deleteAdmin(Admin admin) {
		
	}

	public void updateAdmin(Admin admin) {
		
	}

	public Admin selectAdminById(Integer id) {
		String sql = "select admin_id id,"
				+ "admin_password password"
				+ " from admin where admin_id=?";
		Connection connection = JdbcTools.getConnection(JdbcTools.getProperty());
		PreparedStatement preparedStatement = JdbcTools.getPrepareStatement(connection, sql);
		try {
			preparedStatement.setInt(1, id.intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResultSet resultSet = JdbcTools.getResultSet(preparedStatement);
		List<Admin> list = JdbcTools.ResultsetToList(resultSet, Admin.class);
		if (list.size()!=0) {
			JdbcTools.release(connection, preparedStatement, resultSet);
			return list.remove(0);
		}else {
			JdbcTools.release(connection, preparedStatement, resultSet);
			return null;
		}
	}

}
