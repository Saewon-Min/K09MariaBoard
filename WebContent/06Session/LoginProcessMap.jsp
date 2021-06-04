<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="model.MemberDTO"%>
<%@page import="model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<% 
// 폼값으로 전송된 파라미터를 받아옴
String id = request.getParameter("user_id");
String pw = request.getParameter("user_pw");


// DAO객체 생성 및 DB연결
MemberDAO dao = new MemberDAO();

// 폼 값으로 받은 아이디, 패스워드를 통해 로그인 처리 메소드 호출
Map<String, String> map= dao.getMemberMap(id, pw);

/*
	연습문제]
		회원정보를 MemberDTO에 저장한 후 반환했던 부분을
		Map컬렉션에 저장한 후 반환하여 처리할 수 있도록 수정하시오.
*/

if(map.get("id") != null){
	// 로그인에 성공한 경우 session영역에 회원 인증 정보를 저장한다.
	
	session.setAttribute("USER_ID", map.get("id"));
	session.setAttribute("USER_PW", map.get("pass"));
	session.setAttribute("USER_NAME", map.get("name"));
			
	
	// 로그인 페이지로 이동
	response.sendRedirect("Login.jsp");
	
}else{
	// 로그인 실패시 request 영역에 속성을 저장한다.
	request.setAttribute("ERROR_MSG", "등록된 회원이 아닙니다.");
	// 로그인 페이지로 포워드(전달)한다.
	request.getRequestDispatcher("Login.jsp").forward(request, response);
}

%>    
    
    
    
    