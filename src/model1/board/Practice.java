package model1.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;

public class Practice {

	Connection con;
	Statement stmt;
	PreparedStatement psmt;
	ResultSet rs;
	
	
	public Practice(ServletContext application) {
		
		try {
			String drv = application.getInitParameter("JDBCDriver");
			String url = application.getInitParameter("ConnectionURL");
			String id = application.getInitParameter("OracleId");
			String pwd = application.getInitParameter("OraclePwd");
			
			Class.forName(drv);
			
			con = DriverManager.getConnection(url,id,pwd);
			
			System.out.println("JDBC 연결 성공");
			
		} catch (Exception e) {
			System.out.println("JDBC 연결시 예외발생");
			e.printStackTrace();
		}
		
		
		
	}
		
	
	public void close() {
		try {
			if(rs!=null) rs.close();
			if(psmt!=null) psmt.close();
			if(con!=null) con.close();
			
			
			
		} catch (Exception e) {
			System.out.println("Oracle 자원반납시 예외발생");
		}
		
		
		
	}
	
	
	// 검색된 게시물의 개수를 반환하는 메소드
	public int selectCount(Map<String, Object> map ) {
		int totalCount = 0;
		
		String query = " Select count(*) from board ";
		// 검색 파라미터에 값이 있다면
		if(map.get("searchWord") != null) {
			// 제목, 내용 중 하나에서
			query += " where " + map.get("searchFiled") + " "
					// 검색된 단어의 개수를 출력
					+ " like '%"+map.get("searchWord")+"%'";
			
		}
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			// count(*)의 개수를 반환
			totalCount = rs.getInt(1);
			
		} catch (Exception e) {
			System.out.println("게시물 카운트 중 예외발생");
			e.printStackTrace();
		}
		
		return totalCount;
		
	}
	 
	
	// DB의 목록을 반환해주는 메소드
	public List<BoardDTO> selectList(Map<String, Object> map){
		// BoardDTO 타입의 list를 만들고
		List<BoardDTO> bbs = new Vector<BoardDTO>();
		String query = " select * from board ";
		if(map.get("searchWord")!=null) {
			query += " where " + map.get("searchField") + " " 
					+ "like '% "+map.get("searchWord")+" %'";
			
		}
		// 검색 단어 여부에 상관없이 num을 기준으로 내림차순 정렬
		query += " order by num desc ";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			//query의 결과가 있다면
			while(rs.next()) {
				// dto를 set으로 값 변경
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("postdate"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				
				// DTO 타입의 list에 변경된 값을 저장
				bbs.add(dto);
				
			}
			
			
		} catch (Exception e) {
			System.out.println("게시물 조회중 예외발생");
			e.printStackTrace();
		}
		
		return bbs;
	}
	
	
	
	public List<BoardDTO> selectListPage(Map<String, Object> map){
		List<BoardDTO> bbs = new Vector<BoardDTO>();
		
		String query = " "
				+ " select * "
				+ " from ( select Tb.*. rownum rnum "
				+ " 		from ( select * from board  ";		
				
		
		if(map.get("searchWord")!= null) {
			query += " where " + map.get("searchField")+ " "
					+ " like '%"+map.get("searchWord") +"%' ";
			
			
		}
		query += " "
				+ " order by num desc "
				+ "  ) Tb  "
				+ " ) "
				+ " where rNum between ? and ? ";
		System.out.println(query);
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString());
			rs = psmt.executeQuery();
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				
				
				
				bbs.add(dto);
			}
			
			
			
		} catch (Exception e) {
			System.out.println("게시물 조회중 예외발생");
			e.printStackTrace();
		}
		
		return bbs;
		
		
	}
	
	
	
	
	
	
	public int insertWrite(BoardDTO dto) {
		int result = 0;
		
		try {
			String query = " insert into board ( "
					+ " num, title, content, id, visitcount) "
					+ " values ( "
					+ " seq_board_num.nextval, ?,?,?,0) " ;
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getId());
			
			result = psmt.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("게시물 입력 중 예외발생");
			e.printStackTrace();
		
		}
		
		
		return result;
		
		
	}
	
	
	public BoardDTO selectView(String num) {
		BoardDTO dto = new BoardDTO();
		
		String query = " select b.*, m.name " 
				+ " from member m inner join board b "
				+ " on m.id = b.id "
				+  " where num=?" ; 
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				dto.setNum(rs.getString(1));
				dto.setTitle(rs.getString(2));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				dto.setName(rs.getString("name"));
			}
			
		} catch (Exception e) {

			System.out.println("게시물 상세보기 중 예외발생");
			e.printStackTrace();
		}
		
		return dto;
		
	}
	
	
	public void updateViewCount(String num) {
		
		String query = " update board set "
				+ " visitcount=visitcount+1 "
				+ " where num=? ";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num);
			psmt.executeQuery();
			
			
		} catch (Exception e) {
			System.out.println("게시물 조회수 증가 중 예외 발생");
			e.printStackTrace();
		}
		
	}
	
	
	public int updateEdit(BoardDTO dto) {
		
		int result = 0;
		try {
			String query = " update board set "
					+ " title=?, content=? "
					+ " where num=? ";
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getNum());
			
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("게시물 수정 중 예외 발생");
			e.printStackTrace();
		}
		
		return result;
		
		
		
	}
	
	
	
	public int deletePost(BoardDTO dto) {
		int result = 0;
		
		try {
			String query = " delete from board where num=? ";
			psmt =con.prepareStatement(query);
			psmt.setString(1, dto.getNum());
			result = psmt.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("게시물 삭제 중 예외발생");
			e.printStackTrace();
		}
		
		return result;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}






