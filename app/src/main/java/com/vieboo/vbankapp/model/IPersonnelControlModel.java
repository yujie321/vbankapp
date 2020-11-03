package com.vieboo.vbankapp.model;

import android.graphics.Bitmap;

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
     * @param bitmap
     * @param personId
     */
    void clockIn(Bitmap bitmap, String personId);

    /**
     * 下班打卡
     */
    void clockOut(Bitmap bitmap, String personId);
}
