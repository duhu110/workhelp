package com.chinatelecom.ctsi.workhelper.model;

import java.util.Date;

/**
 * User: zzk
 * Time: 2015/5/20 9:52
 */
public class DutyExecutor {
    private Integer id;
    private Integer dutyId;
    private Integer userId;
    private Integer state;
    private Date time;

    @Override
    public String toString() {
        return "DutyExecutor{" +
                "id=" + id +
                ", dutyId=" + dutyId +
                ", userId=" + userId +
                ", state=" + state +
                ", time=" + time +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDutyId() {
        return dutyId;
    }

    public void setDutyId(Integer dutyId) {
        this.dutyId = dutyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
