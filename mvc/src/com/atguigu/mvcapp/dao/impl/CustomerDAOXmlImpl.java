package com.atguigu.mvcapp.dao.impl;

import java.util.List;

import com.atguigu.mvcapp.dao.CustomerDAO;
import com.atguigu.mvcapp.domain.CriteriaCustomer;
import com.atguigu.mvcapp.domain.Customer;

public class CustomerDAOXmlImpl implements CustomerDAO {

	@Override
	public List<Customer> getAll() {
		System.out.println("getAll");
		return null;
	}

	@Override
	public List<Customer> getListWithCriteriaCustomersCustomer(CriteriaCustomer cc) {
		System.out.println("getListWithCriteriaCustomersCustomer");
		return null;
	}

	@Override
	public void save(Customer customer) {
		System.out.println("save");
		
	}

	@Override
	public Customer get(Integer id) {
		System.out.println("get");
		return null;
	}

	@Override
	public void delete(Integer id) {
		System.out.println("delete");
		
	}

	@Override
	public Long getCountWithName(String name) {
		System.out.println("getCountWithName");
		return null;
	}

	@Override
	public void update(Customer customer) {
		System.out.println("update");
	}

}
