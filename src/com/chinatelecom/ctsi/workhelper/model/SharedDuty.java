package com.chinatelecom.ctsi.workhelper.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SharedDuty {

	public static final int TYPE_SHARE_TOALL = 1;
	
	public static final int TYPE_SHARE_PERSONAL = 2;

	private long id;
	
	
	private String content;
	
	DutyInfo duty;

	long sharedTime;
	int shareFrom;
	int shareTo;

	int shareType;
	int likeCount;
	ArrayList<Comment> comments = new ArrayList<Comment>();

	public String getContent() {
		return content;
	}

	public int getShareFrom() {
		return shareFrom;
	}

	public void setShareFrom(int shareFrom) {
		this.shareFrom = shareFrom;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public SharedDuty() {
		// TODO Auto-generated constructor stub
	}

	public DutyInfo getDuty() {
		return duty;
	}

	public void setDuty(DutyInfo duty) {
		this.duty = duty;
	}

	public long getSharedTime() {
		return sharedTime;
	}

	public void setSharedTime(long sharedTime) {
		this.sharedTime = sharedTime;
	}

	public int getShareType() {
		return shareType;
	}

	public void setShareType(int shareType) {
		this.shareType = shareType;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getShareTo() {
		return shareTo;
	}

	public void setShareTo(int shareTo) {
		this.shareTo = shareTo;
	}

	
	
	
	
}
