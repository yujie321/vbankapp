package com.example.toollib.http.observer;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.exception.ExceptionHandle;
import com.example.toollib.http.function.HttpResultFunction;
import com.example.toollib.http.util.DialogUtil;
import com.example.toollib.http.version.MessageEvent;
import com.example.toollib.http.version.Version;
import com.example.toollib.http.version.VersionEnums;
import com.example.toollib.util.LoginInterceptor;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 * <p>
 * 并发
 */
public class BaseHttpZipRxObserver {


    private WeakReference<Context> mContext;

    public static BaseHttpZipRxObserver getInstance() {
        return new BaseHttpZipRxObserver();
    }

    public void setContext(Context mContext) {
        this.mContext = new WeakReference<>(mContext);
    }

    @SuppressLint("CheckResult")
    public void httpZipObserver(Observable<Object> zip, final BaseCallback baseCallback) {
        if (mContext != null) {
            DialogUtil.getTipLoading(mContext.get(), "").show();
        }
        zip.onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) {
                        if (mContext != null) {
                            DialogUtil.tipLoadingDismiss();
                        }
                        baseCallback.onSuccess(object);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mContext != null) {
                            DialogUtil.tipLoadingDismiss();
                        }
                        ApiException apiException = ExceptionHandle.handleException(throwable);
                        String msg = apiException.getMsg();

                        boolean flag = LoginInterceptor.tokenReLogin(apiException);
                        if (!flag){
                            baseCallback.onFail(msg);
                        }

                    }
                });
    }

}
