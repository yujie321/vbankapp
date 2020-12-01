package com.example.toollib.data.base;

import io.reactivex.ObservableTransformer;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 */
public interface IBaseView {

    /**
     * toast
     * @param message msg
     */
    void showToast(String message);


    /**
     * 用来 绑定view 生命周期，解决rxJava内存泄露
     * @return observableTransformer
     */
    <T> ObservableTransformer<T, T> bindLifecycle();

}
