package com.vieboo.vbankapp.model.impl;

import android.widget.EditText;

import com.example.toollib.data.BaseModule;
import com.example.toollib.enums.StaticExplain;
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
                .filter(charSequence -> true)
                .switchMap((Function<CharSequence, ObservableSource<String>>) charSequence -> Observable.just(charSequence.toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(strings -> {
                    mViewRef.get().setPage(StaticExplain.PAGE_NUMBER.getCode());
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
