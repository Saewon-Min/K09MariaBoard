package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;


/*
DAO(Data Access Object)
	: 데이터베이스의  Data에 접근하기 위한 객체로
	DB접근을 위한 로직으로 주로 구성된다.
	MVC 패턴에서는 M(Model)에 해당한다.
*/
public class MemberDAO {
	
	// 멤버변수
	Connection con; // Connection 객체를 멤버변수로 선언하여 DAO내에서 공유
	PreparedStatement psmt; 
	ResultSet rs;
	
	
	// 기본 생성자를 통한 오라클 연결
	public MemberDAO() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://127.0.0.1:3307/kosmo_db";
			String id ="kosmo_user";  // 계정이름
			String pass = "1234";  // 계정비번
			
			con = DriverManager.getConnection(url,id,pass);
			
			System.out.println("MariaDB 연결성공");
			
		}catch (Exception e){
			System.out.println("MariaDB 연결시 예외발생");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new MemberDAO();
	}
	
	/*
		JSP에서 컨텍스트 초기화 파라미터를 읽어서 매개변수로
		전달하여 DB연결을 하기 위한 인자 생성자
	 */
	// 인자 생성자
	public MemberDAO(String driver, String url){
		try {
			Class.forName(driver);

			String id ="kosmo_user";  // 계정이름
			String pass = "1234";  // 계정비번
			
			con = DriverManager.getConnection(url,id,pass);
			
			System.out.println("MariaDB 연결성공");
			
		}catch (Exception e){
			System.out.println("MariaDB 연결시 예외발생");
			e.printStackTrace();
		}
		
	}
	
	
	// 그룹함수 count()를 통해 회원의 존재 유무만 판단하는 메소드
	public boolean isMember(String id, String pass) {
		
		// 쿼리문 작성
		String sql = "SELECT COUNT(*) FROM member "
				+ " WHERE id=? AND pass=? ";
		int isMember = 0;
		boolean isFlag = false;
		
		try {
			// 쿼리문을 인수로 prepare객체를 생성한다.
			psmt = con.prepareStatement(sql);
			// 쿼리문의 인파라미터를 설정한다. DB의 인덱스는 1부터 시작
			psmt.setString(1,id);
			psmt.setString(2,pass);
			// select 쿼리문의 실행 결과는 ResultSet 객체를 통해 반환받는다.
			rs = psmt.executeQuery();
			// 실행결과를 얻어오기 위해 next()를 호출하여 커서를 이동시킨다.
			rs.next();
			// 실행 결과 중 첫번째 값을 얻어오기 위해 getInt()를 사용한다.
			// 0 아니면 1의 값을 읽어오기 때문에 getInt()함수로 읽어온다.
			isMember = rs.getInt(1);
			
			/*
			DB에서 count(*) 쿼리를 쓴다면 결과값은 1이거나 0이다.
			데이터가 있으면 1 없으면 0을 반환한다.
			 */
			System.out.println("affected : "+isMember);
			if(isMember==0) // 회원이 아닌 경우(아이디, 패스워드로 회원을 찾을수 없음)
				isFlag = false;
			else
				isFlag =true; // 회원인 경우(아이디, 패스워드 일치)
			
			
		}catch(Exception e) {
			// 예외가 발생한다면 확인이 불가하므로 무조건 false를 반환한다.
			isFlag = false;
			e.printStackTrace();
		}
		return isFlag;
		
		
	}
	
	/*
		로그인 방법2
			: 쿼리문을 통해 회원인증 후 MemberDTO객체에 회원 정보를
			저장한 후 JSP 쪽으로 반환해준다.
	 */
	public MemberDTO getMemberDTO(String uid, String upass) {
		
		// 회원정보 저장을 위해 DTO객체 생성
		MemberDTO dto = new MemberDTO();
		
		// 회원정보 조회를 위한 쿼리문 작성
		String query = "SELECT id, pass, name FROM "+
				" member WHERE id=? AND pass=? ";
		try {
			// prepare객체 생성
			psmt = con.prepareStatement(query);
			// 인파라미터 설정
			psmt.setString(1, uid);
			psmt.setString(2, upass);
			// 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 오라클이 반환해준 ResultSet객체를 통해 결과값이 있는지 확인
			if(rs.next()) {
				// 결과가 있다면 DTO객체에 회원 정보 저장
				dto.setId(rs.getString("id"));
				dto.setPass(rs.getString("pass"));
				dto.setName(rs.getString(3));
			}else {
				System.out.println("결과셋이 없습니다.");
			}
			
		} catch (Exception e) {
			System.out.println("getMemberDTO오류");
			e.printStackTrace();
		}
		
		return dto;
		
	}
	
	public Map<String, String> getMemberMap(String id, String pass){
		
		// 회원정보를 저장할 Map컬렉션 생성
		Map<String, String> map = new HashMap<String,String>();
		
		String query = "SELECT id, pass, name FROM "+
				" member WHERE id=? AND pass=? ";
		
		try {
			// prepare객체 생성
			psmt = con.prepareStatement(query);
			// 인파라미터 설정
			psmt.setString(1, id);
			psmt.setString(2, pass);
			// 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 오라클이 반환해준 ResultSet객체를 통해 결과값이 있는지 확인
			if(rs.next()) {
				// 결과가 있다면 Map컬렉션에 회원 정보 저장
				map.put("id",rs.getString("id")); // 아이디
				map.put("pass", rs.getString("pass")); // 패스워드
				map.put("name", rs.getString("name")); // 이름
				
			}else {
				System.out.println("결과셋이 없습니다.");
			}
			
		} catch (Exception e) {
			System.out.println("getMemberMap오류");
			e.printStackTrace();
		}
		
		// Map 컬렉션에 저장된 회원 정보
		return map;
		
	}
	
	
	public Map<String, String> getMemberMap(String id){
		
		// 회원정보를 저장할 Map컬렉션 생성
		Map<String, String> map = new HashMap<String,String>();
		
		String query = "SELECT id, pass, name FROM "+
				" member WHERE id=? ";
		
		try {
			// prepare객체 생성
			psmt = con.prepareStatement(query);
			// 인파라미터 설정
			psmt.setString(1, id);
			
			// 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 오라클이 반환해준 ResultSet객체를 통해 결과값이 있는지 확인
			if(rs.next()) {
				// 결과가 있다면 Map컬렉션에 회원 정보 저장
				map.put("id",rs.getString("id")); // 아이디
				map.put("pass", rs.getString("pass")); // 패스워드
				map.put("name", rs.getString("name")); // 이름
				
			}else {
				System.out.println("결과셋이 없습니다.");
			}
			
		} catch (Exception e) {
			System.out.println("getMemberMap오류");
			e.printStackTrace();
		}
		
		// Map 컬렉션에 저장된 회원 정보
		return map;
		
	}
	
	// 자원 해제
	public void close() {
		try {
			
			if(rs!=null) rs.close();
			if(psmt!=null) psmt.close();
			if(con!=null) con.close();
			
		}catch(Exception e) {
			System.out.println("Oracle 자원 반납시 예외발생");
		}
	}
	
	
}



