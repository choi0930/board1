<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
    <c:set var="contextPath" value="${pageContext.request.contextPath }" />
    <%
    request.setCharacterEncoding("utf-8");
    String commend = request.getParameter("commend");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%if (commend.equals("id")){ %>
	
		<h1>이미 있는 아이디 입니다</h1>
	
<%} %>
<%if (commend.equals("email")){ %>
	
		<h1>잘못된 이메일형식 입니다</h1>
	
<%} %>	
	<a href="${contextPath}/member152/memberForm.do">회원가입창</a>
</body>
</html>