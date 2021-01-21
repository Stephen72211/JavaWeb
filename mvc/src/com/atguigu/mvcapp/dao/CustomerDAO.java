package com.atguigu.mvcapp.dao;

import java.util.List;
import com.atguigu.mvcapp.domain.CriteriaCustomer;

import com.atguigu.mvcapp.domain.Customer;

public interface CustomerDAO {

	public List<Customer> getAll();
	
	public List<Customer> getListWithCriteriaCustomersCustomer(CriteriaCustomer cc);
	
	public void save(Customer customer);
	
	public Customer get(Integer id);
	
	public void delete(Integer id);
	
	/**
	 * > ���غ� name ��ȵ� ��¼��
	 * @param name
	 * @return
	 */
	public Long getCountWithName(String name);

	public void update(Customer customer);
	
	
	
}
