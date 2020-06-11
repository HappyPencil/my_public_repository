package com.zone.dao;

import java.io.IOException;
import java.util.Properties;

import com.zone.conf.PropertyFactory;
import com.zone.daoImp.PageBeanDaoImp;

public class DaoFactory {

	private DaoFactory(){};
	public static DaoFactory getFactory(){
		return new DaoFactory();
	}
	public BaseDao getDaoImp(Class dao){
		BaseDao daoImp = null;
		/*Properties properties = new Properties();
		try {
			//properties = PropertyFactory.getFactory().getProperties("daofactory");
			properties.load(this.getClass().getClassLoader()
					.getResourceAsStream("com/zone/conf/daofactory.properties"));
			daoImp = (BaseDao) Class.forName(properties.getProperty(dao.getSimpleName()))
					.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		Properties properties = PropertyFactory.getFactory()
				.getPropertiesByName("daofactory");
		try {
			daoImp = (BaseDao) Class.forName(properties.getProperty(dao.getSimpleName()))
					.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return daoImp;
	}

}
