<%@page import="model1.board.BoardDAO"%>
<%@page import="model1.board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./isLogin.jsp" %>    
<%
// 폼값 받기
String num = request.getParameter("num");
BoardDTO dto = new BoardDTO();
BoardDAO dao = new BoardDAO(application);
// 기존 게시물 조회
dto = dao.selectView(num);
// session영역에 저장된 회원 인증정보 얻어옴
String session_id = session.getAttribute("USER_ID").toString();
int delResult = 0;
// 작성자 본인 확인
// 작성자 본인 이면
if(session_id.equals(dto.getId())){
	dto.setNum(num); // DTO객체에 일련번호 저장
	delResult = dao.deletePost(dto); // 삭제 메소드 호출
	dao.close();
	
	if(delResult==1){
		JSFunction.alertLocation("삭제되었습니다.", "ListSimple.jsp", out);
	}else{
		JSFunction.alertBack("삭제에 실패하였습니다.", out);
	}
}else{
	/* 
	url에서 정보 변경이 가능하기 때문에 전송된 값과 실제 값을 비교해서
	로그인한 본인만 삭제가 가능하도록 한다.
	*/
	JSFunction.alertBack("본인만 삭제 가능합니다.", out);
	return;
}


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