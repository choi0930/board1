<%@ page language="java" contentType="text/html; charset=UTF-8"
     isELIgnored="false"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%
    	request.setCharacterEncoding("utf-8");
    	String commend = request.getParameter("commend");
    	
    %>
    <c:set var="contextPath" value="${pageContext.request.contextPath }"/>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="${contextPath }/moiveUser/login">로그인</a>
<ul>
	<c:if test="${commend=='check'}">
		<li><a href="${contextPath}/moiveUser/ticketing">영화 예매하기</a></li>
		<li><a href="#">예매 확인하기</a></li>
		<li><a href="#">예매 취소하기</a></li>
	</c:if>
	
	<li><a href="#">관리자 메뉴</a></li>
	</ul>
</body>
</html>