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
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
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
		}else{
			const element = document.getElementById('userId');
			element.innerText = '';
		}
		if(pwd==""){
			alert("비밀번호를 입력해주세요");
			const element = document.getElementById('userPwd');
			element.innerText = '비밀번호를 입력해주세요'
			return false;
		}else{
			const element = document.getElementById('userPwd');
			element.innerText = ''
		}
		if(name==""){
			alert("이름을 입력해주세요");
			const element = document.getElementById('userName');
			element.innerText = '이름을 입력해주세요'
			name.focus();
			return false;
		}else{
			const element = document.getElementById('userName');
			element.innerText = ''
		}
		
		if(regExp.test(email) == false){
			alert("이메일 형식이 아닙니다");
			return false;
		}
		frmMember1.submit();
	}
</script>
<style>
*{
	margin:0px;
	padding: 0px;
}
	#main{
		width:400px;
		margin: 0 auto;
	}
	.error_text{
		color:red;
	}
</style>
</head>
<body>
<form name = "frmMember1" method="post" action="${contextPath }/member152/addMember.do">
	<h1 style="text-align:center;">회원 가입창</h1>
	<section id="main">
	<div class="form-row">
		<div class="col-md-3 form-group">
			<label for="id">아이디</label>
			<input id="id" type="text" name="id">
			<div id="userId" class="error_text"></div>
			</div>
			<div class="col-md-3 form-group">
			<label for="pwd">비밀번호</label>
			<input id = "pwd" type="password" name="pwd">
			<div id="userPwd" class="error_text"></div>
			</div>
			<div class=" col-md-3 form-group">
			<label for="name">이름</label>
			<input id = "name" type="text" name="name">
			<div id="userName" class="error_text"></div>
			</div>
			<div class="col-md-3 form-group">
			<label for="email">이메일</label>
			<input id="email" type="text" name="email">
			</div>
		</div>
		<br>
			
			<!--  <input type="submit" value="가입하기">-->
			<button  class="btn btn-primary" type="button" onclick="check()">가입하기</button>
			<button  class="btn btn-primary" type="reset">다시 입력</button>
		
	
	</section>
</form>
</body>
</html>