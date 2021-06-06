<%@page import="model1.board.BoardDTO"%>
<%@page import="model1.board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
String num = request.getParameter("num");
BoardDAO dao = new BoardDAO(application);
dao.updateVisitCount(num);

BoardDTO dto = dao.selectView(num);
dao.close();



%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function isDelete() {
		var c = confirm("정말로 삭제하시겠습니까?");
		if(c){
			var f = document.writeFrm;
			f.method= "post";
			f.action = "DeleteProcess.jsp";
			f.submit();
			
			
			
		}
		
		
	}


</script>


</head>
<body>

	<form action="" name="writeFrm">
	<input type="hidden" name="num" value="<%=num%>"/>
	<tr>
		<td>
		<%
		if(session.getAttribute("USER_ID") != null &&
			session.getAttribute("USER_ID").toString().equals(dto.getId())){
			
		%>
			<button type="button"
				onclick="location.href = 'Edit.jsp?num=<%=dto.getNum()%>';">
				수정하기
			</button>
			
			<button type="button" onclick="isDelete();">
			삭제하기
			</button>
		
		
		
		<%
		}
		%>
		
			<button type="button" onclick="location.href='List.jsp';">
			리스트 바로가기
			</button>
		
		
		</td>
	
	
	
	
	
	
	
	</tr>
	
	
	
	</form>



</body>
</html>