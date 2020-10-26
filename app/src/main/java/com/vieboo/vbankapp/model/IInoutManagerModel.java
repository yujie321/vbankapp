package com.vieboo.vbankapp.model;

import com.example.toollib.data.IBaseModule;

public interface IInoutManagerModel extends IBaseModule<IInoutManagerView> {

    /**
     * 历史进出
     */
    void requestPersonInOut();

    /**
     * 实时进出
     */
    void requestAccessPersonInOut();

    void getTodayPersonInoutStatic();
}
