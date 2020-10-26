package com.vieboo.vbankapp.model.impl;

import com.example.toollib.data.BaseModule;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.vieboo.vbankapp.data.SecureRecordVo;
import com.vieboo.vbankapp.http.ServiceUrl;
import com.vieboo.vbankapp.model.IBranchOperateModel;
import com.vieboo.vbankapp.model.IBranchOperateView;

import java.util.List;

public class BranchOperateModel extends BaseModule<IBranchOperateView> implements IBranchOperateModel {
    @Override
    public void getLast7dayPeriodStatic() {
        RxUtils.getObservable(ServiceUrl.getUserApi().last7dayPeriodStatic())
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<List<SecureRecordVo>>() {
                    @Override
                    protected void onSuccess(List<SecureRecordVo> secureRecordVoList) {
                        mViewRef.get().setLast7dayPeriodStatic(secureRecordVoList);
                    }
                    @Override
                    public void onError(ApiException apiException) {
                        super.onError(apiException);
                    }
                });
    }

    @Override
    public void getTodayPassengerStatic() {
        RxUtils.getObservable(ServiceUrl.getUserApi().todayPassengerStatic())
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<List<SecureRecordVo>>() {
                    @Override
                    protected void onSuccess(List<SecureRecordVo> secureRecordVoList) {
                        mViewRef.get().setTodayPassengerStatic(secureRecordVoList);
                    }
                    @Override
                    public void onError(ApiException apiException) {
                        super.onError(apiException);
                    }
                });
    }
}
