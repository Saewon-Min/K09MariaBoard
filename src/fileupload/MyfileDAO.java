package fileupload;

import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;

public class MyfileDAO extends JConnect{

	/*
	생성자에서는 부모 생성자를 호출하기 위해 super()를 통해
	호출한다.
	 */
	public MyfileDAO (ServletContext application) {
		super(application);
	}
	
	
	// 파일 등록
	public int myfileInsert(MyfileDTO dto) {
		
		int applyResult = 0;
		
		try {
			String query = " insert into myfile ( "
					+ " idx, name, title, cate, ofile, sfile ) "
					+ " values ( "
					+ " seq_board_num.nextval,? ,? ,? ,?, ? ) ";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getName());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getCate());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			
			
			applyResult = psmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("insert 중 예외발생");
			e.printStackTrace();
		}
		
		return applyResult;
		
		
	}
	
	
	// 파일 리스트 가져오기
	public List<MyfileDTO> myFileList(){
		List<MyfileDTO> fileList = new Vector<MyfileDTO>();
		
		String query = " select * from myfile order by idx desc  ";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				MyfileDTO dto = new MyfileDTO();
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setCate(rs.getString(4));
				dto.setOfile(rs.getString(5));
				dto.setSfile(rs.getString(6));
				dto.setPostdate(rs.getString(7));
				
				fileList.add(dto);
				
			
				
			}
			
		} catch (Exception e) {

			System.out.println("Select시 예외 발생");
			e.printStackTrace();
		}
		
		return fileList;
		
		
		
	}
	
	
	
	
	
	
}
