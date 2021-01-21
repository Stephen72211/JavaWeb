package com.atguigu.mvcapp.dao.impl;

import java.util.List;

import com.atguigu.mvcapp.dao.CustomerDAO;
import com.atguigu.mvcapp.dao.DAO;
import com.atguigu.mvcapp.domain.CriteriaCustomer;
import com.atguigu.mvcapp.domain.Customer;

public class CustomerDAOJdbcImpl extends DAO<Customer>implements CustomerDAO {

	/**
	 * 
	 *  > 实现 CustomerDAO 接口中的方法
	 */
	@Override
	public List<Customer> getAll() {
		
		String sql = "SELECT id,name,address,phone FROM customersnew";
		return getList(sql);
	}

	@Override
	public void save(Customer customer) {
		
		String sql = "INSERT INTO  customersnew(name,address,phone) VALUES(?,?,?) ";

		update(sql, customer.getName(),customer.getAddress(),customer.getPhone());	
		
	}

	@Override
	public Customer get(Integer id) {
		
		String sql = "SELECT id,name,address,phone FROM customersnew WHERE id = ?";
	
		return get(sql, id);
	}

	@Override
	public void delete(Integer id) {
		
		String sql = "DELETE FROM customersnew WHERE id = ?";
		
		update(sql, id);

	}

	@Override
	public Long getCountWithName(String name) {
		String sql = "SELECT COUNT(id) FROM customersnew WHERE name = ?";
		
		return getForValue(sql,name);
	}

	@Override
	public List<Customer> getListWithCriteriaCustomersCustomer(CriteriaCustomer cc) {
		
		String sql = "SELECT id,name,address,phone FROM customersnew WHERE name LIKE ? "
				+ "AND address LIKE ? AND phone LIKE ?";
		
		return getList(sql,cc.getName(),cc.getAddress(),cc.getPhone());
	}

	@Override
	public void update(Customer customer) {
		
		String sql = "UPDATE customersnew SET name = ?,address = ?, phone = ? "
				+ "WHERE id = ?";
		
		update(sql,customer.getName(),customer.getAddress(),customer.getPhone(),customer.getId());
	}

}
