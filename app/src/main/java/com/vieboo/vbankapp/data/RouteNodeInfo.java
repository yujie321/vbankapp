package com.vieboo.vbankapp.data;

public class RouteNodeInfo {

    //实际到达时间
    private String arriveTime;
    //预计到达时间
    private String estimateArriveTime;
    //是否为当前位置
    private boolean isCurrent;
    //是否为本节点(0已经经过 1正在交接 2还未经过)
    private Integer status;
    //网点名称
    private String name;

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getEstimateArriveTime() {
        return estimateArriveTime;
    }

    public void setEstimateArriveTime(String estimateArriveTime) {
        this.estimateArriveTime = estimateArriveTime;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RouteNodeInfo{" +
                "arriveTime='" + arriveTime + '\'' +
                ", estimateArriveTime='" + estimateArriveTime + '\'' +
                ", isCurrent=" + isCurrent +
                ", isThis=" + status +
                ", name='" + name + '\'' +
                '}';
    }
}
