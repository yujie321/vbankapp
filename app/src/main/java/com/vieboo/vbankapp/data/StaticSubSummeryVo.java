package com.vieboo.vbankapp.data;


import com.google.gson.annotations.SerializedName;

public class StaticSubSummeryVo {
    //正常数量
    @SerializedName(value = "normalnum")
    private int normalNum;
    //异常数量
    @SerializedName(value = "abnormalnum")
    private int abnormalNum;
    //总数
    @SerializedName(value = "totalnum")
    private int totalNum;

    public int getNormalNum() {
        return normalNum;
    }

    public void setNormalNum(int normalNum) {
        this.normalNum = normalNum;
    }

    public int getAbnormalNum() {
        return abnormalNum;
    }

    public void setAbnormalNum(int abnormalNum) {
        this.abnormalNum = abnormalNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}
