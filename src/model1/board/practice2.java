package model1.board;

public class practice2 {

	public static String pagingStr(int totalCount, int pageSize,
		int blockPage, int pageNum, String reqUrl) {
		
		String pagingStr = "";
		
		int totalPage = (int)(Math.ceil(((double)totalCount/pageSize)));
		int pageTemp = (((pageNum-1)/blockPage)*blockPage)+1;
		
		if(pageTemp != 1) {
			pagingStr += "<a href='"+reqUrl+"?pageNum=1'>[첫페이지]</a>";
			pagingStr += "&nbsp;";
			pagingStr += "<a href='"+reqUrl+"?pageNum="+(pageTemp-1)+"'>"
						+ "[이전블럭]</a>";
			
		}
		int blockCount = 1;
		
		while(blockCount <= blockPage && pageTemp <= totalPage) {
			if(pageTemp == pageNum) {
				pagingStr += "&nbsp;"+pageTemp+"&nbsp;";
			}else {
				pagingStr += "&nbsp;<a href'"+reqUrl+"?pageNum="+pageTemp+"'>"
						+pageTemp+"</a>&nbsp;";
				
				
			}
			
			pageTemp ++;
			blockCount++;
			
		}
		if(pageTemp <= totalPage) {
			pagingStr += "<a href='"+reqUrl+"?pageNum="+pageTemp+"'>"
					+ "[다음블럭]</a>";
			pagingStr += "&nbsp;";
			pagingStr += "<a href='"+reqUrl+"?pageNum="+totalPage+"'>"
						+ "[마지막페이지]</a>";
			
			
			
		}
		
		return pagingStr;
		
		
	}
	
	
	
}
