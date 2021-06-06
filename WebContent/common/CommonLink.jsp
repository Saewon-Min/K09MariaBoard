<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CommonLink.jsp</title>
</head>
<body>
	<h2>공통링크</h2>
	<table border="1" width="90%">
		<tr>
			<td>
				<!-- 로그인 여부를 판단하기 위해 session영역의 속성을 사용한다.
					session영역에 아무런 속성도 없다면 로그아웃 상태이다. -->
				<!-- session 영역에 user_id 속성값을 얻어옴  -->
				<% if(session.getAttribute("USER_ID")==null) { %>
					<a href="../06Session/Login.jsp">로그인</a>
				<% } else { %>
					<a href="../06Session/Logout.jsp">로그아웃</a>
				<% } %>
				&nbsp;&nbsp;&nbsp;
				<a href="../08Board/ListSimple.jsp">회원제게시판1[PageX]</a>
				
				&nbsp;&nbsp;&nbsp;
				<a href="../08Board/List.jsp">회원제게시판2[PageO]</a>
				
				&nbsp;&nbsp;&nbsp;
				<a href="../board_skin/BoardList.jsp">board_skin</a>
				
				&nbsp;&nbsp;&nbsp;
				<a href="../14MVCBoard/Default.jsp">MVCBoard</a>
				
				&nbsp;&nbsp;&nbsp;
				<a href="../DataRoom/DataList">자료실(Model2)</a>
				
			
			</td>
		</tr>
	
	</table>
	
</body>
</html>