<%@page import="com.atguigu.mvcapp.dao.impl.CustomerDAOJdbcImpl"%>
<%@page import="com.atguigu.mvcapp.domain.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		<%
			Object msg = request.getAttribute("message");
		
			if(msg != null){
		 %>
		 	<br><br>
		 	<font color="red">
		 <%
		 	out.print(msg);
		  %>	
		 	</font>	
		 	
		 	<br><br>
		 <%	
			}	
		 %>

		 <%
		 	// 处理无法获取 id 的问题
		 	
		 	String id = null;
		 	String name = null;
		 	String oldName = null;
		 	String address = null;
		 	String phone = null;
		 
			Customer customer = (Customer)request.getAttribute("customer");
			// 如果customer 不为空,说明这是从 index.jsp 的  UPDATE 链接过来的. 可以直接从 customer 对象得到以下
			if(customer != null){
				id = customer.getId() + "";
				name = customer.getName();
				oldName = customer.getName();
				address = customer.getAddress();
				phone = customer.getPhone();

			// 如果customer 为空,说明页面是从出错的地方返回的,就需要从 request 得到以下.	
			}else{
				id = request.getParameter("id");
				oldName = request.getParameter("oldName");
				name = request.getParameter("oldName");
				address = request.getParameter("address");
				phone = request.getParameter("phone");
			}
		 %>

		<form action="update.do" method="post">
			<input type="hidden" name="id" value="<%=id%>">
			<input type="hidden" name="oldName" value="<%=oldName%>">
		<table>
			<tr>
				<td>CustomerName:</td>
				<td><input type="text" name="name" value="<%=name%>"></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><input type="text" name="address" value="<%=address%>"></td>
			</tr>			
			<tr>
				<td>Phone:</td>
				<td><input type="text" name="phone" value="<%=phone%>"></td>
			</tr>		
			<tr>
				<td><input type="submit" value="提交"/></td>
			</tr>
		</table>
	</form>


</body>
</html>