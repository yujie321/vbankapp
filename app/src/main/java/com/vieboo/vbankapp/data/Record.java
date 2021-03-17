package com.vieboo.vbankapp.data;

import java.util.List;

public class Record {

    /**
     * id : 1
     * planId : 17
     * planName : 测试手动录像
     * beginTime : 2021-03-10 16:34:16
     * endTime : 2021-03-10 16:34:46
     * status : 1
     * recordList : [{"cameraName":null,"fileUrl":"http://192.168.1.254:28280/record/1/0/2021/03/10/16/34_16.mp4","recordBeginTime":"2021-03-10 16:34:17","recordEndTime":"2021-03-10 16:34:47"}]
     */

    private int id;
    private int planId;
    private String planName;
    private String beginTime;
    private String endTime;
    private int status;
    private List<RecordListBean> recordList;

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

    public List<RecordListBean> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RecordListBean> recordList) {
        this.recordList = recordList;
    }
}
