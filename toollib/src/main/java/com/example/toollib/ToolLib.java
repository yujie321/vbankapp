package com.example.toollib;

import android.app.Application;
import android.content.Context;

import com.example.toollib.util.Utils;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;

/**
 * @author Administrator
 * @date 2019/6/24 0024
 */
public class ToolLib {

    /** 全局上下文 */
    private Application mContext;
    /** 连接超时时长x秒 */
    private int connectTimeOut = 60;
    /** 读数据超时时长x秒 */
    private int readTimeOut = 60;
    /** 写数据接超时时长x秒 */
    private int writeTimeOut = 60;

    private String baseUrl ="";


    private ToolLib(){

    }

    public static ToolLib getInstance() {
        return ToolLibHolder.holder;
    }

    private static class ToolLibHolder {
        private static ToolLib holder = new ToolLib();
    }

    /** 必须在全局Application先调用，获取context上下文，否则缓存无法使用 */
    public ToolLib init(Application mContext) {
        this.mContext = mContext;
        return this;
    }

    /** 获取全局上下文 */
    public Context getContext() {
        Utils.checkNotNull(mContext, "please call ToolLib.getInstance().init() first in application!");
        return mContext.getApplicationContext();
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public int getWriteTimeOut() {
        return writeTimeOut;
    }

    public void setWriteTimeOut(int writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }



}
