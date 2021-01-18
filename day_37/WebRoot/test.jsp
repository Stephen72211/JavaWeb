<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.atguigu.javaweb.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="atguigu" uri="http://atguigu.com/myTag/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


		<%
			List<Customer> customers = new ArrayList<Customer>();
			customers.add(new Customer(1,"AAA"));
			customers.add(new Customer(2,"BBB"));
			customers.add(new Customer(3,"CCC"));
			customers.add(new Customer(4,"DDD"));
			customers.add(new Customer(5,"EEE"));
			
			request.setAttribute("customers", customers);
		 %>
		 
		 <atguigu:foreach items="${requestScope.customers }" var="cost">
		 	${cost.id } : ${cost.name } <br>
		 </atguigu:foreach>

		<br><br>
		<atguigu:printUpper time="10">${param.str }</atguigu:printUpper>
		<br><br>
		<atguigu:testJspFragment>hello : ${param.name }</atguigu:testJspFragment>
		
</body>
</html>