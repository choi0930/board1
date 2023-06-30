<%@ page language="java" contentType="text/html; charset=UTF-8"
    isELIgnored="false"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:set var="contextPath" value="${pageContext.request.contextPath }" />
    <%
    request.setCharacterEncoding("utf-8");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
<script type="text/javascript">
function readURL(input){ //미리보기 이미지
	if (input.files && input.files[0]){
		var reader = new FileReader();
		reader.onload = function (e){
			$('#preview').attr('src', e.target.result);
		}
		reader.readAsDataURL(input.files[0]);
	}
}

function backToList(obj){ //리스트로 돌아가기
		obj.action = "${contextPath}/board6/listArticles.do";
		obj.submit();
	}

</script>
</head>
<body>
	<h1 style="text-align: center;">답글쓰기</h1>
	<form name="frmReply" method="post" action="${contextPath}/board6/addReply.do" enctype="multipart/form-data">
		<table align="center">
			<tr>
				<td align="right">글쓴이: &nbsp; </td>
				<td><input type="text" size="5" value="Fail" disabled /></td>
			</tr>
			<tr>
				<td align="center">글제목:&nbsp;</td>
				<td><input type="text" size="67" maxlength="100" name="title"></td>
			</tr>
			<tr>
				<td align="right" valign="top"><br>글내용:&nbsp;</td>
				<td><textarea name="content" rows="10" cols="65" maxlength="4000"></textarea></td>
			</tr>
			<tr>
				<td align="right">이미지파일 첨부:</td>
				<td><input type="file" name="imageFileName" onchange="readURL(this);">
				<td><img src="" id="preview" width=200 height="200" /></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td>
					<input type="submit" value="답글반영하기" />
					<input type="button" value="취소" onClick="backToList(this.form)" />
				</td>
			</tr>
		</table>
	</form> 
</body>
</html>