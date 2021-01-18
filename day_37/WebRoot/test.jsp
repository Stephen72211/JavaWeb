<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.atguigu.javaweb.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<br><br>
		
		<!-- 父标签打印 name 到控制台 -->
		<atguigu:parentTag>
			<!-- 子标签以父标签的标签体存在.子标签把父标签的 name 属性打印到 JSP 页面上. -->
			<atguigu:sonTag/>
		</atguigu:parentTag>
		
		<br><br>
		
		<atguigu:choose>
			<atguigu:when test="${param.age > 24}">^大学毕业</atguigu:when>
			<atguigu:when test="${param.age > 20}">^高中毕业</atguigu:when>
			<atguigu:otherwise>^高中以下</atguigu:otherwise>
		</atguigu:choose>

		
</body>
</html>