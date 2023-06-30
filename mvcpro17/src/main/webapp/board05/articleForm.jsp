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
<title>글쓰기창</title>
<script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
<script type="text/javascript">
	function readURL(input){
		if (input.files && input.files[0]){
			var reader = new FileReader();
			reader.onload = function (e){
				$('#preview').attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
	
	function backToList(obj){
		obj.action = "${contextPath}/board5/listArticles.do";
		obj.submit();
	}
</script>
</head>
<body>
	<h1 style="text-align: center;">새 글 쓰기</h1>                                                  <!-- 파일데이터를 보낼수있는 형식 -->
	<form name="articleForm" method = "post" action="${contextPath }/board5/addArticle.do" enctype="multipart/form-data">
		<table border="0" align="center">
			<tr>
				<td align="right">글제목: </td>
				<td colspan="2"><input type="text" size="67" maxlength="500" name="title"></td>
			</tr>
			<tr>
				<td align="right" valign="top"><br>글내용: </td>
				<td colspan="2"><textarea name="content" rows="10" cols="65" maxlength="4000"></textarea></td>
			</tr>				<!-- 자기소개같은 길게 쓰는 내용일경우 -->
			<tr>
				<td align="right">이미지파일 첨부: </td>
				<td><input type="file" name="imageFileName" onchange="readURL(this);"></td>
				<td><img id="preview" src="#" width=200 height=200 /></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td colspan="2"><input type="submit" value="글쓰기" />
				<input type="button" value="목록보기" onClick="backToList(this.form)"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>