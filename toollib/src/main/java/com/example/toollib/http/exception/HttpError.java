package com.example.toollib.http.exception;

/**
 * @author Administrator
 * @date 2019/6/25 0025
 */
public enum HttpError {
    /***/
    HTTP_SUCCESS(0,"请求成功"),
    UNKNOWN(109 , "未知错误"),
    PARSE_ERROR(101, "解析错误"),
    NET_WORk_ERROR(102, "网络错误"),
    HTTP_ERROR(103, "协议出错"),
    SSL_ERROR(105, "证书出错"),
    TIMEOUT_ERROR(106, "连接超时"),
    ;

    HttpError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
