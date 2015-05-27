package com.chinatelecom.ctsi.workhelper.model;

public class MsgData {

	private String msgDate;
	private String msgTitle;
	private String msgContent;
	
	public MsgData(String msgDate, String msgTitle, String msgContent) {
		super();
		this.msgDate = msgDate;
		this.msgTitle = msgTitle;
		this.msgContent = msgContent;
	}

	public String getMsgDate() {
		return msgDate;
	}

	public void setMsgDate(String msgDate) {
		this.msgDate = msgDate;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	
	
	
	
}
