package com.example.toollib.http.exception;

import android.text.TextUtils;

/**
 * @author Administrator
 * @date 2019/5/7 0007
 */
public class ApiException extends RuntimeException {
    /** 错误码 */
    private int code;
    /** 错误信息 */
    private String msg;
    /** data */
    private String data;
    public ApiException() {
    }

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ApiException(int status, String msg , String data) {
        this.code = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return code;
    }

    public void setStatus(int status) {
        this.code = status;
    }

    public String getMsg() {
        return TextUtils.isEmpty(msg) ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
