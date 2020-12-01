package com.sdses.idCard;

import android.graphics.Bitmap;

public class IdInfo {

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idCardNum;

    /**
     * 性别 0：男   1：女
     */
    private String sex;
    /**
     * 民族
     */
    private String national;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 地址
     */
    private String address;

    /**
     * 签发机关
     */
    private String issue;


    /**
     * 有效期限
     */
    private String indate;


    private int width;
    private int height;

    private Bitmap photoBmp;

    /**
     * 人脸特征数据
     */
    private byte[] pfFaceFeat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getIndate() {
        return indate;
    }

    public void setIndate(String indate) {
        this.indate = indate;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public byte[] getPfFaceFeat() {
        return pfFaceFeat;
    }

    public void setPfFaceFeat(byte[] pfFaceFeat) {
        this.pfFaceFeat = pfFaceFeat;
    }

    @Override
    public String toString() {
        return "IdInfo{" +
                "name='" + name + '\'' +
                ", idCardNum='" + idCardNum + '\'' +
                ", sex='" + sex + '\'' +
                ", national='" + national + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                ", issue='" + issue + '\'' +
                ", indate='" + indate + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public Bitmap getPhotoBmp() {
        return photoBmp;
    }

    public void setPhotoBmp(Bitmap photoBmp) {
        this.photoBmp = photoBmp;
    }

}
