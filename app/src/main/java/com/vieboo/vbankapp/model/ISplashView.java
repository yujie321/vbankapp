package com.vieboo.vbankapp.model;

import com.example.toollib.data.base.IBaseView;

public interface ISplashView extends IBaseView {

    /**
     * 动态库是否存在
     * @return boolean
     */
    boolean getLibraryExists();

    void startHomeFragment();

    void startActiveDeviceFragment();
}
