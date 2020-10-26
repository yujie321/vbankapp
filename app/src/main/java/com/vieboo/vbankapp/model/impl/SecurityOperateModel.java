package com.vieboo.vbankapp.model.impl;

import com.example.toollib.data.BaseModule;
import com.example.toollib.enums.StaticExplain;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.vieboo.vbankapp.data.SecureRecordVo;
import com.vieboo.vbankapp.data.SecureResultVO;
import com.vieboo.vbankapp.data.SecureVO;
import com.vieboo.vbankapp.http.ServiceUrl;
import com.vieboo.vbankapp.model.ISecurityOperateModel;
import com.vieboo.vbankapp.model.ISecurityOperateView;

import java.util.List;
import java.util.ListIterator;

public class SecurityOperateModel extends BaseModule<ISecurityOperateView> implements ISecurityOperateModel {

    @Override
    public void requestSecure() {
        int page = mViewRef.get().getPage();
        RxUtils.getObservable(ServiceUrl.getUserApi().findSecure(page , StaticExplain.PAGE_NUM.getCode()))
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<SecureVO>() {
                    @Override
                    protected void onSuccess(SecureVO secureVO) {
                        List<SecureResultVO> secureResultVOS = secureVO.getData();
                        if (mViewRef.get().getPage() == StaticExplain.PAGE_NUMBER.getCode()) {
                            //刷新
                            mViewRef.get().refreshSecure(secureResultVOS);
                            mViewRef.get().finishRefresh();
                        } else {
                            //加载
                            mViewRef.get().loadMoreSecure(secureResultVOS);
                        }
                        if (secureResultVOS.size() < StaticExplain.PAGE_NUM.getCode()) {
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
    public void getTodaySecureStatic() {
        RxUtils.getObservable(ServiceUrl.getUserApi().todaySecureStatic())
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<List<SecureRecordVo>>() {
                    @Override
                    protected void onSuccess(List<SecureRecordVo> secureRecordVoList) {
                        mViewRef.get().setSecureStatic(secureRecordVoList);
                    }
                    @Override
                    public void onError(ApiException apiException) {
                        super.onError(apiException);
                    }
                });
    }

}
