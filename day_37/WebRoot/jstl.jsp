<%@page import="com.atguigu.javaweb.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<!-- 表达式操作 -->
	<c:set var="name" value="ATGUIGU" scope="page"></c:set>
	 name:   ${pageScope.name }
	<%--
		pageContext.setAttribute("name", "ATGUIGU");
	 --%>
	<br><br>
	<c:set var="subject" value="${param.subject}" scope="session"></c:set>
	subject: ${sessionScope.subject }
	<br><br>
	<%
		Customer cust = new Customer();
		cust.setId(1001);
		request.setAttribute("cust", cust);
	 %>
	 ID1: ${requestScope.cust.id};
	<br>
	<c:set target="${requestScope.cust}" property="id" value="${param.id}"></c:set>
	 ID2: ${requestScope.cust.id};
	<h4>
		<%
			request.setAttribute("book", "<<Java>>");
		 %>
		
		book:  ${requestScope.book }
		<br><br>
		book:  <c:out value="${requestScope.book2 }"></c:out>
	</h4>



</body>
</html>