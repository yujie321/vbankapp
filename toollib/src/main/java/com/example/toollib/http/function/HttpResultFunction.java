package com.example.toollib.http.function;


import com.example.toollib.http.exception.ExceptionHandle;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @author Administrator
 * @date 2019/6/25 0025
 */
public class HttpResultFunction<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        //打印具体错误
        return Observable.error(ExceptionHandle.handleException(throwable));
    }
}
