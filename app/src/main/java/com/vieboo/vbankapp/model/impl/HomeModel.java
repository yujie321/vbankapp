package com.vieboo.vbankapp.model.impl;

import com.example.toollib.data.BaseModule;
import com.example.toollib.enums.StaticExplain;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.Navigation;
import com.vieboo.vbankapp.data.NoticeListVO;
import com.vieboo.vbankapp.data.PassengerVO;
import com.vieboo.vbankapp.data.StaticTodaySummeryVo;
import com.vieboo.vbankapp.http.ServiceUrl;
import com.vieboo.vbankapp.model.IHomeModel;
import com.vieboo.vbankapp.model.IHomeView;

import java.util.ArrayList;
import java.util.List;

public class HomeModel extends BaseModule<IHomeView> implements IHomeModel  {

    @Override
    public void getTodaySummary() {
        RxUtils.getObservable(ServiceUrl.getUserApi().toDaySummary())
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<StaticTodaySummeryVo>() {
                    @Override
                    protected void onSuccess(StaticTodaySummeryVo staticTodaySummeryVo) {
                        mViewRef.get().setTodaySummary(staticTodaySummeryVo);
                    }
                });
    }

    @Override
    public void getPassenger() {
        RxUtils.getObservable(ServiceUrl.getUserApi().passenger())
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<PassengerVO>() {
                    @Override
                    protected void onSuccess(PassengerVO passengerVO) {
                        mViewRef.get().setPassenger(passengerVO);
                    }
                });
    }

    @Override
    public void requestNoticeList() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getNoticeList())
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<List<NoticeListVO>>() {
                    @Override
                    protected void onSuccess(List<NoticeListVO> passengerVO) {
                        if (mViewRef.get().getPage() == StaticExplain.PAGE_NUMBER.getCode()) {
                            //刷新
                            mViewRef.get().refreshNoticeListSuccess(passengerVO);
                            mViewRef.get().finishRefresh();
                        } else {
                            //加载
                            mViewRef.get().loadMoreNoticeListSuccess(passengerVO);
                        }
                        if (passengerVO.size() < StaticExplain.PAGE_NUM.getCode()) {
                            //加载结束
                            mViewRef.get().loadMoreEnd();
                        } else {
                            //加载完成
                            mViewRef.get().loadMoreComplete();
                        }
                    }
                    @Override
                    public void onError(ApiException apiException) {
                        super.onError(apiException);
                        mViewRef.get().finishRefresh();
                        mViewRef.get().loadError();
                    }
                });
    }

    @Override
    public void getEscortInfo() {

    }

    @Override
    public void getNavigationList() {
        List<Navigation> navigationList = new ArrayList<>();
        Navigation navigation = new Navigation();
        navigation.setIcon(R.drawable.ic_personnel_control);
        navigationList.add(navigation);

        Navigation deviceStatus = new Navigation();
        deviceStatus.setIcon(R.drawable.ic_device_status);
        navigationList.add(deviceStatus);

        Navigation branchOperate = new Navigation();
        branchOperate.setIcon(R.drawable.ic_branch_operate);
        navigationList.add(branchOperate);

        Navigation venchie = new Navigation();
        venchie.setIcon(R.drawable.ic_venchie);
        navigationList.add(venchie);

        Navigation securityOperation = new Navigation();
        securityOperation.setIcon(R.drawable.ic_security_operation);
        navigationList.add(securityOperation);

        Navigation inOut = new Navigation();
        inOut.setIcon(R.drawable.ic_in_out);
        navigationList.add(inOut);

        Navigation rule = new Navigation();
        rule.setIcon(R.drawable.ic_rule);
        navigationList.add(rule);

        Navigation call = new Navigation();
        call.setIcon(R.drawable.ic_call);
        navigationList.add(call);
        mViewRef.get().setNavigationList(navigationList);
    }


}
