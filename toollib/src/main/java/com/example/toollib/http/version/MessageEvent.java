package com.example.toollib.http.version;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class MessageEvent {

    private int errCode;
    private Version version;

    public MessageEvent(int errCode , Version version) {
        this.errCode = errCode;
        this.version = version;
    }

    public int getErrCode() {
        return errCode;
    }

    public Version getVersion() {
        return version;
    }
}
