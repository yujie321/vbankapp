package com.vieboo.vbankapp.model;

import com.example.toollib.data.IBaseModule;

public interface IBranchOperateModel extends IBaseModule<IBranchOperateView> {
    void getLast7dayPeriodStatic();

    void getTodayPassengerStatic();
}
