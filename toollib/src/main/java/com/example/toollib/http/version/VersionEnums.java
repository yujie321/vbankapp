package com.example.toollib.http.version;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public enum VersionEnums {

    /** 版本更新状态枚举 */
    LOGIN_STATUS(1004 , "您的登录 信息已过期重新登录"),
    LOGIN_OTHER_STATUS(1005 , "您的账号在其它手机登录"),
    LOGIN_STATUS_(1007 , "您的登录 信息已过期重新登录"),
    APP_UPDATE(3000 , "更新"),
    APP_FORCED_UPDATES(1 , "强制更新"),
    ;
    private int code;
    private String explain;

    VersionEnums(int code, String explain) {
        this.code = code;
        this.explain = explain;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

}
