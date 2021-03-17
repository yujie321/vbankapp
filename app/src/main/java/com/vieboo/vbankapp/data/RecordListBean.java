package com.vieboo.vbankapp.data;

public class RecordListBean {
    /**
     * cameraName : test
     * fileUrl : http://192.168.1.254:28280/record/1/0/2021/03/10/16/34_16.mp4
     * recordBeginTime : 2021-03-10 16:34:17
     * recordEndTime : 2021-03-10 16:34:47
     */

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
