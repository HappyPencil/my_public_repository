package com.zone.test;

import org.junit.Test;

import com.zone.bean.User;

public class TestSQL<T> {

	public TestSQL() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void test_001() {
	
		
		try {
			test_002(User.class);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*User user = new User();
		System.out.println(User.class.getName());
		System.out.println(user.getClass().getName());*/
		
	}

	public <T> T test_002(Class class1) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		
		System.out.println(class1.getSimpleName());
		return null;
		//System.out.println(t.getClass().getSimpleName());
		//System.out.println(t.getClass().getTypeName());
		//Field[] fields = t.getClass().getFields();
		
		/*Class<T> e = (Class<T>) t.getClass().newInstance();
		Field[] fields1 = e.getClass().getDeclaredFields();
		Field[] fields2 = t.getClass().newInstance().getClass().getDeclaredFields();
		
		for (int i = 0; i < fields2.length; i++) {
			System.out.println("_____"+fields2[i].getName()+"________");
		}
		return t;*/
		
	}
}

