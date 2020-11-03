package com.example.toollib.http;


import androidx.annotation.NonNull;

import com.example.toollib.http.version.Version;
import com.google.gson.annotations.SerializedName;

import java.util.Optional;


public class HttpResult<T> {

    @SerializedName("msg")
    private String msg;

    @SerializedName("code")
    private String code;

    @SerializedName("resData")
    private T data ;

    private Version version;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    @NonNull
    @Override
    public String toString() {
        return "HttpResult{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", resData=" + data +
                ", version=" + version +
                '}';
    }
}
