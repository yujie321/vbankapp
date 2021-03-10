package com.vieboo.vbankapp.data;

public class RecordInfo {

    private String cameraName;
    private String fileUrl;
    private String recordBeginTime;
    private String recordEndTime;

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getRecordBeginTime() {
        return recordBeginTime;
    }

    public void setRecordBeginTime(String recordBeginTime) {
        this.recordBeginTime = recordBeginTime;
    }

    public String getRecordEndTime() {
        return recordEndTime;
    }

    public void setRecordEndTime(String recordEndTime) {
        this.recordEndTime = recordEndTime;
    }
}
