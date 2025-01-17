package com.vieboo.vbankapp.model;

import com.example.toollib.data.IBaseModule;

public interface IRecordManagerModel extends IBaseModule<IRecordManagerView> {
    void requestRecordPlan();
    void requestRecordList();

    void requestRecordDetail(int id);
}
