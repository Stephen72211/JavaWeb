package com.atguigu.mvcapp.test;

import java.util.List;

import org.junit.Test;

import com.atguigu.mvcapp.dao.CustomerDAO;
import com.atguigu.mvcapp.dao.impl.CustomerDAOJdbcImpl;
import com.atguigu.mvcapp.domain.Customer;
import com.atguigu.mvcapp.domain.CriteriaCustomer;

public class CustomerDAOJdbcImplTest {

	private CustomerDAO customerDAO = 
					new CustomerDAOJdbcImpl();
	
	@Test
	public void testGetAll() {
		
		List<Customer> customers = customerDAO.getAll();
		
		for (Customer customer : customers) {
			System.out.println(customer);
		}
	}

	@Test
	public void testSave() {
		Customer customer = new Customer();
		
		customer.setAddress("WuHan");
		customer.setName("Ken");
		customer.setPhone("13511992654");
		
		customerDAO.save(customer);
	}

	@Test
	public void testGetInteger() {
		
		System.out.println(customerDAO.get(2));
	}

	@Test
	public void testDelete() {
		customerDAO.delete(1);
	}

	@Test
	public void testGetCountWithName() {
		System.out.println(customerDAO.getCountWithName("Jerry"));
	}
	
	@Test
	public void testCetListWithCriteriaCustomersCustomer(){
		CriteriaCustomer criteriaCustomer = new CriteriaCustomer(null,"u",null);
						
		List<Customer> customers = customerDAO.getListWithCriteriaCustomersCustomer(criteriaCustomer);
		
		System.out.println(customers);
	}

}
