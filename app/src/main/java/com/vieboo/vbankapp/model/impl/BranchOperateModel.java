package com.vieboo.vbankapp.model.impl;

import com.example.toollib.data.BaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.observer.BaseHttpZipRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.vieboo.vbankapp.data.AddPersonalInitVO;
import com.vieboo.vbankapp.data.AuthVO;
import com.vieboo.vbankapp.data.DepartmentVO;
import com.vieboo.vbankapp.data.PassengerSummeryVo;
import com.vieboo.vbankapp.data.PositionsVO;
import com.vieboo.vbankapp.data.SecureRecordVo;
import com.vieboo.vbankapp.data.TodyPassengerVO;
import com.vieboo.vbankapp.http.ServiceUrl;
import com.vieboo.vbankapp.model.IBranchOperateModel;
import com.vieboo.vbankapp.model.IBranchOperateView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

public class BranchOperateModel extends BaseModule<IBranchOperateView> implements IBranchOperateModel {
    @Override
    public void getLast7dayPeriodStatic() {
        RxUtils.getObservable(ServiceUrl.getUserApi().last7dayPeriodStatic())
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<List<SecureRecordVo>>() {
                    @Override
                    protected void onSuccess(List<SecureRecordVo> secureRecordVoList) {
                        if(secureRecordVoList == null){
                            secureRecordVoList = new ArrayList<SecureRecordVo>();
                        }
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

        Observable<HttpResult<List<SecureRecordVo>>> todayPassengerStatic = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().todayPassengerStatic().subscribeOn(Schedulers.io()));

        Observable<HttpResult<List<SecureRecordVo>>> todayVipPassengerStatic = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().todayVipPassengerStatic().subscribeOn(Schedulers.io()));

        Observable<Object> objectObservable = Observable.zip(todayPassengerStatic, todayVipPassengerStatic, new BiFunction<HttpResult<List<SecureRecordVo>>, HttpResult<List<SecureRecordVo>>, Object>() {
            @Override
            public Object apply(HttpResult<List<SecureRecordVo>> todayPassengerStatic, HttpResult<List<SecureRecordVo>> todayVipPassengerStatic) throws Exception {
                TodyPassengerVO todyPassengerVO = new TodyPassengerVO();
                todyPassengerVO.setTodayPassengerStatic(todayPassengerStatic.getData());
                todyPassengerVO.setTodayVipPassengerStatic(todayVipPassengerStatic.getData());
                return todyPassengerVO;
            }
        }).compose(mViewRef.get().bindLifecycle());

        BaseHttpZipRxObserver instance = BaseHttpZipRxObserver.getInstance();
        instance.setContext(mContext.get());
        instance.httpZipObserver(objectObservable, new BaseCallback() {
            @Override
            public void onSuccess(Object object) {
                TodyPassengerVO todyPassengerVO = (TodyPassengerVO) object;
                mViewRef.get().setTodayPassenger(todyPassengerVO);
            }
        });
    }

    @Override
    public void getTodayPassengerSummery() {
        RxUtils.getObservable(ServiceUrl.getUserApi().todayPassengerSummery())
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<PassengerSummeryVo>() {
                    @Override
                    protected void onSuccess(PassengerSummeryVo passengerSummeryVo) {
                        if(passengerSummeryVo == null){
                            passengerSummeryVo = new PassengerSummeryVo();
                        }
                        mViewRef.get().setTodayPassengerSummery(passengerSummeryVo);
                    }
                    @Override
                    public void onError(ApiException apiException) {
                        super.onError(apiException);
                    }
                });
    }
}
