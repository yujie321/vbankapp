package com.vieboo.vbankapp.data;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PersonImageBean  {

    @Id(autoincrement = true)
    private Long id;
    private String personId;
    private String imageUrl;
    private String type;
    //特征
    private String padFeature;
    private long dataVersion;
    //特征提取状态 0-待提取 1-提取成功 -1-提取失败
    private Integer featState;

    @Generated(hash = 941548286)
    public PersonImageBean(Long id, String personId, String imageUrl, String type,
            String padFeature, long dataVersion, Integer featState) {
        this.id = id;
        this.personId = personId;
        this.imageUrl = imageUrl;
        this.type = type;
        this.padFeature = padFeature;
        this.dataVersion = dataVersion;
        this.featState = featState;
    }

    @Generated(hash = 55526495)
    public PersonImageBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPadFeature() {
        return padFeature;
    }

    public void setPadFeature(String padFeature) {
        this.padFeature = padFeature;
    }

    public long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Integer getFeatState() {
        return featState;
    }

    public void setFeatState(Integer featState) {
        this.featState = featState;
    }
}
