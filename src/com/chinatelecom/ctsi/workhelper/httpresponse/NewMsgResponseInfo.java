package com.chinatelecom.ctsi.workhelper.httpresponse;

import com.chinatelecom.ctsi.workhelper.model.DutyInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class NewMsgResponseInfo {
    public static final int MSG_TYPE_DUTY =1;
    public static final int MSG_TYPE_NOTICE =2;
    public static final int MSG_TYPE_THIRD_PART =3;
    private int type;
    private String personId;

    @JsonProperty("duty_info")
    private List<DutyInfo> dutyInfoList;

   


    public List<DutyInfo> getDutyInfoList() {
		return dutyInfoList;
	}

	public void setDutyInfoList(List<DutyInfo> dutyInfoList) {
		this.dutyInfoList = dutyInfoList;
	}

	public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

   

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
