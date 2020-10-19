package com.vieboo.vbankapp.data;


import com.google.gson.annotations.SerializedName;

public class StaticTodaySummeryVo {
    //当日打卡人数
    @SerializedName(value = "ondutypersonnum")
    private int onDutyPersonNum;
    //网点排名
    @SerializedName(value = "branchrank")
    private int branchRank;
    //设备在线情况
    @SerializedName(value = "devicestatic")
    private StaticSubSummeryVo deviceStatic;
    //人员打卡情况
    @SerializedName(value = "dutystatic")
    private StaticSubSummeryVo dutyStatic;
    //通知公告阅读情况
    @SerializedName(value = "noticestatic")
    private StaticSubSummeryVo noticeStatic;
    //规章制度阅读情况
    @SerializedName(value = "rulestatic")
    private StaticSubSummeryVo ruleStatic;
    //进出情况
    @SerializedName(value = "inoutstatic")
    private StaticSubSummeryVo inoutStatic;
    //呼叫接听情况
    @SerializedName(value = "callstatic")
    private StaticSubSummeryVo callStatic;

    public int getOnDutyPersonNum() {
        return onDutyPersonNum;
    }

    public void setOnDutyPersonNum(int onDutyPersonNum) {
        this.onDutyPersonNum = onDutyPersonNum;
    }

    public int getBranchRank() {
        return branchRank;
    }

    public void setBranchRank(int branchRank) {
        this.branchRank = branchRank;
    }

    public StaticSubSummeryVo getDeviceStatic() {
        return deviceStatic;
    }

    public void setDeviceStatic(StaticSubSummeryVo deviceStatic) {
        this.deviceStatic = deviceStatic;
    }

    public StaticSubSummeryVo getDutyStatic() {
        return dutyStatic;
    }

    public void setDutyStatic(StaticSubSummeryVo dutyStatic) {
        this.dutyStatic = dutyStatic;
    }

    public StaticSubSummeryVo getNoticeStatic() {
        return noticeStatic;
    }

    public void setNoticeStatic(StaticSubSummeryVo noticeStatic) {
        this.noticeStatic = noticeStatic;
    }

    public StaticSubSummeryVo getRuleStatic() {
        return ruleStatic;
    }

    public void setRuleStatic(StaticSubSummeryVo ruleStatic) {
        this.ruleStatic = ruleStatic;
    }

    public StaticSubSummeryVo getInoutStatic() {
        return inoutStatic;
    }

    public void setInoutStatic(StaticSubSummeryVo inoutStatic) {
        this.inoutStatic = inoutStatic;
    }

    public StaticSubSummeryVo getCallStatic() {
        return callStatic;
    }

    public void setCallStatic(StaticSubSummeryVo callStatic) {
        this.callStatic = callStatic;
    }
}
