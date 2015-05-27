package com.chinatelecom.ctsi.workhelper.protocol.base;

public class BaseResponse {

	public static final int RESPONSE_CODE_SUCCESS = 1;
	public static final int RESPONSE_CODE_FAILED = 0;
	public static final int RESPONSE_CODE_UPDATE = 2;
	int code;
	String message;
	long serverTime;
	public BaseResponse() {
		// TODO Auto-generated constructor stub
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getServerTime() {
		return serverTime;
	}

	public void setServerTime(long serverTime) {
		this.serverTime = serverTime;
	}

	

}
