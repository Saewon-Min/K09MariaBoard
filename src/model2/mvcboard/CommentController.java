package model2.mvcboard;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fileupload.FileUtil;
import utils.JSFunction;

/*
한번의 매핑으로 여러 요청을 받을 수 있도록 
매핑명에 와일드 카드를 사용했음
 */
@WebServlet("*.comm")
public class CommentController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	// 전송방식에 상관없이 요청을 받아서 분기하는 메소드
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		// 요청명을 분석한다.
		String uri = req.getRequestURI();
		int lastSlash = uri.lastIndexOf("/");
		String commandStr = uri.substring(lastSlash);
		
		// 마지막 요청명을 통해 각 기능의 메소드를 호출한다.
		if(commandStr.equals("/commentWrite.comm")) {
			commentWrite(req, resp);
		}else if(commandStr.equals("/commentEdit.comm")) {
			commentEdit(req, resp);
		}else if(commandStr.equals("/commentEditAction.comm")) {
			commentEditAction(req, resp);
		}else if(commandStr.equals("/commentDelete.comm")) {
			commentDelete(req, resp);
		}else if(commandStr.equals("/commentDeleteAction.comm")) {
			commentDeleteAction(req, resp);
		}
		
		
		
		
	}
	
	
	// 댓글쓰기
	private void commentWrite(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		/*
		서블릿에서 기능 처리를 위한 메소드는 doGet(), doPost()와 동일하게
		request, response 객체를 매개변수로 사용하고,
		ServletException, IOException 에 대한 예외처리를 해주는게 좋다.
		 */
		System.out.println("댓글쓰기");
		
		// 폼 값 받기
		String board_idx = req.getParameter("board_idx");
		String name = req.getParameter("name");
		String pass = req.getParameter("pass");
		String comments = req.getParameter("comments");
		
		// DTO 객체에 저장
		CommentDTO dto = new CommentDTO();
		dto.setBoard_idx(board_idx);
		dto.setName(name);
		dto.setPass(pass);
		dto.setComments(comments);
		
		// DAO 객체 생성 및 메소드 호출
		CommentDAO dao = new CommentDAO();
		
		int result = dao.commentInsert(dto);
		if(result == 1) {
			/*
			댓글 작성 완료 후 내용 보기 페이지로 리다이렉트 할때
			댓글 목록 부분을 로드하기 위해 URL뒤에 앵커를 추가한다.
			 */
			resp.sendRedirect("./view.do?idx="+board_idx+"#commentsList");
		}else {
			JSFunction.alertBack(resp, "댓글 작성에 실패했습니다.");
		}
		
		
	}
	
	
	
	private void commentEdit(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{

		String idx = req.getParameter("idx");
		String board_idx = req.getParameter("board_idx");


		CommentDAO dao = new CommentDAO();
		CommentDTO dto = dao.commentView(idx,board_idx);

		// 하나의 레코드를 저장한 DTO객체를 request영역에 저장한 후 View로 포워드
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("/14MVCBoard/EditComment.jsp").forward(req, resp);
		
			
	}
		
		
	private void commentEditAction(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		
		String idx = req.getParameter("idx");
		String board_idx = req.getParameter("board_idx");
		String pass= req.getParameter("pass");
		String name = req.getParameter("name");
		String postdate = req.getParameter("postdate");
		String comments = req.getParameter("comments");
		

		
		
		CommentDTO dto = new CommentDTO();		
		dto.setComments(comments);
		dto.setBoard_idx(board_idx);
		dto.setIdx(idx);
		dto.setPass(pass);

		
		CommentDAO dao = new CommentDAO();
		boolean confirmPass = dao.confirmPassword(pass, idx);
		//dao.close();
		if(confirmPass == true) {
			
			int result = dao.commentUpdate(dto);
			dao.close();
			
			if(result==1) {
				JSFunction.alertOpenerReloadClose(resp,"댓글이 수정되었습니다.");	
				
			}else {
				JSFunction.alertBack(resp, "댓글 수정 중 오류가 발생했습니다.");
				
			}
			
		}else {
			JSFunction.alertBack(resp, "비밀번호 검증에 실패했습니다.");
		}

	}
	
		
	private void commentDelete(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		String idx = req.getParameter("idx");
		String board_idx = req.getParameter("board_idx");
		CommentDAO dao = new CommentDAO();
		CommentDTO dto = dao.commentView(idx,board_idx);
		
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("/14MVCBoard/DeleteComment.jsp").forward(req, resp);
		
	}
	
	private void commentDeleteAction(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		
		String idx = req.getParameter("idx");
		String board_idx = req.getParameter("board_idx");

		String pass = req.getParameter("pass");
	

		CommentDAO dao =  new CommentDAO();
		
		boolean confirmPass = dao.confirmPassword(pass, idx);
		if(confirmPass == true) {
			
			CommentDTO dto = dao.commentView(idx,board_idx);
			
			int result = dao.deleteComment(idx,board_idx,pass);
			dao.close();
			
			
			if(result==1) {
				JSFunction.alertOpenerReloadClose(resp,"댓글이 삭제되었습니다.");			
			}	
			else {
				JSFunction.alertBack(resp, "댓글 삭제를 실패했습니다.");
			}
		}else {
			JSFunction.alertBack(resp, "비밀번호 검증에 실패했습니다.");
		}
		
		
		
		
		
		
	}


	 
}
