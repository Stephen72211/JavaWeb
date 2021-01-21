package com.atguigu.mvcapp.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.atguigu.mvcapp.db.JdbcUtils;

/**
 *  > 封装了基本的 CRUD 的方法，以供子类继承使用
 *  > 直接在方法中获取数据库连接.
 *  > 整个 DAO采用 DBUtils 解决方案..
 * @param <T>: 当前 DAO 处理的实体类的类型是什么
 */
public class DAO<T> {
	
	private QueryRunner queryRunner = new QueryRunner();
	private Class<T> clazz;
	
	public DAO(){
		
		// getClass 得到的是子类的 Class，再得到其父类的   带泛型的类型
		
		Type superClass = getClass().getGenericSuperclass();
		
		//System.out.println(superClass);
		
		// 如果 superClass 确实是带参数，并且和 ParameterizedType 的类型一致的话
		// 就进行强转
		
		if(superClass instanceof ParameterizedType) {
			
			ParameterizedType parameterizedType = (ParameterizedType) superClass;
			// 获取泛型的参数
			Type [] typeArgs = parameterizedType.getActualTypeArguments();
			
			// 获取 typeArgs 不为空并且 > 0，就获取第一个
			
			if(typeArgs != null && typeArgs.length > 0) {
				
				//如果这第一个 是 Class 类型的话
				if(typeArgs[0] instanceof Class) {
					
					clazz = (Class<T>) typeArgs[0];
				}
				
			}
		
		}
		
	}

	/**
	 * > 该方法封装了 INSERT、DELETE、UPDATE 操作
	 * @param sql: SQL 语句
	 * @param args: 填充 SQL 语句的占位符.
	 */
	
	public void update(String sql, Object ... args) {
		
		Connection connection = null;
		
		try {
			
			connection = JdbcUtils.getConnection();
			
			queryRunner.update(connection,sql, args);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseConnection(connection);
		}
		
	}
	
	
	/**
	 * > 返回对应的 T 的一个实体对象.
	 * @param sql
	 * @param args
	 * @return
	 */
	public T get(String sql, Object ... args) {
		Connection connection = null;
		
		try {
			connection = JdbcUtils.getConnection();
			
			return queryRunner.query(connection, sql, new BeanHandler<T>(clazz), args);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtils.releaseConnection(connection);
		}
		
		return null;
	}
	
	/**
	 * > 返回对应的 T 的一组实体对象.
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<T> getList(String sql, Object ... args) {
		
		Connection connection = null;
		
		try {
			connection = JdbcUtils.getConnection();
			
			return queryRunner.query(connection, sql, new BeanListHandler<T>(clazz), args);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtils.releaseConnection(connection);
		}
		return null;
	}	
	
	
	/**
	 * > 返回某一个字段的值: 返回某一条记录的 customerName,或返回数据表中有多少条记录等.
	 * @param sql
	 * @param args
	 * @return
	 */
	public <E> E getForValue(String sql, Object ... args) {
		Connection connection = null;
		
		try {
			connection = JdbcUtils.getConnection();
			
			return (E) queryRunner.query(connection, sql, new ScalarHandler(), args);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtils.releaseConnection(connection);
		}
		
		return null;
	}

}
