package com.vieboo.vbankapp.model;

import com.example.toollib.data.base.IBaseListView;
import com.vieboo.vbankapp.data.Navigation;
import com.vieboo.vbankapp.data.NoticeListVO;
import com.vieboo.vbankapp.data.PassengerVO;
import com.vieboo.vbankapp.data.StaticTodaySummeryVo;

import java.util.List;

public interface IHomeView extends IBaseListView {

    /**
     * 当日总览
     * @param staticTodaySummeryVo staticTodaySummeryVo
     */
    void setTodaySummary(StaticTodaySummeryVo staticTodaySummeryVo);

    /**
     * 当日客流及安保运营统计情况
     * @param passengerVO passengerVO
     */
    void setPassenger(PassengerVO passengerVO);

    /**
     * 刷新通知列表
     * @param passengerVO passengerVO
     */
    void refreshNoticeListSuccess(List<NoticeListVO> passengerVO);

    /**
     * 加载通知列表
     * @param passengerVO passengerVO
     */
    void loadMoreNoticeListSuccess(List<NoticeListVO> passengerVO);

    /**
     * 菜单
     * @param navigationList navigationList
     */
    void setNavigationList(List<Navigation> navigationList);

}
