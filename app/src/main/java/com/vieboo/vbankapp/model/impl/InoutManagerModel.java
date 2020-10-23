package com.vieboo.vbankapp.model.impl;


import com.example.toollib.data.BaseModule;
import com.example.toollib.enums.StaticExplain;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.DateUtil;
import com.vieboo.vbankapp.data.InOutVO;
import com.vieboo.vbankapp.data.PersonInOutVO;
import com.vieboo.vbankapp.data.SecureResultVO;
import com.vieboo.vbankapp.data.SecureVO;
import com.vieboo.vbankapp.http.ServiceUrl;
import com.vieboo.vbankapp.model.IInoutManagerModel;
import com.vieboo.vbankapp.model.IInoutManagerView;

import java.util.List;

public class InoutManagerModel extends BaseModule<IInoutManagerView> implements IInoutManagerModel {
    @Override
    public void requestPersonInOut() {
        int page = mViewRef.get().getPage();
        String startTime = "1990-01-01 00:00:00";
        int timeTwentyFour = DateUtil.getTimeTwentyFour();
        String endTime = DateUtil.getDateToString(timeTwentyFour, "yyyy-MM-dd HH:mm:dd");
        RxUtils.getObservable(ServiceUrl.getUserApi().findPersonInOut(startTime, endTime, page, StaticExplain.PAGE_NUM.getCode()))
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<InOutVO>() {
                    @Override
                    protected void onSuccess(InOutVO response) {
                        List<PersonInOutVO> personInOutVOS = response.getData();
                        if (mViewRef.get().getPage() == StaticExplain.PAGE_NUMBER.getCode()) {
                            //刷新
                            mViewRef.get().refreshPersonInOut(personInOutVOS);
                            mViewRef.get().finishRefresh();
                        } else {
                            //加载
                            mViewRef.get().loadMorePersonInOut(personInOutVOS);
                        }
                        if (personInOutVOS.size() < StaticExplain.PAGE_NUM.getCode()) {
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
    public void requestAccessPersonInOut() {
        //String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
        //String startTime = date + " 00:00:00";
        int timeZero = DateUtil.getTimeZero();
        String startTime = DateUtil.getDateToString(timeZero, "yyyy-MM-dd HH:mm:dd");
        int timeTwentyFour = DateUtil.getTimeTwentyFour();
        String endTime = DateUtil.getDateToString(timeTwentyFour, "yyyy-MM-dd HH:mm:dd");
        RxUtils.getObservable(ServiceUrl.getUserApi().findPersonInOut(startTime, endTime, 1, 10000))
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<InOutVO>() {
                    @Override
                    protected void onSuccess(InOutVO response) {
                        List<PersonInOutVO> personInOutVOS = response.getData();
                        mViewRef.get().setAccessPersonInOut(personInOutVOS);
                    }
                });

    }
}