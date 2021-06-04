package model2.mvcboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fileupload.FileUtil;
import utils.JSFunction;

@WebServlet("/mvcboard/pass.do")
public class PassController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 파라미터를 request 내장객체로 받은 후 request 영역에 저장
		req.setAttribute("mode", req.getParameter("mode"));
		req.getRequestDispatcher("/14MVCBoard/Pass.jsp").forward(req, resp);
	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String idx = req.getParameter("idx");
		String mode = req.getParameter("mode");
		String pass = req.getParameter("pass");
	
		MVCBoardDAO dao =  new MVCBoardDAO();
		/*
		게시물의 일련번호와 비밀번호를 통해 해당 게시물이 있는지
		확인하기 위한 메소드 호출
		 */
		boolean confirmPass = dao.confirmPassword(pass, idx);
		dao.close();
		   
		// 비밀번호 검증이 완료되었다면 수정 혹은 삭제로 분기한다.
		if(confirmPass == true) {
			// 수정
			if(mode.equals("edit")) {
				/*
				서블릿에서 session 내장객체를 아래와 같이 얻을 수 있다.
				검증에 성공한 비밀번호를 session영역에 저장한 후
				수정 폼으로 이동한다. 페이지 이동시 request영역은
				공유되지 않으므로 여기서는 session영역을 사용한다.
				 */
				HttpSession session = req.getSession();
				session.setAttribute("pass", pass);
				
				// 수정페이지로 이동한다.
				resp.sendRedirect("../mvcboard/edit.do?idx="+idx);
			
			// 삭제
			}else if(mode.equals("delete")) {
				dao = new MVCBoardDAO();
				
				// 첨부된 파일이 있다면 삭제하기 위해 기존 게시물을 가져온다.
				MVCBoardDTO dto = dao.selectView(idx);
				// 기존 게시물을 삭제한다.
				int result = dao.deletePost(idx);
				dao.close();
				
				// 게시물이 삭제되었다면 첨부된 파일도 삭제한다.
				if(result==1) {
					String saveFileName = dto.getSfile();
					FileUtil.deleteFile(req,"/Uploads",saveFileName);
				}
				JSFunction.alertLocation(resp, "삭제되었습니다.", "../mvcboard/list.do");
				
			}
			
		}else {
			JSFunction.alertBack(resp, "비밀번호 검증에 실패했습니다.");
		}
		
		
	
	}
	
	
	
}
