package com.chinatelecom.ctsi.workhelper.model;

import java.util.Date;

import com.ctsi.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DutyInfo {
	
	public static final int TYPE_ALL = 0;
	public static final int TYPE_TASK = 1;
	public static final int TYPE_REMIND = 2;

	public static final int STATUS_UNFINISHED = 0;

	public static final int STATUS_FINISHED = 1;

	/**
	 * {"id":1,"title":"测试","content":"hello world","deadline":1432189688802,
	//"createUserID":1,"executorID":1,"status":0}
	 */
	private int id;

	private String title;
	private String content;

	private long deadline;// 截止时间
	@JsonProperty("executorID")
	private int excutorId;// 执行人id
	@JsonProperty("createUserID")
	private int createUserId;// 创建人id

	private int status;// 完成状态

	private int type;// 提醒 OR 任务

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getDeadline() {
		return deadline;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}

	public int getExcutorId() {
		return excutorId;
	}

	public void setExcutorId(int excutorId) {
		this.excutorId = excutorId;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSpeechString() {
		StringBuffer sb = new StringBuffer();

		sb.append("您有一条新的任务,请于");
		sb.append(DateUtil.Date2String(new Date(), "MM月dd日"));
		sb.append("完成,内容如下:");
		sb.append(title + "。");
		sb.append(content + "。");
		sb.append("播报完毕,外勤助手团队感谢您的收听。");
		return sb.toString();
	}

}
