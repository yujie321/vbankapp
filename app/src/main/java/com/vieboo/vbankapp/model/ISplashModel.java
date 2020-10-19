package com.vieboo.vbankapp.model;

import com.example.toollib.data.IBaseModule;

public interface ISplashModel extends IBaseModule<ISplashView> {

    /**
     * 激活人脸识别引擎
     */
    void activeEngine();

}
