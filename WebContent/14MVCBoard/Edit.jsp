<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일첨부형 게시판</title>
<script type="text/javascript">
	function formValidate(f){
		if(f.name.value==""){
			alert("작성자를 입력하세요");
			f.name.focus();
			return false;
		}
		if(f.title.value==""){
			alert("제목을 입력하세요");
			f.title.focus();
			return false;
		}
		if(f.content.value==""){
			alert("내용을 입력하세요");
			f.content.focus();
			return false;
		}
	}
</script>
</head>
<body>

<h2>파일 첨부형 게시판 - 수정하기(Edit)</h2>
<!--  
	수정 페이지에서도 파일을 첨부할 수 있으므로 enctype이 있어야한다.
	만약 파일을 첨부하지 않은 상태로 수정을 하게되면, 기존의 파일명을
	그대로 유지해야 하므로 hidden 입력상자를 통해 기존 파일명을
	전송한다.
-->
<form name="writeFrm" method="post" enctype="multipart/form-data"
	action="../mvcboard/edit.do"
	onsubmit="return formValidate(this);">

	<input type="hid den" name="idx" value=${dto.idx } /><!-- 일련번호 -->
	<input type="hid den" name="prevOfile" value=${dto.ofile } /><!-- 원본 파일명 -->	
	<input type="hid den" name="prevSfile" value=${dto.sfile } /><!-- 저장된 파일명 -->	

<table border="1" width="90%">
	<tr>
		<td>작성자</td>
		<td>
			<input type="text" name="name" style="width:150px;" value="${dto.name }" />
		</td>
	</tr>
	<tr>
		<td>제목</td>
		<td>
			<input type="text" name="title" style="width:90%;" value="${dto.title }" />
		</td>
	</tr>
	<tr>
		<td>내용</td>
		<td>
			<textarea name="content" style="width:90%;height:100px;">${dto.content }</textarea>
		</td>
	</tr>
	<tr>
		<td>첨부파일</td>
		<td>
		<!-- 만약 첨부된 파일이 있다면 이미지를 나타낸다 -->
		<c:if test="${not empty dto.ofile }">		
			<img src="../Uploads/${dto.sfile }" style="width:100px" />
			<br />
		</c:if>
			<input type="file" name="ofile" /> 
		</td>
	</tr>	
	<tr>
		<td colspan="2" align="center">
			<button type="submit">작성완료</button>
			<button type="reset">RESET</button>
			<button type="button" onclick="location.href='../mvcboard/list.do';">
				리스트바로가기
			</button>
		</td>
	</tr>
</table>	
</form>
</body>
</html>

