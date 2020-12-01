package com.vieboo.vbankapp.data;

import java.util.List;

public class TodyPassengerVO {

    private List<SecureRecordVo> todayPassengerStatic;
    private List<SecureRecordVo> todayVipPassengerStatic;

    public List<SecureRecordVo> getTodayPassengerStatic() {
        return todayPassengerStatic;
    }

    public void setTodayPassengerStatic(List<SecureRecordVo> todayPassengerStatic) {
        this.todayPassengerStatic = todayPassengerStatic;
    }

    public List<SecureRecordVo> getTodayVipPassengerStatic() {
        return todayVipPassengerStatic;
    }

    public void setTodayVipPassengerStatic(List<SecureRecordVo> todayVipPassengerStatic) {
        this.todayVipPassengerStatic = todayVipPassengerStatic;
    }
}
