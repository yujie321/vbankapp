package com.vieboo.vbankapp.data;

public class DeviceStatusVo {
    private int normalnum;
    private int abnormalnum;
    private int onlinenum;
    private int offlinenum;

    public int getNormalnum() {
        return normalnum;
    }

    public void setNormalnum(int normalnum) {
        this.normalnum = normalnum;
    }

    public int getAbnormalnum() {
        return abnormalnum;
    }

    public void setAbnormalnum(int abnormalnum) {
        this.abnormalnum = abnormalnum;
    }

    public int getOnlinenum() {
        return onlinenum;
    }

    public void setOnlinenum(int onlinenum) {
        this.onlinenum = onlinenum;
    }

    public int getOfflinenum() {
        return offlinenum;
    }

    public void setOfflinenum(int offlinenum) {
        this.offlinenum = offlinenum;
    }
}
