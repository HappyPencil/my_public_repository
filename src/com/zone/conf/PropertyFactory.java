package com.zone.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyFactory {

	private static PropertyFactory propertyFactory = null;
	private Properties prop = new Properties();;
	
	private PropertyFactory() {
		try {
			prop.load(this.getClass().getClassLoader()
					.getResourceAsStream("com/zone/conf/propertyfactory.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static PropertyFactory getFactory() {
		if (propertyFactory!=null) {
			return propertyFactory;
		}else {
			return new PropertyFactory();
		}
	}
	
/*正则表达式
 * 待定
 * 	public Properties getRecordProperties(Class t) {
		Properties properties = new Properties();
		
		
		return null;
	}
	*/
	
	
	public Properties getPropertiesByName(String fileName) {
		Properties properties = new Properties();
		String fileClassPath = new String();
		if (prop.containsKey(fileName)) {
			fileClassPath = prop.getProperty(fileName);
			try {
				properties.load(this.getClass().getClassLoader()
						.getResourceAsStream(fileClassPath));
				return properties;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			throw new RuntimeException("error.file not register");
		}
		return null;
	}
}