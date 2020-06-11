package com.zone.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.zone.bean.User;
import com.zone.dao.UserDao;
import com.zone.jdbc.JdbcTools;

public class UserDaoImp implements UserDao {

	public UserDaoImp() {
	}


	public void updateUser(User user) {
		String sql = "update user set user_name=?,user_password=?,user_count=?";
		Connection connection = JdbcTools.getConnection(JdbcTools.getProperty());
		PreparedStatement preparedStatement = null;
		ResultSet resultset = null; 
		try {
			preparedStatement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setBigDecimal(3, user.getCount());
			preparedStatement.executeUpdate();
			resultset = preparedStatement.getGeneratedKeys();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer id = null;
		try {
			while(resultset.next()){
				id = resultset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcTools.release(connection, preparedStatement, resultset);
	}

	public User selectUserById(Integer id) {
		
		String sql = "select user_id id,"
				+ "user_name name,"
				+ "user_password password,"
				+ "user_count count"
				+ " from user where user_id=?";
		
		Connection connection = JdbcTools.getConnection(JdbcTools.getProperty());
		PreparedStatement preparedStatement = JdbcTools.getPrepareStatement(connection, sql);
		try {
			preparedStatement.setInt(1, id.intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResultSet resultSet = JdbcTools.getResultSet(preparedStatement);
		List<User> list = JdbcTools.ResultsetToList(resultSet, User.class);
		if (list.size()!=0) {
			JdbcTools.release(connection, preparedStatement, resultSet);
			return list.remove(0);
		}else {
			JdbcTools.release(connection, preparedStatement, resultSet);
			return null;
		}
	}

	/**
	 * 
	 *  插入一条记录并获取自增主键值
	 */
	public Integer createUser(User user) {
		String sql = "insert into user(user_name,user_password,user_count) value(?,?,?)";
		Connection connection = JdbcTools.getConnection(JdbcTools.getProperty());
		PreparedStatement preparedStatement = null;
		ResultSet resultset = null; 
		try {
			preparedStatement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setBigDecimal(3, user.getCount());
			preparedStatement.executeUpdate();
			resultset = preparedStatement.getGeneratedKeys();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer id = null;
		try {
			while(resultset.next()){
				id = resultset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcTools.release(connection, preparedStatement, resultset);
		return id;
	}

	public User selectUserByName(String name) {
		String sql = "select user_id id,"
				+ "user_name name,"
				+ "user_password password,"
				+ "user_count count"
				+ " from user where user_name=?";
		Connection connection = JdbcTools.getConnection(JdbcTools.getProperty());
		PreparedStatement preparedStatement = JdbcTools.getPrepareStatement(connection, sql);
		try {
			preparedStatement.setString(1, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResultSet resultSet = JdbcTools.getResultSet(preparedStatement);
		List<User> list = JdbcTools.ResultsetToList(resultSet, User.class);
		JdbcTools.release(connection, preparedStatement, resultSet);
		if (list.size()!=0) {
			return list.remove(0);
		}
		return null;
	}


}
