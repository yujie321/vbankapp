package com.vieboo.vbankapp.data;

public class PunchRecordVO {

    private String id;
    private String imageUrl;
    private String Name;
    private String number;
    private String positionName;

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id == null ? "" : id;
    }

    public String getImageUrl() {
        return imageUrl == null ? "" : imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? "" : imageUrl;
    }

    public String getName() {
        return Name == null ? "" : Name;
    }

    public void setName(String name) {
        Name = name == null ? "" : name;
    }

    public String getNumber() {
        return number == null ? "" : number;
    }

    public void setNumber(String number) {
        this.number = number == null ? "" : number;
    }

    public String getPositionName() {
        return positionName == null ? "" : positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName == null ? "" : positionName;
    }
}
