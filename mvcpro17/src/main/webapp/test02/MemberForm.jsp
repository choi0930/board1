<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
    %>
    <%@ taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
    <c:set var="contextPath" value="${pageContext.request.contextPath }" />
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입창</title>
<script type="text/javascript">



/*
function check(){
	
	var id = frm.id.value;
	var pwd = frm.pwd.value;
	var name = frm.name.value;
	var email = frm.email.value;
	var pattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	
	
	if(id =="" && pwd =="" && name==""){
		alert("필수 입력항목 비어있음");
		return false;
		
	}
	if(emailCheck(email) == false){
		alert("이메일 형식이 잘못됨");
		return false;
		
	}

	frm.submit();
}

*/
</script>
</head>
<body>
<form name = "frm" id="frmMember" method="post" action="${contextPath}/member151/addMember.do">
	<h1 style="text-align: center;">회원 가입창</h1>
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
			<td width="200"></td>
			<td width="400"><button  >가입하기</button>
			<input type="reset" value="다시입력"></td>
		</tr>
	</table>
	
	
</form>

</body>
</html>