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

		// 1. 获取  ServletPath: /edit.do 或 /addCustomer.do
		String servletPath = request.getServletPath();
		
		// 2. 去除 / 和 .do, 得到类似于 edit 或 addCustomer 这样的字符串
		String methodName = servletPath.substring(1);
		methodName = methodName.substring(0, methodName.length()-3);
		
		// 3. 通过反射获得 methodName 对应的方法.
		
		Method method;
		try {
			
		// 4. 利用反射调用对应的方法.	
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
		// try... catch 的作用: 防止 idStr 不能转为 int 类型.
		// 若不能转则 id = 0,无法进行删除操作.
		try {
			id = Integer.parseInt(idStr);
			customerDAO.delete(id);
			response.sendRedirect("query.do");
		} catch (Exception e) {}
		
		
	}

	private void query(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 调用  CustomerDAO 的 getAll() 得到 Customer 的集合
		String name = request.getParameter("name");
		
		String address = request.getParameter("address");
		
		String phone = request.getParameter("phone");
		
		List<Customer> customers = customerDAO.getListWithCriteriaCustomersCustomer(new CriteriaCustomer(name,address,phone));
		
		// 2. 把 Customer 的集合放入 request 中
		request.setAttribute("customers", customers);
		
		// 3. 转发页面到 index.jsp (不能使用 重定向)
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	private void addCustomer(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 获取表单参数: name,address,phone
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		// 2. 检验 name 是否已经被占用。
		// 2.1 调用 CustomerDao 的 getCountWithName(String name) 获取 name 在数据库中是否存在
		
		Long count = customerDAO.getCountWithName(name);
		System.out.println(count);
		// 2.2 若返回值大于 0 ，则相应 newcustomer.jsp 页面:
		// 通过转发的方式来响应 
		if(count > 0) {
			// 2.2.1 要求在 newcustomer.jsp 页面显示一个错误消息： 用户名 name 已经被占用,请重新选择!
			// 在 request 中放入一个属性 message : "用户名 name已被占用！"
			// 在页面上通过 request.getAttribute 的方式显示。
			request.setAttribute("message", "用户名" + name +"已被占用,请重新选择！");
			
			// 2.2.2 newcustomer.jsp 的表单值可以回显。
			// 通过 value="<%= request.getParameter("name") == null ? "" : request.getParameter("name")%>"
			// 来进行回显.
			// 2.2.3 结束方法.return
			
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
		// 1. 获取请求参数 id
		String idStr = request.getParameter("id");
		
		int id = -1;
		
		try {
			// 2. 调用  customerDAO 的  get(id) 方法得到  Customer 对象.
			Customer customer = customerDAO.get(Integer.parseInt(idStr));
			
			
			
			if(customer != null){
				forwardPath = "/updatecustomer.jsp";
				// 3. 把 Customer 对象放入  ,传给 update.jsp
				request.setAttribute("customer", customer);
			}
		} catch (Exception e) {}
		// 4. 响应 update.jsp,转发.
		request.getRequestDispatcher(forwardPath).forward(request, response);
	}	
	
	private void update(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		
		// 1. 获取表单参数: id,name,address,phone
		String id = request.getParameter("id");
		String oldName = request.getParameter("oldName");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		
		
		// 2. 检验 name 是否被占用:
		// 不能用 = ,必须用  equalsIgnoreCase 忽略大小写
		if(!oldName.equalsIgnoreCase(name)){
			Long count = customerDAO.getCountWithName(name);
			// 2.1 比较 name 和 oldName 是否相同,若相同说明 name 可用.
			// 2.2 若不相同,则调用 CustomerDAO 的 getCountWithName(String name) 获取 name 在数据库中是否存在
			// 2.3 若返回值大于 0 ,则响应 newCustomer.jsp 页面,通过转发的方式来响应 .
			// 2.2.1 要求在 updatecustomer.jsp 页面显示一个错误消息： 用户名 name 已经被占用,请重新选择!
			// 在 request 中放入一个属性 message : "用户名 name已被占用！"
			// 在页面上通过 request.getAttribute 的方式显示。
			if(count > 0){
				request.setAttribute("message", "用户名" + name +"已被占用,请重新选择！");
				request.getRequestDispatcher("/updatecustomer.jsp").forward(request, response);
				return;
			}
			
		}
			// 3. 若验证通过,则把表单参数封装为一个 Customer 对象 customer
			Customer customer = new Customer(name, address, phone);
			
			customer.setId(Integer.parseInt(id));
			
			// 4. 调用 CustomerDAO 的 update(Customer customer) 执行更新操作
			customerDAO.update(customer);
			//重定向到 query.do
			response.sendRedirect("query.do");
			
		}
		
	
		
		
		

		
		// 2.2.2 newcustomer.jsp 的表单值可以回显。
		// address,phone 显示提交表单的新的值, 而 name 显示 oldName,而不是新提交的 name
		 
		// 2.2.3 结束方法.return
		
		
		// 若通过则调用 CustomerDAO 的 update(Customer customer) 执行更新操作.
		//Customer customer = new Customer(Integer.parseInt(id),name, address, phone);
		
		//customerDAO.update(customer);
		
		// 5. 重定向到 query.do: 使用重定向可以避免出现表单的重复提交问题.
		//response.sendRedirect("success.jsp");
		
	
	
}
