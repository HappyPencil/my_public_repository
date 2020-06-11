package com.zone.jdbc;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.commons.beanutils.BeanUtils;

public class JdbcTools {
	
	/**
	 * JDBC拆分步骤  1
	 * 得到配置文件jdbc.properties
	 * @return
	 */
	public static Properties getProperty(){
		Properties properties = new Properties();
		InputStream inputStream = JdbcTools.class.getClassLoader()
									.getResourceAsStream("com/zone/conf/jdbc.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return properties;
	}
	
	/**
	 * JDBC拆分步骤  2
	 * 获取数据库连接
	 * @param properties
	 * @return
	 */
	public static Connection getConnection(Properties properties){
		Connection conn = null;
		try {
			Class.forName(properties.getProperty("driverClass"));
			conn = DriverManager.getConnection(properties.getProperty("jdbcUrl"),properties.getProperty("user"),properties.getProperty("password"));
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * JDBC拆分步骤  3
	 * 准备prepareStatement
	 * @param conn
	 * @param sql
	 * @return
	 */
	public static PreparedStatement getPrepareStatement(Connection conn,String sql){
		PreparedStatement prpsmt = null;
		try {
			prpsmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return prpsmt;
	}
	
	/**
	 * JDBC拆分步骤  4-1
	 * 得到查询结果集
	 * @param prpstmt
	 * @return
	 */
	public static ResultSet getResultSet(PreparedStatement prpstmt){
		ResultSet reslt = (ResultSet)null;
		try {
			reslt = prpstmt.executeQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return reslt;
	}
	
	/**
	 * JDBC拆分步骤  4-2
	 * 执行更新操作
	 * @param prpstmt
	 */
	public static void doUpdate(PreparedStatement prpstmt){
		try {
			prpstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	
	
	/**
	 * JDBC拆分步骤  4-3
	 * 封装查询结果集到list
	 * @param reslt
	 * @param bean
	 * @return
	 */
	public static <T> List<T> ResultsetToList(ResultSet reslt,Class bean){
		List<T> list = new ArrayList<T>();
		T obj = null;
		ResultSetMetaData rsmd = null;
		try {
			rsmd = (ResultSetMetaData) reslt.getMetaData();
			while (reslt.next()) {
				obj = (T) bean.newInstance();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					BeanUtils.setProperty(obj, rsmd.getColumnLabel(i), reslt.getObject(i));
				}
				list.add(obj);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * JDBC拆分步骤  5
	 * 释放jdbc资源
	 * @param conn
	 * @param prpstmt
	 * @param reslt
	 */
	public static void release(Connection conn,PreparedStatement prpstmt,ResultSet reslt){
		try {
			if (reslt!=null) {
				reslt.close();
			}
			if (prpstmt!=null) {
				prpstmt.close();
			}
			if (conn!=null) {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	/**
	 * 通用查询工具方法（测试）
	 * 将压力转移到SQL语句
	 * @param sql
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> query(String sql,Class bean){
		List<T> list = new ArrayList<T>();
		T obj = null;
		Properties properties = JdbcTools.getProperty();
		Connection conn = JdbcTools.getConnection(properties);
		PreparedStatement prpstmt = JdbcTools.getPrepareStatement(conn, sql);
		ResultSet reslt = JdbcTools.getResultSet(prpstmt);
		ResultSetMetaData rsmd = null;
				try {
					rsmd = (ResultSetMetaData) reslt.getMetaData();
					while (reslt.next()) {
						obj = (T) bean.newInstance();
						for (int i = 1; i <= rsmd.getColumnCount(); i++) {
							BeanUtils.setProperty(obj, rsmd.getColumnLabel(i), reslt.getObject(i));
						}
						list.add(obj);
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
		JdbcTools.release(conn, prpstmt, reslt);
		return list;
	}
	
	/**
	 * 简易更新方法
	 * @param sql
	 */
	public static void callUpdate(String sql){
		Properties properties = JdbcTools.getProperty();
		Connection conn = JdbcTools.getConnection(properties);
		PreparedStatement prpstmt = JdbcTools.getPrepareStatement(conn, sql);
		try {
			prpstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		JdbcTools.release(conn, prpstmt, null);
	}
	
}
