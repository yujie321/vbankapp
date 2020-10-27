package com.vieboo.vbankapp.model;

import com.example.toollib.data.IBaseModule;

public interface IRegulationsModel extends IBaseModule<IRegulationsView> {

    void initData();

    void findRegulations();

}
