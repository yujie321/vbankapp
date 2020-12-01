package com.vieboo.vbankapp.model;

import com.example.toollib.data.IBaseModule;

public interface IAddPersonalModel extends IBaseModule<IAddPersonalView> {

    /**
     * 初始化数据
     */
    void initData();

    void initIDCard();
}
