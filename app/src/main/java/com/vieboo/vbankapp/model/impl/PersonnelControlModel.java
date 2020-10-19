package com.vieboo.vbankapp.model.impl;

import com.example.toollib.data.BaseModule;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.PunchRecordVO;
import com.vieboo.vbankapp.data.RecordVO;
import com.vieboo.vbankapp.http.ServiceUrl;
import com.vieboo.vbankapp.model.IPersonnelControlModel;
import com.vieboo.vbankapp.model.IPersonnelControlView;

import java.util.List;

public class PersonnelControlModel extends BaseModule<IPersonnelControlView> implements IPersonnelControlModel {

    @Override
    public void getStaffStyle() {
        RxUtils.getObservable(ServiceUrl.getUserApi().findPerson(1 , 10000))
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<List<PunchRecordVO>>() {
                    @Override
                    protected void onSuccess(List<PunchRecordVO> passengerVO) {
                        mViewRef.get().setStaffStyle(passengerVO);
                    }
                });
    }

    @Override
    public void getPunchRecord() {
        RxUtils.getObservable(ServiceUrl.getUserApi().findPunchRecord())
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<List<RecordVO>>() {
                    @Override
                    protected void onSuccess(List<RecordVO> recordVOS) {
                        mViewRef.get().setPunchRecord(recordVOS);
                    }
                });
    }

    @Override
    public void clockIn() {
        String id = mViewRef.get().getPersonalId();
        RxUtils.getObservable(ServiceUrl.getUserApi().clockIn(id))
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        mViewRef.get().showToast(mContext.get().getResources().getString(R.string.clock_in_success));
                    }
                });
    }

    @Override
    public void clockOut() {
        String id = mViewRef.get().getPersonalId();
        RxUtils.getObservable(ServiceUrl.getUserApi().clockOut(id))
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        mViewRef.get().showToast(mContext.get().getResources().getString(R.string.clock_out_success));
                    }
                });
    }

}
