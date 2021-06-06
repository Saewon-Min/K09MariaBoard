package model2.mvcboard;

public class CommentDTO {

	private String idx ;
	private String board_idx ;
	private String name ;
	private String pass ;
	private String comments ;
	//private String commentsEdit ; // 댓글 수정때문에 추가
	private String postdate ; // 작성일을 to_char()를 통해 문자열로 변경하기 때문에
	// java.sql.Date 타입이 아닌 String 타입으로 정의한다.
	
	
	
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getBoard_idx() {
		return board_idx;
	}
	public void setBoard_idx(String board_idx) {
		this.board_idx = board_idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
//	public String getCommentsEdit() {
//		return commentsEdit;
//	}
//	public void setCommentsEdit(String commentsEdit) {
//		this.commentsEdit = commentsEdit;
//	}
	public String getPostdate() {
		return postdate;
	}
	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}
	
	
	
	
	
}
