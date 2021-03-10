package com.vieboo.vbankapp.model.impl;

import com.example.toollib.data.BaseModule;
import com.example.toollib.enums.StaticExplain;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.vieboo.vbankapp.data.InOutVO;
import com.vieboo.vbankapp.data.NoticeListVO;
import com.vieboo.vbankapp.data.PersonInOutVO;
import com.vieboo.vbankapp.data.RecordPlan;
import com.vieboo.vbankapp.http.ServiceUrl;
import com.vieboo.vbankapp.model.IRecordManagerModel;
import com.vieboo.vbankapp.model.IRecordManagerView;

import java.util.ArrayList;
import java.util.List;

public class RecordManagerModel extends BaseModule<IRecordManagerView> implements IRecordManagerModel  {
    @Override
    public void requestRecordPlan() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getRecordPlans("", mViewRef.get().getPage(), StaticExplain.PAGE_NUM.getCode()))
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<List<RecordPlan>>() {
                    @Override
                    protected void onSuccess(List<RecordPlan> recordPlans) {
                        if(recordPlans == null){
                            recordPlans = new ArrayList<RecordPlan>();
                        }
                        if (mViewRef.get().getPage() == StaticExplain.PAGE_NUMBER.getCode()) {
                            //刷新
                            mViewRef.get().refreshRecordPlan(recordPlans);
                            mViewRef.get().finishRefresh();
                        } else {
                            //加载
                            mViewRef.get().loadMoreRecordPlan(recordPlans);
                        }
                        if (recordPlans.size() < StaticExplain.PAGE_NUM.getCode()) {
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
}
