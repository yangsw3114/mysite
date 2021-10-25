package com.douzone.mysite.vo;

public class BoardVo {
	private Long no;
	private String title;
	private String contents;
	private Integer  hit;
	private String regdate;
	private Integer  GroupNo;
	private Integer  OrderNo;
	private Integer  depth;
	private Long UserNo;
	private String userName;
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public Long getUserNo() {
		return UserNo;
	}
	public void setUserNo(Long userNo) {
		UserNo = userNo;
	}
	public Integer getHit() {
		return hit;
	}
	public void setHit(Integer hit) {
		this.hit = hit;
	}
	public Integer getGroupNo() {
		return GroupNo;
	}
	public void setGroupNo(Integer groupNo) {
		GroupNo = groupNo;
	}
	public Integer getOrderNo() {
		return OrderNo;
	}
	public void setOrderNo(Integer orderNo) {
		OrderNo = orderNo;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", contents=" + contents + ", hit=" + hit + ", regdate="
				+ regdate + ", GroupNo=" + GroupNo + ", OrderNo=" + OrderNo + ", depth=" + depth + ", UserNo=" + UserNo
				+ ", userName=" + userName + "]";
	}
	

	
	
	
}
