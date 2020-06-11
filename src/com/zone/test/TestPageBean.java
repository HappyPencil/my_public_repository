package com.zone.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.jws.soap.SOAPBinding.Use;

import org.junit.Test;

import com.zone.bean.BaseBean;
import com.zone.bean.PageBean;
import com.zone.bean.Product;
import com.zone.bean.User;
import com.zone.conf.PropertyFactory;
import com.zone.dao.BaseDao;
import com.zone.dao.DaoFactory;
import com.zone.dao.PageBeanDao;
import com.zone.daoImp.PageBeanDaoImp;
import com.zone.jdbc.JdbcTools;

public class TestPageBean <T>{

	public TestPageBean() {
	}

	@Test
	public void test_001() throws ClassNotFoundException {
		/*PageBean pageBean = ((PageBeanDao)DaoFactory.getFactory()
				.getDaoImp(PageBeanDao.class)).getPageBean(Product.class, 1, 2);
		System.out.println(pageBean.getListBean().get(0));*/
		//System.out.println(test_002(Product.class,new Integer(0),new Integer(1)).size());;
		
		/*Method[] methods = Class.class.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals("forName")) {
				method.invoke(Class<Page>, args)
			}
		}*/
		/*System.out.println(Class.forName(PropertyFactory.getFactory()
				.getPropertiesByName("daofactory")
				.getProperty(PageBeanDao.class.getSimpleName()))
		.toGenericString());
		
		TypeVariable<Class<PageBeanDao>>[] typeVariable = PageBeanDao.class.getTypeParameters();
		for (TypeVariable<?> typeVariable2 : typeVariable) {
			System.out.println(typeVariable2);
		}*/
		/*System.out.println(User.class.getCanonicalName());
		System.out.println(User.class.getTypeName());
		System.out.println(User.class.getSimpleName());
		System.out.println(Use.class.getTypeName().equals(User.class.getName()));
		System.out.println(Class.class.getDeclaredClasses());*/
		@SuppressWarnings("unchecked")
		PageBeanDao daoImp = (PageBeanDao) DaoFactory.getFactory()
				.getDaoImp(PageBeanDao.class);
		PageBean userPage = daoImp.getPageBean(User.class, 2, 1);
		System.out.println(userPage.getListBean().get(1));
	}
	
	
	/*
	
	public List<BaseBean> test_002(Class beanClass,Integer index, Integer count) {
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
		List<BaseBean> list = new ArrayList<BaseBean>(); 
		try {
			while(resultSet.next()) {
				Method[] methods = beanClass.getDeclaredMethods();
				BaseBean bean = (BaseBean) beanClass.newInstance();
				for (int j = 0; j < methods.length; j++) {
					for (int k = 0; k < fields.length; k++) {
						//是否是setter
						if (methods[j].getName().substring(0, 3).toLowerCase().equals("set")) {
							//是否是该属性的setter
							if (methods[j].getName().substring(3).toLowerCase()
									.equals(fields[k].getName().toLowerCase())) {
								//是"*"还是具体字段
								if (record.contains("*")) {
									String value = resultSet.getString(prop_record
											.getProperty(fields[k].getName().toLowerCase()));
									methods[j].invoke(bean, value);
								}else {
									String value = resultSet.getString(fields[k].getName());
									methods[j].invoke(bean, value);
								}
							}else {
								break;
							}
						}else {
							break;
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
	}*/
}
