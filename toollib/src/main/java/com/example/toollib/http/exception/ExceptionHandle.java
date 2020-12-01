package com.example.toollib.http.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;


/**
 * @author Administrator
 * @date 2019/6/25 0025
 */
public class ExceptionHandle {

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof ApiException) {
            ex = (ApiException) e;
            return ex;
        } else if (e instanceof HttpException) {
            ex = new ApiException(e, HttpError.HTTP_ERROR.getCode());
            ex.setMsg("服务器连接异常，请稍后再试");
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException || e instanceof MalformedJsonException) {
            ex = new ApiException(e, HttpError.PARSE_ERROR.getCode());
            ex.setMsg("解析错误");
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, HttpError.NET_WORk_ERROR.getCode());
            ex.setMsg("连接失败");
            return ex;
        } else if (e instanceof javax.net.ssl.SSLException) {
            ex = new ApiException(e, HttpError.SSL_ERROR.getCode());
            ex.setMsg("证书验证失败");
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ApiException(e, HttpError.TIMEOUT_ERROR.getCode());
            ex.setMsg("连接超时");
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ApiException(e, HttpError.TIMEOUT_ERROR.getCode());
            ex.setMsg("连接超时");
            return ex;
        } else if (e instanceof java.net.UnknownHostException) {
            ex = new ApiException(e, HttpError.TIMEOUT_ERROR.getCode());
            ex.setMsg("主机地址未知");
            return ex;
        } else {
            ex = new ApiException(e, HttpError.UNKNOWN.getCode());
            ex.setMsg("未知错误");
            return ex;
        }
    }
}
