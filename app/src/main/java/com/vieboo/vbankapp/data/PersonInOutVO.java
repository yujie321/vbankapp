package com.vieboo.vbankapp.data;


import com.google.gson.annotations.SerializedName;

public class PersonInOutVO {
    //记录主键
    private Integer id;
    //进出时间
    private String time;
    //图片url
    @SerializedName(value = "imageurl")
    private String imageUrl;
    //进出人员名称
    @SerializedName(value = "personname")
    private String personName;
    //进出pad名称
    @SerializedName(value = "padname")
    private String padName;
    //人员所属组织
    @SerializedName(value = "orgname")
    private String orgName;
    //pad所属组
    @SerializedName(value = "groupname")
    private String groupName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time == null ? "" : time;
    }

    public String getImageUrl() {
        return imageUrl == null ? "" : imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? "" : imageUrl;
    }

    public String getPersonName() {
        return personName == null ? "" : personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName == null ? "" : personName;
    }

    public String getPadName() {
        return padName == null ? "" : padName;
    }

    public void setPadName(String padName) {
        this.padName = padName == null ? "" : padName;
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? "" : orgName;
    }

    public String getGroupName() {
        return groupName == null ? "" : groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? "" : groupName;
    }
}
