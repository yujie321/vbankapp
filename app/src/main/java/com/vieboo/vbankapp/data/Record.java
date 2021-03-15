package com.vieboo.vbankapp.data;

public class Record {
    /**
     * id : 1
     * planId : 17
     * planName : 测试手动录像
     * beginTime : 2021-03-10 16:34:16
     * endTime : 2021-03-10 16:34:46
     * status : 1
     * recordList : null
     */

    private int id;
    private int planId;
    private String planName;
    private String beginTime;
    private String endTime;
    private int status;
    private Object recordList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getRecordList() {
        return recordList;
    }

    public void setRecordList(Object recordList) {
        this.recordList = recordList;
    }
}
