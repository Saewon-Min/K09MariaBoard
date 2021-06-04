package fileupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

public class FileUtil {
	public static MultipartRequest uploadFile(HttpServletRequest req,
			String saveDirectory, int maxPostSize) {
		
		MultipartRequest mr = null;
		try {
			/*
			파일 업로드를 위해 MultipartRequest 객체를 생성한다.
			해당 객체가 정상적으로 생성되면 파일업로드는 완료된다.
			 */
			mr = new MultipartRequest(req, saveDirectory,maxPostSize,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
		return mr;
		
	}
	
	
	// 파일 삭제
	public static void deleteFile(HttpServletRequest req, String directory, String filename) {
		
		// 서버의 물리적 경로 얻어오기
		String sDirectory = req.getServletContext().getRealPath(directory);
		
		// 물리적 경로와 파일명을 통해 파일 객체 생성
		File f = new File(sDirectory + File.separator+filename);
		
		// 파일이 존재하면
		if(f.exists()) {
			// 해당 파일 삭제
			f.delete();
		}
		
	}
	
	
	// 
	public static void downloadFile(HttpServletRequest req, HttpServletResponse resp,
			String directory, String sfileName, String ofileName) {
		
		String sDirectory = req.getServletContext().getRealPath(directory);
		try{	
			File file = new File(sDirectory, sfileName);
			InputStream iStream = new FileInputStream(file);
			
			String client = req.getHeader("User-Agent");
			if(client.indexOf("WOW64")==-1){
				ofileName = new String(ofileName.getBytes("UTF-8"), "ISO-8859-1");
			}
			else{
				ofileName = new String(ofileName.getBytes("KSC5601"), "ISO-8859-1");
			}
			
			resp.reset();
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment; filename=\"" + ofileName + "\"");
			resp.setHeader("Content-Length", ""+file.length() );
			
			//out.clear();
			
			OutputStream oStream = resp.getOutputStream();
			byte b[] = new byte[(int)file.length()];
			int readBuffer = 0;	
			while( (readBuffer = iStream.read(b)) > 0 ){
				oStream.write(b, 0, readBuffer);
			}	
			iStream.close();
			oStream.close();
		}
		catch(FileNotFoundException e){
			System.out.println("파일을 찾을 수 없습니다.");
			e.printStackTrace();
		}
		catch(Exception e){
			System.out.println("예외가 발생하였습니다.");
			e.printStackTrace();
		}
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}




