package com.chinatelecom.ctsi.workhelper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PushMessage {
	
	
	public static final int TYPE_NEW_TASK=1;
	public static final int TYPE_NEW_SHARED=2;
//	
//	05-21 14:30:24.683: D/MqttCallbackHandlermmm(11694): 重复{"type":1,"duty":
	//{"id":1,"title":"测试","content":"hello world","deadline":1432189688802,
	//"createUserID":1,"executorID":1,"status":0}}
//
//	
	
	int type;
	@JsonProperty("duty")
	DutyInfo info;
	SharedDuty sharedDuty;
		
	
	
	public PushMessage() {
		// TODO Auto-generated constructor stub
	}



	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}



	public DutyInfo getInfo() {
		return info;
	}



	public void setInfo(DutyInfo info) {
		this.info = info;
	}



	public SharedDuty getSharedDuty() {
		return sharedDuty;
	}



	public void setSharedDuty(SharedDuty sharedDuty) {
		this.sharedDuty = sharedDuty;
	}
	
	
}

