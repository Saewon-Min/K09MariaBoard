<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- JSTL 사용을 위한 taglib 지시어 추가 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style >a{text-decoration: none;}</style>
</head>
<body>
	<h2>목록 보기(List)</h2>
	<%@ include file="../common/CommonLink.jsp" %>
	<!-- 검색폼 : 제목과 내용을 통해 검색할 수 있다. -->
	<form method="get">
	<table border="1" width="90%">
	<tr>
		<td align='center'>
			<select name="searchField">
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
			<input type="text" name="searchWord" />
			<input type="submit" value="검색하기" />
		</td>
	</tr>
	
	</table>
	</form>
	
	<!-- 목록 출력을 위한 테이블 -->
	<table border="1" width="90%">
	<tr>
		<th width="10%">번호</th>
		<th width="50%">제목</th>
		<th width="15%">작성자</th>
		<th width="10%">조회수</th>
		<th width="15%">작성일</th>
	</tr>

<!-- 게시물 출력 부분을 JSTL로 변경함 -->
<c:choose>
	<c:when test="${empty boardLists }">
		<tr>
			<td colspan="5" align="center">
				등록된 게시물이 없습니다.
			</td>
		
		</tr>
	</c:when>
	<c:otherwise>
		<!-- 게시물이 있는 경우 확장 for문 형태의 forEach태그 사용 -->
		<c:forEach items="${boardLists }" var="row" varStatus="loop">

		<tr align="center">
			<td>
				${map.totalCount - (((map.pageNum-1) * map.pageSize)
					+ loop.index) }
			</td>
			<td align="left">
				<a href="../mvcboard/view.do?idx=${row.idx }">${row.title }</a>
			</td>
			<td>${row.name }</td>
			<td>${row.visitcount }</td>
			<td>${row.postdate }</td>
			<td>
			<!-- 첨부된 파일이 있는경우에만 다운로드 링크 출력됨 -->
			<c:if test="${not empty row.ofile }">
				<!-- 파일 다운로드 시 다운로드 횟수를 증가시켜야 하므로
					게시물의 일련번호가 필요함 -->
				<a href="../mvcboard/download.do?ofile=${row.ofile 
					}&sfile=${row.sfile }&idx=${row.idx }">[Down]</a>
			</c:if>
			</td>
		</tr>
		</c:forEach>
	</c:otherwise>
</c:choose>
	</table>
	<table border="1" width="90%">

		<tr align="center">
			<td>
				<!-- 컨트롤러에서 map에 저장한 페이지 번호 문자열 출력 -->
				${map.pagingImg }
			</td>
			<td width="100">
				<button type="button" onclick="location.href='../mvcboard/write.do';">
				글쓰기
				</button>
			</td>
		</tr>
	</table>
	
</body>
</html>
