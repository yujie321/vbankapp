package com.vieboo.vbankapp.data;

import java.util.List;

public class EscortInfo {

    //实际到达时间
    private String arriveTime;
    //押运结束时间
    private String endTime;
    //预计到达时间
    private String estimateArriveTime;
    //押运车辆车牌号码
    private String plateNum;
    //押运人员列表
    private List<EscortPerson> escortPersonList;
    //录像列表
    private List<RecordInfo> recordList;
    //路线节点列表
    private List<RouteNodeInfo> routeNodeList;
    //交接人员列表
    private List<TransPersonInfo> transPersonList;

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEstimateArriveTime() {
        return estimateArriveTime;
    }

    public void setEstimateArriveTime(String estimateArriveTime) {
        this.estimateArriveTime = estimateArriveTime;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public List<EscortPerson> getEscortPersonList() {
        return escortPersonList;
    }

    public void setEscortPersonList(List<EscortPerson> escortPersonList) {
        this.escortPersonList = escortPersonList;
    }

    public List<RecordInfo> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RecordInfo> recordList) {
        this.recordList = recordList;
    }

    public List<RouteNodeInfo> getRouteNodeList() {
        return routeNodeList;
    }

    public void setRouteNodeList(List<RouteNodeInfo> routeNodeList) {
        this.routeNodeList = routeNodeList;
    }

    public List<TransPersonInfo> getTransPersonList() {
        return transPersonList;
    }

    public void setTransPersonList(List<TransPersonInfo> transPersonList) {
        this.transPersonList = transPersonList;
    }

}
