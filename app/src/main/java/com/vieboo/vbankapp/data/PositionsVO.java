package com.vieboo.vbankapp.data;

public class PositionsVO {

    private Integer id;
    private String positionName;
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName == null ? "" : positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName == null ? "" : positionName;
    }

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? "" : createTime;
    }
}
