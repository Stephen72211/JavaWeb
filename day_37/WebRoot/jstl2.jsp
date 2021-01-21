<%@page import="com.atguigu.javaweb.Customer"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'jstl.jsp' starting page</title>

  </head>
  <meta http-equiv="Content-Type" content="text-html; charset=UTF-8">
	<body>
	
		<h4>c:url 产生一个 url 地址.可以 Cookie 是否可用来智能进行 URL 重写,
			还可以对 GET 请求的参数进行编码
		 </h4>
		
		<c:url value="/test.jsp" var="testurl">
			<c:param name="name" value="尚硅谷"></c:param>
		</c:url>
		
		url: ${testurl }
<%-- 	<h4>c:redirect: 使当前 JSP 页面重定向到指定的页面</h4>
		<br><br>
		<c:redirect url="jstl2.jsp"></c:redirect>
		
		<br><br>
		<h4>c:import: 可以包含任何页面到当前页面</h4>
		<br><br>
		<c:import url="http://www.baidu.com"></c:import> --%>
		
		
		
		<br><br>
		<h4>c:forEach: 可以对 数组、Collection、Map 进行遍历</h4>
		
		<c:forEach begin="1" end="10" step="3" var="i">
			${i } -- 
		</c:forEach>
		<br><br>
		
		<%
			List<Customer> custs = new ArrayList<Customer>();
			custs.add(new Customer(1,"AAA"));
			custs.add(new Customer(2,"BBB"));
			custs.add(new Customer(3,"CCC"));
			custs.add(new Customer(4,"DDD"));
			custs.add(new Customer(5,"EEE"));
			
			request.setAttribute("custs", custs);
		 %>
		
		<c:forEach var="cust" items="${requestScope.custs }" begin="0" end="4" step="1"
			varStatus="status">
			${cust.id} 	--${cust.name } -- ${status.count}<br>
		</c:forEach>
		<br><br>
		<!-- 遍历 Map -->
		
		<%
			Map<String,Customer> custMap = new HashMap<String,Customer>();
			
			custMap.put("a",new Customer(1,"AAA"));
			custMap.put("b",new Customer(2,"BBB"));
			custMap.put("c",new Customer(3,"CCC"));
			custMap.put("d",new Customer(4,"DDD"));
			custMap.put("e",new Customer(5,"EEE"));
			custMap.put("f",new Customer(6,"FFF"));
			
			request.setAttribute("custMap", custMap);
			
		 %>
		
		<c:forEach var="custMap" items="${requestScope.custMap }">
		
			${custMap.key} : ${custMap.value.name}<br>
		</c:forEach>
		<br><br>
			<!-- 遍历数组 -->
		遍历 数组：
		<br>
		<%
			String[] names = new String[]{"A","B","C"};
			request.setAttribute("names", names);
		 %>
		 
		 <c:forEach var="names" items="${requestScope.names }">
		 	
		 	${names}<br>
		 
		 </c:forEach>
		
		<br><br>
		<c:set value="20" var="age" scope="request"></c:set>
		<c:if test="${requestScope.age > 18 }">成年了！</c:if>
		<br>
		<c:if test="${param.age > 18 }" var="isAdult" scope="request"></c:if>
		isAdult:  <c:out value="${requestScope.isAdult }"></c:out>
		<br>
		<c:choose>
			<c:when test="${param.age > 60 }">老年</c:when>
			<c:when test="${param.age > 40 }">中年</c:when>
			<c:when test="${param.age > 18 }">青年</c:when>
			<c:otherwise>未成年</c:otherwise>
		</c:choose>
		<br>
		
		
	</body>
</html>
