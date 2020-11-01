package com.vieboo.vbankapp.model.impl;

import com.example.toollib.data.BaseModule;
import com.example.toollib.enums.StaticExplain;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.vieboo.vbankapp.data.DeviceStatusVo;
import com.vieboo.vbankapp.data.VQDResultVO;
import com.vieboo.vbankapp.data.VQDSystemRecordVo;
import com.vieboo.vbankapp.http.ServiceUrl;
import com.vieboo.vbankapp.model.IDeviceStatusModel;
import com.vieboo.vbankapp.model.IDeviceStatusView;

import java.util.List;

public class DeviceStatusModel extends BaseModule<IDeviceStatusView> implements IDeviceStatusModel {
    @Override
    public void requestVQD() {
        int page = mViewRef.get().getPage();
        RxUtils.getObservable(ServiceUrl.getUserApi().findVQD(page, StaticExplain.PAGE_NUM.getCode()))
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<VQDSystemRecordVo>() {
                    @Override
                    protected void onSuccess(VQDSystemRecordVo vqdSystemRecordVo) {
                        if (vqdSystemRecordVo == null) {
                            vqdSystemRecordVo = new VQDSystemRecordVo();
                        }
                        List<VQDResultVO> vqdResultVOS = vqdSystemRecordVo.getData();
                        if (mViewRef.get().getPage() == StaticExplain.PAGE_NUMBER.getCode()) {
                            //刷新
                            mViewRef.get().refreshVQD(vqdResultVOS);
                            mViewRef.get().finishRefresh();
                        } else {
                            //加载
                            mViewRef.get().loadMoreVQD(vqdResultVOS);
                        }
                        if (vqdResultVOS.size() < StaticExplain.PAGE_NUM.getCode()) {
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
    public void getDeviceStatus() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getVqdStatic())
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<DeviceStatusVo>() {
                    @Override
                    protected void onSuccess(DeviceStatusVo deviceStatusVo) {
                        mViewRef.get().setDeviceStatus(deviceStatusVo);
                    }
                });

    }
}
