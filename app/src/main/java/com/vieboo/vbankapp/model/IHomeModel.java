package com.vieboo.vbankapp.model;

import com.example.toollib.data.IBaseModule;

public interface IHomeModel extends IBaseModule<IHomeView> {

    /**
     * 获取当日总览
     */
    void getTodaySummary();

    /**
     * 获取当日客流信息
     */
    void getPassenger();

    /**
     * 请求通知列表
     */
    void requestNoticeList();

    /**
     * 押运信息
     */
    void getEscortInfo();

    /**
     * 导航菜单
     */
    void getNavigationList();
}
