package model2.mvcboard;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mvcboard/view.do")
public class ViewController extends HttpServlet{

	/*
	서블릿의 수명주기 메소드 중 사용자의 요청을 받아서 get / post를
	판단하여 분기하는 역할을 한다. 따라서 두가지 요청 모두를 받을 수 있다.
	 */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idx = req.getParameter("idx");
		MVCBoardDAO dao = new MVCBoardDAO();
		dao.updateVisitCount(idx); // 조회수 증가
		MVCBoardDTO dto = dao.selectView(idx); // 게시물 조회
		dao.close();
		
		// 내용에 대해서는 줄바꿈 처리를 한다. 엔터키를 <br>로 변경한다.
		dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
		// 리퀘스트 영역에 저장
		req.setAttribute("dto", dto);
		
		
		// 댓글 기능(목록) 추가
		CommentDAO dao2 = new CommentDAO();
		
		// 현재 조회하는 게시물의 일련번호를 통해 댓글 목록을 가져온다.
		List<CommentDTO> comments = dao2.commentSelectList(idx);
		
		req.setAttribute("comments", comments);
		dao2.close();
		
		req.getRequestDispatcher("/14MVCBoard/View.jsp").forward(req, resp);
		
		
	}
	
	
	
	
}
