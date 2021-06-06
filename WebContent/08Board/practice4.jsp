<%@page import="utils.JSFunction"%>
<%@page import="model1.board.BoardDTO"%>
<%@page import="model1.board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
String num = request.getParameter("num");
BoardDAO dao = new BoardDAO(application);
BoardDTO dto = dao.selectView(num);

String session_id = session.getAttribute("USER_ID").toString();
if(!session_id.equals(dto.getId())){
	JSFunction.alertBack("작성자 본인만 수정할수 있습니다.", out);
	return;
	
}

dao.close();


%>    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>