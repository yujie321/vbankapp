package com.vieboo.vbankapp.model;

import com.example.toollib.data.IBaseModule;

public interface IDeviceStatusModel extends IBaseModule<IDeviceStatusView> {

    /**
     * 请求视频诊断
     */
    void requestVQD();

}
