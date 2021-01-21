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
 *  > ��װ�˻����� CRUD �ķ������Թ�����̳�ʹ��
 *  > ֱ���ڷ����л�ȡ���ݿ�����.
 *  > ���� DAO���� DBUtils �������..
 * @param <T>: ��ǰ DAO �����ʵ�����������ʲô
 */
public class DAO<T> {
	
	private QueryRunner queryRunner = new QueryRunner();
	private Class<T> clazz;
	
	public DAO(){
		
		// getClass �õ���������� Class���ٵõ��丸���   �����͵�����
		
		Type superClass = getClass().getGenericSuperclass();
		
		//System.out.println(superClass);
		
		// ��� superClass ȷʵ�Ǵ����������Һ� ParameterizedType ������һ�µĻ�
		// �ͽ���ǿת
		
		if(superClass instanceof ParameterizedType) {
			
			ParameterizedType parameterizedType = (ParameterizedType) superClass;
			// ��ȡ���͵Ĳ���
			Type [] typeArgs = parameterizedType.getActualTypeArguments();
			
			// ��ȡ typeArgs ��Ϊ�ղ��� > 0���ͻ�ȡ��һ��
			
			if(typeArgs != null && typeArgs.length > 0) {
				
				//������һ�� �� Class ���͵Ļ�
				if(typeArgs[0] instanceof Class) {
					
					clazz = (Class<T>) typeArgs[0];
				}
				
			}
		
		}
		
	}

	/**
	 * > �÷�����װ�� INSERT��DELETE��UPDATE ����
	 * @param sql: SQL ���
	 * @param args: ��� SQL ����ռλ��.
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
	 * > ���ض�Ӧ�� T ��һ��ʵ�����.
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
	 * > ���ض�Ӧ�� T ��һ��ʵ�����.
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
	 * > ����ĳһ���ֶε�ֵ: ����ĳһ����¼�� customerName,�򷵻����ݱ����ж�������¼��.
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
