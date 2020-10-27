package com.vieboo.vbankapp.model.impl;

import android.widget.EditText;

import com.example.toollib.data.BaseModule;
import com.example.toollib.enums.StaticExplain;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.Log;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.vieboo.vbankapp.data.RegulationsVO;
import com.vieboo.vbankapp.http.ServiceUrl;
import com.vieboo.vbankapp.model.IRegulationsModel;
import com.vieboo.vbankapp.model.IRegulationsView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegulationsModel extends BaseModule<IRegulationsView> implements IRegulationsModel {

    @Override
    public void initData() {
        EditText searchView = mViewRef.get().getEditText();
        RxTextView.textChanges(searchView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                // 进行过滤操作
                .filter(charSequence -> true)
                // 从网络获取搜索字符串数据，switchMap比较flatMap，因为switchMap会发送最近的数据，
                .switchMap((Function<CharSequence, ObservableSource<String>>) charSequence -> Observable.just(charSequence.toString()))
                // 因为去网络搜索是耗时操作，所以需要切换在子线程中
                .subscribeOn(Schedulers.io())
                // 搜索结果的将会在ui界面上展示，所以切换到ui线程
                .observeOn(AndroidSchedulers.mainThread())
                // 绑定观察者接受信息
                .subscribe(strings -> {
                    findRegulations();
                    // 获取搜索结果
                }, throwable -> Log.e(throwable.getMessage()));
    }

    @Override
    public void findRegulations() {
        EditText editText = mViewRef.get().getEditText();
        String searchText = editText.getText().toString();
        RxUtils.getObservable(ServiceUrl.getUserApi().findRegulations(searchText, mViewRef.get().getPage(), StaticExplain.PAGE_NUM.getCode()))
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<List<RegulationsVO>>() {
                    @Override
                    protected void onSuccess(List<RegulationsVO> regulationsVOList) {
                        if (mViewRef.get().getPage() == StaticExplain.PAGE_NUMBER.getCode()) {
                            //刷新
                            mViewRef.get().refreshRegulationsSuccess(regulationsVOList);
                            mViewRef.get().finishRefresh();
                        } else {
                            //加载
                            mViewRef.get().loadMoreRegulationsSuccess(regulationsVOList);
                        }
                        if (regulationsVOList.size() < StaticExplain.PAGE_NUM.getCode()) {
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
