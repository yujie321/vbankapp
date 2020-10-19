package com.example.toollib.data;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 */
public class BaseModule<V> implements IBaseModule<V>{

    /**
     * 持有UI接口的弱引用
     */
    public WeakReference<V> mViewRef;
    public WeakReference<Context> mContext;


    @Override
    public void attachView(V view , Context mContext) {
        mViewRef = new WeakReference<>(view);
        this.mContext = new WeakReference<>(mContext);
    }
}
