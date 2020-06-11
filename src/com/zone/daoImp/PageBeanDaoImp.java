package com.zone.daoImp;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.zone.bean.BaseBean;
import com.zone.bean.PageBean;
import com.zone.conf.PropertyFactory;
import com.zone.dao.PageBeanDao;
import com.zone.jdbc.JdbcTools;

public class PageBeanDaoImp implements PageBeanDao {

	public PageBeanDaoImp() {
	}

	
	
	
	/**
	 * 通用PageBean
	 * 获取具体PageBean
	 */
	public <T> PageBean getPageBean(Class beanClass, Integer recordCount,Integer pageID) {
		
		Properties prop_table = PropertyFactory.getFactory().getPropertiesByName("table");
		
		String table = prop_table.getProperty(beanClass.getSimpleName());
		String sql = "select count(*) recordtotal from "+table;
		
		Connection connection = JdbcTools.getConnection(JdbcTools.getProperty());
		PreparedStatement preparedStatement = JdbcTools.getPrepareStatement(connection, sql);
		ResultSet resultSet = JdbcTools.getResultSet(preparedStatement);
		Integer recordTotal = 0;
		try {
			resultSet.next();
			recordTotal = resultSet.getInt("recordtotal");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcTools.release(connection, preparedStatement, resultSet);
		}
		
		//判断超页 如果超页返回当前记录数最后一页
		PageBean pageBean = new PageBean(pageID,recordTotal,recordCount);
		pageBean.setListBean(this.getBeanList(beanClass,pageBean.getRecordStart(),
				pageBean.getRecordCount()));
		return pageBean;
	}
	
	
	
	/**
	 * 获取PageBean中的List
	 * 返回值list<?>和list<T>均可
	 * @param pageID
	 * @param recordCount
	 * @param bean
	 * @return
	 */
	public <T> List<?> getBeanList(Class beanClass,Integer index, Integer count){
		
			Properties prop_table = PropertyFactory.getFactory()
					.getPropertiesByName("table");
			Properties prop_record = PropertyFactory.getFactory()
					.getPropertiesByName(beanClass.getSimpleName());
				
			Field[] fields = beanClass.getDeclaredFields();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < fields.length; i++) {
				if (prop_record.containsKey(fields[i].getName())) {
					sb.append(prop_record.getProperty(fields[i].getName()))
					.append(" as ")
					.append(fields[i].getName())
					.append((i<fields.length-1)?(","):(""));
				}
			}
			//"*"的ASCII十进制码为42（避免转义错误）
			char star = (char)42;
			//new String 或者new StringBufer就相当于只写一对双引号（将对象中的值全部赋值为null）
			String record = ((sb.toString()).equals(""))?(sb.toString()):(new Character(star).toString());
			String table = prop_table.getProperty(beanClass.getSimpleName());
			String sql = "select "
					+record
					+" from "
					+table
					+" limit "
					+index.intValue()+","+count.intValue();
			Connection connection = JdbcTools.getConnection(JdbcTools.getProperty());
			PreparedStatement preparedStatement = JdbcTools.getPrepareStatement(connection, sql);
			
			ResultSet resultSet = JdbcTools.getResultSet(preparedStatement);
			List<T> list = new ArrayList<T>(); 
			try {
				while(resultSet.next()) {
					Method[] methods = beanClass.getDeclaredMethods();
					@SuppressWarnings("unchecked")
					T bean = (T) beanClass.newInstance();
					for (int j = 0; j < methods.length; j++) {
						for (int k = 0; k < fields.length; k++) {
							//是否是setter
							if (methods[j].getName().substring(0, 3).toLowerCase().equals("set")) {
								//是否是该属性的setter
								if (methods[j].getName().substring(3).toLowerCase()
										.equals(fields[k].getName().toLowerCase())) {
									//是"*"还是具体字段
									if (record.contains("*")) {
										Object value = resultSet.getObject(prop_record
												.getProperty(fields[k].getName().toLowerCase()));
										methods[j].invoke(bean, value);
									}else {
										Object value = resultSet.getObject(fields[k].getName());
										methods[j].invoke(bean, value);
									}
								}/*else {
									break;
								}*/
							}/*else {
								break;
							}*/
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
			} catch (InstantiationException e) {
				e.printStackTrace();
			}finally {
				JdbcTools.release(connection, preparedStatement, resultSet);
			}
			return list;
	}



/*
	public <T> Integer create(Class<T> bean) {
		try {
			T t = (T) Class.forName(bean.getClass().getName());
			return t.toString();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
*/



	public void delete(Class beanClass,Integer id) {
		Properties prop_table = PropertyFactory.getFactory().getPropertiesByName("table");
		Properties prop_record = PropertyFactory.getFactory().getPropertiesByName(beanClass.getSimpleName());
		String table = prop_table.getProperty(beanClass.getSimpleName());
		Field[] fields = beanClass.getDeclaredFields();
		StringBuffer sb = new StringBuffer();
		String recordName = "";
		for (int i = 0; i < fields.length; i++) {
			if (prop_record.containsKey(fields[i].getName())) {
				sb.append(prop_record.getProperty(fields[i].getName()))
				.append(" as ")
				.append(fields[i].getName())
				.append((i<fields.length-1)?(","):(""));
				if (fields[i].getName().toLowerCase().equals("id")) {
					recordName = prop_record.getProperty(fields[i].getName());
				}
			}
		}
		String sql = "delete from "+table+" where "+recordName+"="+id;
		Connection connection = JdbcTools.getConnection(JdbcTools.getProperty());
		PreparedStatement preparedStatement = JdbcTools.getPrepareStatement(connection, sql);
		JdbcTools.doUpdate(preparedStatement);
		JdbcTools.release(connection, preparedStatement, null);
	}


	public <T> T selectByID(Class beanClass,Integer id) {
		Properties prop_table = PropertyFactory.getFactory()
				.getPropertiesByName("table");
		Properties prop_record = PropertyFactory.getFactory()
				.getPropertiesByName(beanClass.getSimpleName());
		Field[] fields = beanClass.getDeclaredFields();
		StringBuffer sb = new StringBuffer();
		String recordName = "";
		for (int i = 0; i < fields.length; i++) {
			if (prop_record.containsKey(fields[i].getName())) {
				sb.append(prop_record.getProperty(fields[i].getName()))
				.append(" as ")
				.append(fields[i].getName())
				.append((i<fields.length-1)?(","):(""));
				if (fields[i].getName().toLowerCase().equals("id")) {
					recordName = prop_record.getProperty(fields[i].getName());
				}
			}
		}
		//"*"的ASCII十进制码为42（避免转义错误）
		char star = (char)42;
		//new String 或者new StringBufer就相当于只写一对双引号（将对象中的值全部赋值为null）
		String record = ((sb.toString()).equals(""))?(sb.toString()):(new Character(star).toString());
		String table = prop_table.getProperty(beanClass.getSimpleName());
		String sql = "select "+record+" from "+table+" where "+recordName+"="+id;
		Connection connection = JdbcTools.getConnection(JdbcTools.getProperty());
		PreparedStatement preparedStatement = JdbcTools.getPrepareStatement(connection, sql);
		
		ResultSet resultSet = JdbcTools.getResultSet(preparedStatement);
		List<T> list = new ArrayList<T>(); 
		try {
			while(resultSet.next()) {
				Method[] methods = beanClass.getDeclaredMethods();
				@SuppressWarnings("unchecked")
				T bean = (T) beanClass.newInstance();
				for (int j = 0; j < methods.length; j++) {
					for (int k = 0; k < fields.length; k++) {
						//是否是setter
						if (methods[j].getName().substring(0, 3).toLowerCase().equals("set")) {
							//是否是该属性的setter
							if (methods[j].getName().substring(3).toLowerCase()
									.equals(fields[k].getName().toLowerCase())) {
								//是"*"还是具体字段
								if (record.contains("*")) {
									Object value = resultSet.getObject(prop_record
											.getProperty(fields[k].getName().toLowerCase()));
									methods[j].invoke(bean, value);
								}else {
									Object value = resultSet.getObject(fields[k].getName());
									methods[j].invoke(bean, value);
								}
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
		} catch (InstantiationException e) {
			e.printStackTrace();
		}finally {
			JdbcTools.release(connection, preparedStatement, resultSet);
		}
		if (list.size()!=0) {
			return list.remove(0);
		}else {
			return null;			
		}
	}




	public <T> List<T> selectByName(Class beanClass,String name) {

		Properties prop_table = PropertyFactory.getFactory()
				.getPropertiesByName("table");
		Properties prop_record = PropertyFactory.getFactory()
				.getPropertiesByName(beanClass.getSimpleName());
			
		Field[] fields = beanClass.getDeclaredFields();
		StringBuffer sb = new StringBuffer();
		String recordName = "";
		for (int i = 0; i < fields.length; i++) {
			if (prop_record.containsKey(fields[i].getName())) {
				sb.append(prop_record.getProperty(fields[i].getName()))
				.append(" as ")
				.append(fields[i].getName())
				.append((i<fields.length-1)?(","):(""));
				if (fields[i].getName().toLowerCase().equals("name")) {
					recordName = prop_record.getProperty(fields[i].getName());
				}
			}
		}
		//"*"的ASCII十进制码为42（避免转义错误）
		char star = (char)42;
		//new String 或者new StringBufer就相当于只写一对双引号（将对象中的值全部赋值为null）
		String record = ((sb.toString()).equals(""))?(sb.toString()):(new Character(star).toString());
		String table = prop_table.getProperty(beanClass.getSimpleName());
		String sql = "select "+record+" from "+table+" where "+recordName+"="+name;
		Connection connection = JdbcTools.getConnection(JdbcTools.getProperty());
		PreparedStatement preparedStatement = JdbcTools.getPrepareStatement(connection, sql);
		
		ResultSet resultSet = JdbcTools.getResultSet(preparedStatement);
		List<T> list = new ArrayList<T>(); 
		try {
			while(resultSet.next()) {
				Method[] methods = beanClass.getDeclaredMethods();
				@SuppressWarnings("unchecked")
				T bean = (T) beanClass.newInstance();
				for (int j = 0; j < methods.length; j++) {
					for (int k = 0; k < fields.length; k++) {
						//是否是setter
						if (methods[j].getName().substring(0, 3).toLowerCase().equals("set")) {
							//是否是该属性的setter
							if (methods[j].getName().substring(3).toLowerCase()
									.equals(fields[k].getName().toLowerCase())) {
								//是"*"还是具体字段
								if (record.contains("*")) {
									Object value = resultSet.getObject(prop_record
											.getProperty(fields[k].getName().toLowerCase()));
									methods[j].invoke(bean, value);
								}else {
									Object value = resultSet.getObject(fields[k].getName());
									methods[j].invoke(bean, value);
								}
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
		} catch (InstantiationException e) {
			e.printStackTrace();
		}finally {
			JdbcTools.release(connection, preparedStatement, resultSet);
		}
		return list;
	}

}
