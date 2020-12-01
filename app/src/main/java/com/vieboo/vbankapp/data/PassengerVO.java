package com.vieboo.vbankapp.data;

import com.google.gson.annotations.SerializedName;

public class PassengerVO {

    //当日客流人数
    @SerializedName(value = "passengernum")
    private int passengerNum;
    //上一工作日客流人数
    @SerializedName(value = "lastpassengernum")
    private int lastPassengerNum;
    //已完成安保运营数量
    @SerializedName(value = "normalguardnum")
    private int normalGuardNum;
    //异常安保运营数量
    @SerializedName(value = "abnormalguardnum")
    private int abnormalGuardNum;
    //安保运营人数
    @SerializedName(value = "guardpersonnum")
    private int guardPersonNum;
    //运营区域数量
    @SerializedName(value = "guardregionnum")
    private int guardRegionNum;

    public int getPassengerNum() {
        return passengerNum;
    }

    public void setPassengerNum(int passengerNum) {
        this.passengerNum = passengerNum;
    }

    public int getLastPassengerNum() {
        return lastPassengerNum;
    }

    public void setLastPassengerNum(int lastPassengerNum) {
        this.lastPassengerNum = lastPassengerNum;
    }

    public int getNormalGuardNum() {
        return normalGuardNum;
    }

    public void setNormalGuardNum(int normalGuardNum) {
        this.normalGuardNum = normalGuardNum;
    }

    public int getAbnormalGuardNum() {
        return abnormalGuardNum;
    }

    public void setAbnormalGuardNum(int abnormalGuardNum) {
        this.abnormalGuardNum = abnormalGuardNum;
    }

    public int getGuardPersonNum() {
        return guardPersonNum;
    }

    public void setGuardPersonNum(int guardPersonNum) {
        this.guardPersonNum = guardPersonNum;
    }

    public int getGuardRegionNum() {
        return guardRegionNum;
    }

    public void setGuardRegionNum(int guardRegionNum) {
        this.guardRegionNum = guardRegionNum;
    }
}
