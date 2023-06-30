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
<title>글 상세보기</title>
<script type="text/javascript">
	
	function backToList(obj){
		obj.action = "${contextPath}/board4/listArticles.do";
		obj.submit();
	}
</script>
<style>
#tr_btn_modify{
display: none;
}
</style>
</head>
<body>
	<form name="frmArticle" method="post" enctype="multipart/form-data">
	<table border="0" align="center">
		<tr>
			<td width="20%" align="center" bgcolor="#FF9933">글번호</td>
			<td><input type="text" value="${article.articleNO }" name="articleNO" disabled="disabled"></td>
		</tr>
		<tr>
			<td width="20%" align="center" bgcolor="#FF9933">작성자 아이디</td>
			<td><input type="text" value="${article.id }" name="id" disabled="disabled"></td>
		</tr>
		<tr>
			<td width="20%" align="center" bgcolor="#FF9933">글제목</td>
			<td>
				<input type="text" value="${article.title }" name="title" id="i_title" disabled />
			</td>
		</tr>
		<tr>
			<td width="20%" align="center" bgcolor="#FF9933">글내용</td>
			<td>
				<textarea rows="20" cols="60" name="content" id="i_content" disabled>${article.content}</textarea>
			</td>
		</tr>
		<c:if test="${not empty article.imageFileName && article.imageFileName != 'null' }">
			<tr>
				<td width="20%" align="center" bgcolor="#FF9933" rowspan="2">이미지</td>
				<td>
					<input type="hidden" name="originalFileName" value="${article.imageFileName }" />
					<img src="${contextPath }/download2.do?imageFileName=${article.imageFileName }&articleNO=${article.articleNO }" id="preview">
				</td>
			</tr>
			<tr>
				<td><input type="file" name="imageFileName" id = "i_imageFileName" disabled onchange="readURL(this);"></td>
			</tr>
			</c:if>
			<tr>
				<td width="20%" align="center" bgcolor="#FF9933">등록일자</td>
				<td><input type="text" value="${article.writeDate }" name="writeDate" disabled="disabled"></td>
			</tr>
			<tr id="tr_btn_modify">
				<td colspan="2" align="center">
					<input type="button" value="수정하기" onclick="fn_modify_article(frmArticle)">
					<input type="button" value="취소" onClick="backToList(frmArticle)">					
				</td>
			</tr>
			<tr id = "tr_btn">
			<td colspan=2 align="center">
					<input type="button" value="수정하기" onclick="fn_enable(this.form)">
					<input type="button" value="삭제하기" onClick="fn_remove_article('${contextPath}')">
					<input type="button" value="리스트로 돌아가기" onClick="backToList(this.form)" >
					<input type="button" value="답글쓰기" onClick=>
				</td>
			</tr>
	</table>
	</form>
</body>
</html>