<%@ page language="java" contentType="text/html; charset=UTF-8"
    isELIgnored="false"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>studentInfo</title>
<style>
	table{
	border-collapse: collapse;
	}
	h1{
	text-align: center;
	}
	a{
	text-decoration: none;
	color:green;
	}
	a:hover {
	color:red;
	}
	a:visited {
	color:green;
	}
</style>
</head>
<body>
	<h1>회원가입</h1>
	<form method="post" action="${contextPath }/moiveUser/addUser" >
		<table align="center">
			<tr>
				<td width="200"><p align="right">아이디</p></td>
				<td width="400"><input type="text" name="id"></td>
			</tr>
			<tr>
				<td width="200"><p align="right">비밀번호</p></td>
				<td width="400"><input type="password" name="pwd"></td>
			</tr>
			<tr>
				<td width="200"><p align="right">이름</p></td>
				<td width="400"><input type="text" name="name"></td>
			</tr>
			<tr>
				<td width="200"><p align="right">이메일</p></td>
				<td width="400"><input type="text" name="email"></td>
			</tr>
			<tr>
				<td width="200"><p align="right"><a href="#" >메인페이지 이동</a></p></td>
				<td width="400"><input type="submit" value="회원가입" >
				<input type="reset" value="다시입력" ></td>
				
			</tr>
			
		</table>
	</form>
</body>
</html>