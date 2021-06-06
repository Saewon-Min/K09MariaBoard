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
		if(f.pass.value==""){
			alert("비밀번호를 입력하세요");
			f.pass.focus();
			return false;
		}
	}
</script>
</head>
<body>	

	<h2>파일 첨부형 게시판 - 비밀번호 검증(Pass)</h2>
	
	<!--  
		파일 첨부가 없는 페이지에서는 enctype을 제거하는 것이 좋다.
		만약 enctype 속성이 있다면 request 객체를 통해 폼값을 받을 수 없다.
	-->
	<form name="writeFrm" method="post" action="../mvcboard/pass.do"
		onsubmit="return formValidate(this);">
		
	<!--  
		idx : 게시물의 일련번호
		mode : 게시물의 수정 혹은 삭제를 위한 구분값
		※ hidden 입력 상자는 화면상에서 보이지 않기때문에 값이 입력됐는지
			확인하기 위해 소스보기 혹은 개발자 모드를 사용해야 한다.
			특히 EL을 사용하는 경우에는 에러가 발생하지 않으므로
			값이 있는지 반드시 확인해야 한다.
	-->
	<input type="hidden" name="idx" value=${param.idx } />	
	<input type="hidden" name="mode" value=${mode } />	
	<table border="1" width="90%">
		<tr>
			<td>비밀번호</td>
			<td>
				<input type="password" name="pass" style="width:100px;" />
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<button type="submit">검증하기</button>
				<button type="reset">RESET</button>
				<button type="button" onclick="location.href='../mvcboard/list.do';">
					리스트 바로가기</button>
			</td>


		</tr>
	
	
	</table>
		
		
	</form>
	
</body>
</html>
	