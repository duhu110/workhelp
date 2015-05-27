package com.chinatelecom.ctsi.workhelper.httpresponse;

import com.chinatelecom.ctsi.workhelper.model.DutyInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by apple on 14/12/10.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class DutyResponseInfo extends BaseResponseInfo{
    
	/*
	 * 如果JSON 内容名称不一样用这个修改
	 * 
	 * @JsonProperty("dutyList")
	 */
	private List<DutyInfo> dutyList;

	public List<DutyInfo> getDutyList() {
		return dutyList;
	}

	public void setDutyList(List<DutyInfo> dutyList) {
		this.dutyList = dutyList;
	}

    

}
