package com.example.toollib.http.version;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class Version {

    @Id
    private Long id;
    /** 版本id */
    private int versionId;
    /** 手机类型：1安卓手机;2苹果手机 */
    private int mobileType;
    /** 版本号 */
    private String version;
    /** 版本说明 */
    private String versionDesccribe;
    /** 是否强制更新:0否;1是 */
    private int ifComple;
    /** 版本更新地址 */
    private String versionUrl;
    /** 内部版本号 */
    private int versionCode;

    @Generated(hash = 2103309236)
    public Version(Long id, int versionId, int mobileType, String version,
                   String versionDesccribe, int ifComple, String versionUrl,
                   int versionCode) {
        this.id = id;
        this.versionId = versionId;
        this.mobileType = mobileType;
        this.version = version;
        this.versionDesccribe = versionDesccribe;
        this.ifComple = ifComple;
        this.versionUrl = versionUrl;
        this.versionCode = versionCode;
    }

    @Generated(hash = 1433053919)
    public Version() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public int getMobileType() {
        return mobileType;
    }

    public void setMobileType(int mobileType) {
        this.mobileType = mobileType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersionDesccribe() {
        return versionDesccribe;
    }

    public void setVersionDesccribe(String versionDesccribe) {
        this.versionDesccribe = versionDesccribe;
    }

    public int getIfComple() {
        return ifComple;
    }

    public void setIfComple(int ifComple) {
        this.ifComple = ifComple;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

}
