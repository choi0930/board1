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
	
	function fn_enable(obj){ //수정하기 버튼을 눌렀을때 
		document.getElementById("i_title").disabled = false;
		document.getElementById("i_content").disabled = false;
		document.getElementById("i_imageFileName").disabled = false;
		document.getElementById("tr_btn_modify").style.display= "block";
		document.getElementById("tr_btn").style.display = "none";
	}
	
	function fn_modify_article(obj) {//수정 정보 적용
		obj.action = "${contextPath}/board6/modArticle.do";
		obj.submit();
	}
	
	function fn_remove_article(url,articleNO){ //삭제
		var form = document.createElement("form"); //form을 만듬
		form.setAttribute("method","post"); //form method=post
		form.setAttribute("action", url); //form action에 매개변수로 받은 url설정 -> ${contextPath}/board6/removeArticle.do
		var articleNOInput = document.createElement("input"); //input 태그 만듬
		articleNOInput.setAttribute("type","hidden"); //type= hidden
		articleNOInput.setAttribute("name","articleNO"); //name=articleNo
		articleNOInput.setAttribute("value", articleNO); //value = articleNO
		
		form.appendChild(articleNOInput); //form에 input 태그 추가
		document.body.appendChild(form); //form을 body에 추가
		form.submit();
	}
	
	function fn_reply_form(url, parentNO){
		var form = document.createElement("form"); 
		form.setAttribute("method","post"); 
		form.setAttribute("action", url); 
		var articleNOInput = document.createElement("input"); 
		articleNOInput.setAttribute("type","hidden"); 
		articleNOInput.setAttribute("name","parentNO"); 
		articleNOInput.setAttribute("value", parentNO); 
		
		form.appendChild(articleNOInput); //form에 input 태그 추가
		document.body.appendChild(form); //form을 body에 추가
		form.submit();
		
	}
</script>
<style>
#tr_btn_modify{
display: none;
}
</style>
</head>
<body>
	<form name="frmArticle" method="post" action="${contextPath}"enctype="multipart/form-data">
	<table border="0" align="center">
		<tr>
			<td width="20%" align="center" bgcolor="#FF9933">글번호</td>
			<td><input type="text" value="${article.articleNO }" disabled="disabled">
				<input type="hidden" name="articleNO" value="${article.articleNO }" />
			</td>
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
			
			</c:if>
			<tr>
				<td><input type="file" name="imageFileName" id = "i_imageFileName" onchange="readURL(this)" disabled ></td>
			</tr>
			<tr>
				<td width="20%" align="center" bgcolor="#FF9933">등록일자</td>
				<td><input type="text" value="${article.writeDate }" name="writeDate" disabled="disabled"></td>
			</tr>
			<tr id="tr_btn_modify"> <!-- 수정하기 버튼을 눌렀을때 표시되는 버튼 -->
				<td colspan="2" align="center"> 
					<input type="button" value="수정적용" onclick="fn_modify_article(frmArticle)">
					<input type="button" value="취소" onClick="backToList(frmArticle)">					
				</td>
			</tr>
			<tr id = "tr_btn">
			<td colspan=2 align="center">
					<input type="button" value="수정하기" onclick="fn_enable(this.form)"> <!-- 수정적용과 취소 버튼을 보이게 만들고 위 수정가능한 input부분을 disabled해재 -->
					<input type="button" value="삭제하기" onClick="fn_remove_article('${contextPath}/board6/removeArticle.do',${article.articleNO})"> 
					<input type="button" value="리스트로 돌아가기" onClick="backToList(this.form)" >
					<input type="button" value="답글쓰기" onClick="fn_reply_form('${contextPath}/board6/replyForm.do',${article.articleNO})">
				</td>
			</tr>
	</table>
	</form>
</body>
</html>