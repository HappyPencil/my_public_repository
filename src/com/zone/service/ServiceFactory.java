package com.zone.service;

import java.io.IOException;
import java.util.Properties;

public class ServiceFactory {

	private ServiceFactory(){};
	public static ServiceFactory getServiceFactory(){
		return new ServiceFactory();
	};
	
	public BaseService getServiceImp(Class service){
		BaseService serviceImp = null;
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getClassLoader()
					.getResourceAsStream("com/zone/conf/servicefactory.properties"));
			serviceImp = (BaseService)Class.forName(properties.getProperty(service.getSimpleName()))
					.newInstance();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return serviceImp;
	}

}
