package com.example.toollib.data;

import android.content.Context;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 */
public interface IBaseModule<V> {
    /**
     * 弱引用
     *
     * @param view     view
     * @param mContext context
     */
    void attachView(V view, Context mContext);

}
