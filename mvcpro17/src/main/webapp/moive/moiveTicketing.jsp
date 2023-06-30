<%@ page language="java" contentType="text/html; charset=UTF-8"
    isELIgnored="false"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%
    	request.setCharacterEncoding("utf-8");
    %>
    <c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<td width="7%">영화 제목</td>
			<td width="7%">장르</td>
			<td width="7%">예매하기</td>
		</tr>
		<c:forEach var="movie" items="${moviesList}">
			<tr>
				<td>${movie.title}</td>
				<td>${movie.genre}</td>
				<td><a href="${contextPath }/moiveServlet/findByMovieId?id=${movie.id}">예매</a>
			</tr>
		</c:forEach>
		
	</table>
</body>
</html>