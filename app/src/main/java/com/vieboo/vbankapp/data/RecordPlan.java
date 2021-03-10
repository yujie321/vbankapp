package com.vieboo.vbankapp.data;

public class RecordPlan {
    /**
     * id : 16
     * name : 测试计划录像
     * type : 0
     * beginDate : 2021-02-28
     * endDate : 2021-03-26
     * beginTime : 17:05:34
     * endTime : 19:05:33
     * duration : null
     * description : 测试计划
     * cameraNum : 2
     * cameraIdList : null
     * cameraNameList : null
     */

    private int id;
    private String name;
    private int type;
    private String beginDate;
    private String endDate;
    private String beginTime;
    private String endTime;
    private int duration;
    private String description;
    private int cameraNum;
    private Object cameraIdList;
    private Object cameraNameList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCameraNum() {
        return cameraNum;
    }

    public void setCameraNum(int cameraNum) {
        this.cameraNum = cameraNum;
    }

    public Object getCameraIdList() {
        return cameraIdList;
    }

    public void setCameraIdList(Object cameraIdList) {
        this.cameraIdList = cameraIdList;
    }

    public Object getCameraNameList() {
        return cameraNameList;
    }

    public void setCameraNameList(Object cameraNameList) {
        this.cameraNameList = cameraNameList;
    }
}
