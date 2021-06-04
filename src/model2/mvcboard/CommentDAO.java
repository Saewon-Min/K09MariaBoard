package model2.mvcboard;

import java.util.List;
import java.util.Vector;

import common.ConnectionPool;

public class CommentDAO extends ConnectionPool{

	
	public CommentDAO() {
		super();
	}
	
	public int commentInsert(CommentDTO dto) {
		
		int result = 0;
		try {
			String query = " insert into mycomment ( "
					+ " board_idx, name, pass, comments ) "
					+ " values ( "
					+ " ?, ?, ?, ? ) ";
			
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getBoard_idx());
			psmt.setString(2, dto.getName());
			psmt.setString(3, dto.getPass());
			psmt.setString(4, dto.getComments());
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("댓글 입력중 예외발생");
			e.printStackTrace();
		
		}
		
		return result;
		
		
	}
	
	// mvcboard의 일련번호를 매개변수로 받아 해당 댓글을 조회하는 메소드
	public List<CommentDTO> commentSelectList(String board_idx){
		List<CommentDTO> comments = new Vector<CommentDTO>();
		
		// 댓글 작성일을 시:분까지 출력하기 위해 to_char()함수를 사용함
		String query = " select idx, board_idx, name, pass, comments, "
				+ " DATE_FORMAT(postdate,'%Y-%m-%d %H:%i:%s') "
				+ " from mycomment "
				+ " where board_idx=? "
				+ " order by idx desc " ;
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, board_idx);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				
				CommentDTO dto = new CommentDTO();
				
				dto.setIdx(rs.getString(1));
				dto.setBoard_idx(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setPass(rs.getString(4));
				dto.setComments(rs.getString(5).replaceAll("\r\n", "<br/>"));
				//dto.setCommentsEdit(rs.getString(5));
				dto.setPostdate(rs.getString(6));
				
				comments.add(dto);
				
			}
			
			
		} catch (Exception e) {
			System.out.println("댓글 조회중 예외발생");
			e.printStackTrace();
		
		}
		
		return comments;
	}
	
	
	
	// 댓글의 일련번호를 매개변수로 받아 해당 댓글을 조회하는 메소드
	public CommentDTO commentView(String idx, String board_idx){
		CommentDTO dto = new CommentDTO();
		
		
		String query = " select idx, board_idx, name, pass, comments, "
				+ " DATE_FORMAT(postdate,'%Y-%m-%d %H:%i:%s') "
				+ " from mycomment "
				+ " where idx=? and board_idx=?" ;
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);
			psmt.setString(2, board_idx);
			rs = psmt.executeQuery();
			
			while(rs.next()) {

				dto.setIdx(rs.getString(1));
				dto.setBoard_idx(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setPass(rs.getString(4));
				dto.setComments(rs.getString(5).replaceAll("\r\n", "<br/>"));
				dto.setPostdate(rs.getString(6));

				
			}
			
			
		} catch (Exception e) {
			System.out.println("댓글 수정 페이지 보기 중 예외발생");
			e.printStackTrace();
		
		}
		
		return dto;
	}
	
	
	
	
	// 수정 처리
	public int commentUpdate(CommentDTO dto) {

		
		int result = 0;
		try {
			String query = " update mycomment set "
					+ " comments=? "
					+ " where idx=? and board_idx=? and pass=? ";
			
			psmt = con.prepareStatement(query);
			//psmt.setString(1, dto.getName());
			//psmt.setString(2, dto.getPostdate());
			psmt.setString(1, dto.getComments());
			psmt.setString(2, dto.getIdx());
			psmt.setString(3, dto.getBoard_idx());
			psmt.setString(4, dto.getPass());

			result = psmt.executeUpdate();
	
			
		} catch (Exception e) {
			System.out.println("댓글 수정 중 예외발생");
			e.printStackTrace();
		}
		
		
		return result;
		
	}
	
	
	public int deleteComment(String idx, String board_idx, String pass) {
		
		int result = 0;
		try {
			String query= " delete from mycomment where idx=? and board_idx=? and pass=?";
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);
			psmt.setString(2, board_idx);
			psmt.setString(3, pass);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시물 삭제 중 예외발생");
			e.printStackTrace();
		}
		
		return result;
		
		
		
		
	}
	
	
	
	public boolean confirmPassword(String pass, String idx) {
		
		boolean isCorr = true;
		try {
			// 일련번호와 패스워드가 일치하는 게시물이 있는지 확인
			String sql = " select count(*) from mycomment where pass=? and idx=? ";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, pass);
			psmt.setString(2, idx);
			rs = psmt.executeQuery();
			rs.next();
			if(rs.getInt(1)==0) {
				// 패스워드가 일치하는 게시물이 없으므로  false
				isCorr = false;
			}
			
			
		} catch (Exception e) {
			// 예외가 발생하여 확인이 불가하므로 false
			isCorr = false;
			e.printStackTrace();
		}
		
		return isCorr;
		
		
		
	}
	
	
	
	
}
