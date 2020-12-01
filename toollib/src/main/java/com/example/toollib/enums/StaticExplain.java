package com.example.toollib.enums;

/**
 * @author Administrator
 * @date 2019/8/6 0006
 */
public enum StaticExplain {


    /**
     * 登录
     */
    LOGIN_STATUS(1004, "您的登录 信息已过期重新登录"),
    LOGIN_OTHER_STATUS(1005, "您的账号在其它手机登录"),
    LOGIN_STATUS_(1007, "您的登录 信息已过期重新登录"),
    PAGE_NUMBER(1, "加载 page  默认 1"),
    PAGE_NUM(20, "刷新加载一次加载20条数据"),;
    private int code;
    private String explain;

    StaticExplain(int code, String explain) {
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
