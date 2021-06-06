<%@page import="utils.BoardPage"%>
<%@page import="common.BoardConfig"%>
<%@page import="model1.board.BoardDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="model1.board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<% 
// DB연결을 위한 객체 생성
BoardDAO dao= new BoardDAO(application);

// 파라미터를 받아올 map컬렉션 생성
Map<String, Object> param = new HashMap<String, Object>();

// request영역에서 파라미터를 받아옴
String searchField = request.getParameter("searchField");
String searchWord = request.getParameter("searchWord");

// 검색 단어가 있다면
if(searchWord != null){
	// map컬렉션에 저장
	param.put("searchField", searchField);
	param.put("searchWord", searchWord);
	
}

// 게시물의 개수를 반환하는 dao의 selectCount메소드에 map객체를 매개변수로 전달
int totalCount = dao.selectCount(param);

/**** 페이지 처리 시작 ****/
int pageSize = BoardConfig.PAGE_PER_SIZE;
int blockPage = BoardConfig.PAGE_PER_BLOCK;

int totalPage = (int)Math.ceil((double)totalCount/pageSize);
int pageNum = 1;

String pageTemp = request.getParameter("pageNum");
if(pageTemp != null && !pageTemp.equals("")){
	pageNum = Integer.parseInt(pageTemp);
}

int start = (pageNum -1) * pageSize +1;
int end = pageNum*pageSize;

param.put("start", start);
param.put("end", end);

System.out.println(start +" = " + end);



/**** 페이지 처리 끝 ****/


// boardDTO 타입의 list 생성
// dao의 selectList객체로부터 DTO 타입의 list를 반환받는다.
List<BoardDTO> boardLists = dao.selectListPage(param);

// 자원반납
dao.close();

%>    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
	// 목록이 비어있다면
	if(boardLists.isEmpty()){
	%>
		<!-- 게시물이 없다고 출력 -->
	<%
	// 목록이 있다면
	}else{
		// 가상번호를 생성
		int vNum =  0;
		int countNum =0;
		for(BoardDTO dto : boardLists){
			
			// vNum에 전체 개수부터 하나씩 줄어든 숫자를 반환
			/* 
			내림차순으로 정렬된 데이터를 반환하기 때문에
			9 8 7 6 5 이런식으로 가상의 번호가 붙을것
			제일 마지막 게시물이 최초의 게시물이므로
			1번이라는 숫자가 붙음
			*/
			//vNum = totalCount--;
			vNum = totalCount - (((pageNum-1)*pageSize)+countNum++);
	%>		
	
	<%		
		}
	}
	%>



	<%= BoardPage.pagingStr(totalCount, pageSize, blockPage,
			pageNum, request.getRequestURI()) %>








</body>
</html>