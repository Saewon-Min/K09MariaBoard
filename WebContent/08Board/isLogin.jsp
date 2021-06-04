<%@page import="utils.JSFunction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
/*
session영역에 회원 인증 정보가 저장되어있는지 확인한다.
만약 로그인에 성공했다면 USER_ID라는 속성값이 저장되어 있을것이다.
*/
if(session.getAttribute("USER_ID")==null){
	
	// 로그인 정보가 없다면 로그인 페이지로 이동한다.
	JSFunction.alertLocation("로그인 후 사용해주세요",
			"../06Session/Login.jsp", out);
	
	/*
	Javascript와 JSP가 같이 있으면 JSP코드가 우선적으로 실행되므로
	반드시 return을 통해 실행을 중지해야 한다.
	*/
	return;
}


%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인페이지</title>
</head>
<body>

</body>
</html>