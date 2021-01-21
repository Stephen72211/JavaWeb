package com.atguigu.mvcapp.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;



/**
 *  JDBC 操作的工具类
 */
public class JdbcUtils {
	
	private static DataSource dataSource = null;
	
	static {
		dataSource = new ComboPooledDataSource("mvcapp");
	}
	
	
	/**
	 *  > 返回一个数据源的一个 Connection 对象。
	 * @return
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	/**
	 * > 释放 Connection 连接
	 * @param connection
	 */
	public static void releaseConnection(Connection connection) {
		
		try {
			
			if(connection != null) {
				connection.close();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
