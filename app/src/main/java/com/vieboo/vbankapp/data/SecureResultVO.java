package com.vieboo.vbankapp.data;


import com.google.gson.annotations.SerializedName;

public class SecureResultVO {
    //人员id
    @SerializedName(value = "personid")
    private String personId;
    //人员名称
    @SerializedName(value = "personname")
    private String personName;
    //运营时间
    private String time;
    //运营镜头id
    @SerializedName(value = "cameraid")
    private String cameraId;
    //运营镜头名称
    @SerializedName(value = "cameraname")
    private String cameraName;
    @SerializedName(value = "imageurl")
    private String imageUrl;
    @SerializedName(value = "faceurl")
    private String faceUrl;

    public String getImageUrl() {
        return imageUrl == null ? "" : imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? "" : imageUrl;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getPersonId() {
        return personId == null ? "" : personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId == null ? "" : personId;
    }

    public String getPersonName() {
        return personName == null ? "" : personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName == null ? "" : personName;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time == null ? "" : time;
    }

    public String getCameraId() {
        return cameraId == null ? "" : cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId == null ? "" : cameraId;
    }

    public String getCameraName() {
        return cameraName == null ? "" : cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName == null ? "" : cameraName;
    }
}
