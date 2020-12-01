package com.example.toollib.http.util;

import com.example.toollib.http.function.HttpResultFunction;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Administrator
 * @date 2019/6/25 0025
 */
public class RxUtils {
    /**
     * @param httpResultObservable httpResultObservable
     */
    public static <T> Observable<T> getObservable(Observable<T> httpResultObservable) {
        return httpResultObservable.delaySubscription(1, TimeUnit.SECONDS)
                .onErrorResumeNext(new HttpResultFunction<T>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /** 链式请求 */
    public static <T> Observable<T> getObservableJust(Observable<T> httpResultObservable) {
        return httpResultObservable.delaySubscription(1, TimeUnit.SECONDS)
                .onErrorResumeNext(new HttpResultFunction<T>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 并发请求
     *
     * @param zip zip
     * @return observable
     */
    public static <T> Observable<T> getObservableZip(Observable<T> zip) {
        return zip.onErrorResumeNext(new HttpResultFunction<T>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
