package com.example.toollib.data.base;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 */
public abstract class BaseCallback<T> {
    /**
     * 成功回调
     *
     * @param obj T
     */
    public abstract void onSuccess(T obj);

    /**
     * 错误回调
     * @param obj 错误处理
     */
    public void onFail(Object obj) {
    }
}
