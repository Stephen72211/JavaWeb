package com.atguigu.mvcapp.dao;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atguigu.mvcapp.dao.impl.CustomerDAOJdbcImpl;
import com.atguigu.mvcapp.dao.impl.CustomerDAOXmlImpl;
import com.atguigu.mvcapp.domain.CriteriaCustomer;
import com.atguigu.mvcapp.domain.Customer;

public class CustomerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CustomerDAO customerDAO = new CustomerDAOJdbcImpl();
	//private CustomerDAO customerDAO = new CustomerDAOXmlImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 1. ��ȡ  ServletPath: /edit.do �� /addCustomer.do
		String servletPath = request.getServletPath();
		
		// 2. ȥ�� / �� .do, �õ������� edit �� addCustomer �������ַ���
		String methodName = servletPath.substring(1);
		methodName = methodName.substring(0, methodName.length()-3);
		
		// 3. ͨ�������� methodName ��Ӧ�ķ���.
		
		Method method;
		try {
			
		// 4. ���÷�����ö�Ӧ�ķ���.	
			method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(this, request,response);	
		} catch (Exception e) {
			// e.printStackTrace();
			response.sendRedirect("error.jsp");
		} 
	
	}


	
	private void deleteCustomer(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		String idStr = request.getParameter("id");
		int id = 0;
		// try... catch ������: ��ֹ idStr ����תΪ int ����.
		// ������ת�� id = 0,�޷�����ɾ������.
		try {
			id = Integer.parseInt(idStr);
			customerDAO.delete(id);
			response.sendRedirect("query.do");
		} catch (Exception e) {}
		
		
	}

	private void query(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		// 1. ����  CustomerDAO �� getAll() �õ� Customer �ļ���
		String name = request.getParameter("name");
		
		String address = request.getParameter("address");
		
		String phone = request.getParameter("phone");
		
		List<Customer> customers = customerDAO.getListWithCriteriaCustomersCustomer(new CriteriaCustomer(name,address,phone));
		
		// 2. �� Customer �ļ��Ϸ��� request ��
		request.setAttribute("customers", customers);
		
		// 3. ת��ҳ�浽 index.jsp (����ʹ�� �ض���)
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	private void addCustomer(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		// 1. ��ȡ������: name,address,phone
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		// 2. ���� name �Ƿ��Ѿ���ռ�á�
		// 2.1 ���� CustomerDao �� getCountWithName(String name) ��ȡ name �����ݿ����Ƿ����
		
		Long count = customerDAO.getCountWithName(name);
		System.out.println(count);
		// 2.2 ������ֵ���� 0 ������Ӧ newcustomer.jsp ҳ��:
		// ͨ��ת���ķ�ʽ����Ӧ 
		if(count > 0) {
			// 2.2.1 Ҫ���� newcustomer.jsp ҳ����ʾһ��������Ϣ�� �û��� name �Ѿ���ռ��,������ѡ��!
			// �� request �з���һ������ message : "�û��� name�ѱ�ռ�ã�"
			// ��ҳ����ͨ�� request.getAttribute �ķ�ʽ��ʾ��
			request.setAttribute("message", "�û���" + name +"�ѱ�ռ��,������ѡ��");
			
			// 2.2.2 newcustomer.jsp �ı�ֵ���Ի��ԡ�
			// ͨ�� value="<%= request.getParameter("name") == null ? "" : request.getParameter("name")%>"
			// �����л���.
			// 2.2.3 ��������.return
			
			request.getRequestDispatcher("/newCustomer.jsp").forward(request, response);
			return;
			
		}
			Customer customer = new Customer(name, address, phone);
			
			customerDAO.save(customer);
			
			response.sendRedirect("success.jsp");
	}

	private void edit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String forwardPath = "/error.jsp";
		// 1. ��ȡ������� id
		String idStr = request.getParameter("id");
		
		int id = -1;
		
		try {
			// 2. ����  customerDAO ��  get(id) �����õ�  Customer ����.
			Customer customer = customerDAO.get(Integer.parseInt(idStr));
			
			
			
			if(customer != null){
				forwardPath = "/updatecustomer.jsp";
				// 3. �� Customer �������  ,���� update.jsp
				request.setAttribute("customer", customer);
			}
		} catch (Exception e) {}
		// 4. ��Ӧ update.jsp,ת��.
		request.getRequestDispatcher(forwardPath).forward(request, response);
	}	
	
	private void update(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		
		// 1. ��ȡ������: id,name,address,phone
		String id = request.getParameter("id");
		String oldName = request.getParameter("oldName");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		
		
		// 2. ���� name �Ƿ�ռ��:
		// ������ = ,������  equalsIgnoreCase ���Դ�Сд
		if(!oldName.equalsIgnoreCase(name)){
			Long count = customerDAO.getCountWithName(name);
			// 2.1 �Ƚ� name �� oldName �Ƿ���ͬ,����ͬ˵�� name ����.
			// 2.2 ������ͬ,����� CustomerDAO �� getCountWithName(String name) ��ȡ name �����ݿ����Ƿ����
			// 2.3 ������ֵ���� 0 ,����Ӧ newCustomer.jsp ҳ��,ͨ��ת���ķ�ʽ����Ӧ .
			// 2.2.1 Ҫ���� updatecustomer.jsp ҳ����ʾһ��������Ϣ�� �û��� name �Ѿ���ռ��,������ѡ��!
			// �� request �з���һ������ message : "�û��� name�ѱ�ռ�ã�"
			// ��ҳ����ͨ�� request.getAttribute �ķ�ʽ��ʾ��
			if(count > 0){
				request.setAttribute("message", "�û���" + name +"�ѱ�ռ��,������ѡ��");
				request.getRequestDispatcher("/updatecustomer.jsp").forward(request, response);
				return;
			}
			
		}
			// 3. ����֤ͨ��,��ѱ�������װΪһ�� Customer ���� customer
			Customer customer = new Customer(name, address, phone);
			
			customer.setId(Integer.parseInt(id));
			
			// 4. ���� CustomerDAO �� update(Customer customer) ִ�и��²���
			customerDAO.update(customer);
			//�ض��� query.do
			response.sendRedirect("query.do");
			
		}
		
	
		
		
		

		
		// 2.2.2 newcustomer.jsp �ı�ֵ���Ի��ԡ�
		// address,phone ��ʾ�ύ�����µ�ֵ, �� name ��ʾ oldName,���������ύ�� name
		 
		// 2.2.3 ��������.return
		
		
		// ��ͨ������� CustomerDAO �� update(Customer customer) ִ�и��²���.
		//Customer customer = new Customer(Integer.parseInt(id),name, address, phone);
		
		//customerDAO.update(customer);
		
		// 5. �ض��� query.do: ʹ���ض�����Ա�����ֱ����ظ��ύ����.
		//response.sendRedirect("success.jsp");
		
	
	
}
