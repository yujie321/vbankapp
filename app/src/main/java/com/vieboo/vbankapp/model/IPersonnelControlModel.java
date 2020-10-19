package com.vieboo.vbankapp.model;

import com.example.toollib.data.IBaseModule;

public interface IPersonnelControlModel extends IBaseModule<IPersonnelControlView> {

    /**
     * 员工风采
     */
    void getStaffStyle();

    /**
     * 打卡记录
     */
    void getPunchRecord();

    /**
     * 上班打卡
     */
    void clockIn();

    /**
     * 下班打卡
     */
    void clockOut();
}
