package com.zone.test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.zone.bean.PageBean;
import com.zone.bean.User;
import com.zone.conf.PropertyFactory;
import com.zone.dao.DaoFactory;
import com.zone.dao.PageBeanDao;
import com.zone.jdbc.JdbcTools;

public class TestDemo_1 {
	
	public static void main(String[] args) {
		String str = null;
		str = Thread.currentThread().getContextClassLoader().getResource("").toString();
		System.out.println(str);
	}

	@Test
	public <T> void test_01() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		/*String up = "insert into user("
				+ "user_name,"
				+ "user_password,"
				+ "user_count"
				+ ") values('zhou','zhou',10000)";
		JdbcTools.callUpdate(up);*/
		/*
		String sql = "select "
				+ "user_id id,"
				+ "user_name name,"
				+ "user_password password,"
				+ "user_count count"
				+ " from user";
		List<User> list = new ArrayList<User>();
		list = JdbcTools.query(sql, User.class);
		Iterator<User> it = list.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		*/
		/*Properties properties = new Properties();
		try {
			properties.load(
					GuidePath.class.getClassLoader()
					.getResourceAsStream("com/zone/conf/jdbc.properties")
					);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(properties.getProperty("user"));*/
		
		/*Field [] fields = User.class.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			System.out.print(" "+fields[i].getName()+" ");
		}*/
/*	
		PageBeanDao pageBeanDaoImp = (PageBeanDao) DaoFactory.getFactory()
				.getDaoImp(PageBeanDao.class);
		PageBean pageBean = pageBeanDaoImp.getPageBean(User.class, 1, 2);
		System.out.println(pageBean);
*/		
		/*User user1 = new User();
		user1.setName("1");
		User user = null;
		boolean flag = true;
		try {
			user = user1;
			if (flag) {
				user.setName("2");
			}else {
				user.setName("3");
			}
			while(flag) {
				user.setName("4");
				flag = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(user.getName());*/
		
/*		
		String sql = "select * from user where user_id=1;";
		Connection connection = JdbcTools.getConnection(JdbcTools.getProperty());
		PreparedStatement preparedStatement = JdbcTools.getPrepareStatement(connection, sql);
		ResultSet resultSet = JdbcTools.getResultSet(preparedStatement);
		try {
			resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int id = 0;
		String name = null;
		try {
			id = resultSet.getInt(1);
			name = resultSet.getString("user_name");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(id+" "+name);
*/
		Integer index = 0;
		Integer count = 2;
		Class t = User.class;
		
		
		Properties prop_table = PropertyFactory.getFactory()
				.getPropertiesByName("table");
		String simpleName = t.getSimpleName();
		String table = prop_table.getProperty(simpleName);
		
		
		Properties prop_record = PropertyFactory.getFactory()
				.getPropertiesByName(t.getSimpleName());
			
		Field[] fields = t.getDeclaredFields();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < fields.length; i++) {
			if (prop_record.containsKey(fields[i].getName())) {
				sb.append(prop_record.getProperty(fields[i].getName()))
				.append(" as ")
				.append(fields[i].getName())
				.append((i<fields.length-1)?(","):(""));
			}
		}
		
		String record = ((sb.toString())!=null)?(sb.toString()):("*");
		
		String sql = "select "+record+" from "+table+" limit "+index.intValue()+","+count.intValue()+"";
		Connection connection = JdbcTools.getConnection(JdbcTools.getProperty());
		PreparedStatement preparedStatement = JdbcTools.getPrepareStatement(connection, sql);
		/*try {
			preparedStatement.setString(1, record);
			preparedStatement.setString(2, table);
			preparedStatement.setInt(3, index);
			preparedStatement.setInt(4, count);
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		
		ResultSet resultSet = JdbcTools.getResultSet(preparedStatement);
		List<T> list = new ArrayList<T>(); 
		try {
			while(resultSet.next()) {
				T bean = (T) t.newInstance();
					Method[] methods = t.getDeclaredMethods();
					for (int j = 0; j < methods.length; j++) {
						for (int k = 0; k < fields.length; k++) {
							//是否是setter
							if (methods[j].getName().substring(0, 2).equals("set")) {
								//是否是该属性的setter
								if (methods[j].getName().substring(3).toLowerCase()
										.equals(fields[k].getName().toLowerCase())) {
									String value = resultSet.getString(prop_record.getProperty(fields[k].getName()));
									methods[j].invoke(User.class, value);
								}
							}
						}
					}
				list.add(bean);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage()+"error in jdbc");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}finally {
			JdbcTools.release(connection, preparedStatement, resultSet);
		}
		//test
		System.out.println(record);
		System.out.println(sql);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	
	}
}






















