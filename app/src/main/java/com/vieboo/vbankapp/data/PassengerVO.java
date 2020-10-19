package com.vieboo.vbankapp.data;

public class PassengerVO {

    //当日客流人数
    private int passengerNum;
    //上一工作日客流人数
    private int lastPassengerNum;
    //已完成安保运营数量
    private int normalGuardNum;
    //异常安保运营数量
    private int abnormalGuardNum;
    //安保运营人数
    private int guardPersonNum;
    //运营区域数量
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
        return 10;
    }

    public void setNormalGuardNum(int normalGuardNum) {
        this.normalGuardNum = normalGuardNum;
    }

    public int getAbnormalGuardNum() {
        return 5;
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
