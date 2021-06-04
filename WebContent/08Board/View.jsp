<%@page import="model1.board.BoardDTO"%>
<%@page import="model1.board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
// 파라미터 받기
String num = request.getParameter("num");// 일련번호
String searchField = request.getParameter("searchField");// 검색필드
String searchWord = request.getParameter("searchWord"); // 검색어

String queryStr = "";
if(searchWord != null){
	// 검색 파라미터 추가하기
	queryStr = "searchField="+searchField+"&searchWord="+searchWord;
}

BoardDAO dao = new BoardDAO(application);


// 조회수 증가
dao.updateVisitCount(num);

// 파라미터로 전달된 일련번호를 조회
BoardDTO dto = dao.selectView(num);
dao.close();

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View.jsp</title>

<script type="text/javascript">
/*
	Javascript를 통한 폼값 전송으로 삭제 처리
*/
function isDelete() {
	/*
	confirm은 yes / no 로 값이 반환됨
	*/
	var c = confirm("정말로 삭제하시겠습니까?");
	if(c){
		var f = document.writeFrm;
		f.method = "post";
		f.action = "DeleteProcess.jsp";
		f.submit();
		
	}
}

</script>


</head>
<body>
	<h2>회원제 게시판 - 상세보기(View)</h2>
	<!-- 
		회원제 게시판에서 게시물 삭제를 위해 상세보기에
		게시물의 일련번호를 hidden 입력상자를 삽입한다.
	 -->
	<form name="writeFrm">
	<!-- 
		hidden 입력상자는 화면상에 보이지는 않지만 해당 값이 전송되어
		로그인 한 회원이 작성자가 맞는지 확인한다.
	 -->
	<input type="hidden" name="num" value="<%=num%>"/>
	<table border="1" width="90%">
		<tr>
			<td>번호</td>
			<td><%=dto.getNum() %></td>
			<td>작성자</td>
			<td><%= dto.getName() %></td>
		</tr>
		<tr>
			<td>작성일</td>
			<td><%= dto.getPostdate()%></td>
			<td>조회수</td>
			<td><%= dto.getVisitcount() %></td>
		</tr>
		<tr>
			<td>제목</td>
			<td colspan="3"><%=dto.getTitle() %></td>
		</tr>
		<tr>
			<td>내용</td>
			<td colspan="3" height="100">
				<%= dto.getContent().replace("\r\n","<br>") %></td>
				
			<!-- 
				<textarea> 에서 줄바꿈을 위해 Enter키를 누르면
				\r\n으로 저장된다. 이를 브라우저에 출력할때는
				<br>로 변경한 후 출력해야 줄바꿈 처리가 된다.
			 -->

		</tr>
		<tr>
			<td colspan="4" align="center">
			<% 
			/*
			로그인이 되었고, 동시에 해당 글의 작성자이면
			수정, 삭제 버튼을 보이게 처리한다.
			*/
			if(session.getAttribute("USER_ID") != null && 
				session.getAttribute("USER_ID").toString().equals(dto.getId())){
			%>
				<button type="button"
					onclick="location.href = 'Edit.jsp?num=<%=dto.getNum() %>&<%=queryStr%>';">
					수정하기
				</button>
				<button type="button" onclick="isDelete();">삭제하기</button>
			
			<% 
			}
			%>
				<button type="button" onclick="location.href='List.jsp?<%=queryStr%>';">
					리스트 바로가기
				</button>
			
			</td>
		
		</tr>
	
	</table>
	
	</form>

</body>
</html>