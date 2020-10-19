package com.example.toollib.http.manager;


import androidx.collection.ArrayMap;

import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * @author Administrator
 * @date 2019/6/25 0025
 */
public class RxActionManager {


    private static RxActionManager mInstance;
    private ArrayMap<String, Disposable> mMaps = new ArrayMap<>();

    public static RxActionManager getInstance() {
        if (mInstance == null) {
            mInstance = new RxActionManager();
        }
        return mInstance;
    }

    /**
     * @param tag        tag
     * @param dispatcher dispatcher
     */
    public void put(String tag, Disposable dispatcher) {
        mMaps.put(tag, dispatcher);
    }

    /**
     * 是否取消了请求
     *
     * @param tag 请求tag
     * @return true 取消 false 没有取消
     */
    public boolean isDisposed(Object tag) {
        Disposable disposable = mMaps.get(tag);
        return disposable != null && disposable.isDisposed();
    }

    /**
     * 取消订阅
     */
    public void cancel() {
        for (Map.Entry<String, Disposable> item : mMaps.entrySet()) {
            Disposable disposable = item.getValue();
            if (disposable != null && !disposable.isDisposed()) {
                //取消订阅
                disposable.dispose();
                mMaps.remove(disposable);
            }
        }
    }

}
