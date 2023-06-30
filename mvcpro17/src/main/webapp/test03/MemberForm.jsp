<%@ page language="java" contentType="text/html; charset=UTF-8"
    isELIgnored="false"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <c:set var = "contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입창</title>
<script type="text/javascript">
	
	
	
	function check(){
		var id=frmMember1.id.value;
		var pwd = frmMember1.pwd.value;
		var name = frmMember1.name.value;
		var email= frmMember1.email.value;
		
		var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		
		if(id==""){
			alert("아이디를 입력해주세요");
			const element = document.getElementById('userId');
			element.innerText = '아이디를 입력해주세요';
			return false;
		}
		if(pwd==""){
			alert("비밀번호를 입력해주세요");
			pwd.focus();
			return false;
		}
		if(name==""){
			alert("이름을 입력해주세요");
			name.focus();
			return false;
		}
		
		if(regExp.test(email) == false){
			alert("이메일 형식이 아닙니다");
			return false;
		}
		frmMember1.submit();
	}
</script>
</head>
<body>
<form name = "frmMember1" method="post" action="${contextPath }/member152/addMember.do">
	<h1 style="text-align:center;">회원 가입창</h1>
	<table align="center">
		<tr>
			<td width="200"><p align="right">아이디</p></td>
			<td width="400"><input type="text" name="id"></td>
			<td><div id="userId"></div></td>
		</tr>
		<tr>
			<td width="200"><p align="right">비밀번호</p></td>
			<td width="400"><input type="password" name="pwd"></td>
			<td id="#userPwd"></td>
		</tr>
		<tr>
			<td width="200"><p align="right">이름</p></td>
			<td width="400"><input type="text" name="name"></td>
			<td id="#userName"></td>
		</tr>
		<tr>
			<td width="200"><p align="right">이메일</p></td>
			<td width="400"><input type="text" name="email"></td>
			<td id="#userEmail"></td>
		</tr>
		<tr>
			<td width="200"></td>
			<td width="400"><!--  <input type="submit" value="가입하기">-->
			<button type="button" onclick="check()">가입하기</button>
			<input type="reset" value="다시입력"></td>
		</tr>
	</table>
</form>
</body>
</html>