package com.vieboo.vbankapp.data;

import com.google.gson.annotations.SerializedName;

public class VQDResultVO  {

    //镜头状态,0:未知 1:正常 2:警告 4:异常 8:诊断失败
    private Integer status;
    //镜头名称
    @SerializedName(value = "cameraname")
    private String cameraName;
    //故障内容
    @SerializedName(value = "errname")
    private String errName;
    //网络连通性,1:在线,0:离线
    @SerializedName(value = "isonline")
    private Integer isOnline;
    //诊断时间
    private String time;
    //录像状态,0:未知 1:正常 2:警告 4:异常 8:诊断失败
    @SerializedName(value = "recordstatus")
    private Integer recordStatus;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCameraName() {
        return cameraName == null ? "" : cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName == null ? "" : cameraName;
    }

    public String getErrName() {
        return errName == null ? "" : errName;
    }

    public void setErrName(String errName) {
        this.errName = errName == null ? "" : errName;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time == null ? "" : time;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

}
