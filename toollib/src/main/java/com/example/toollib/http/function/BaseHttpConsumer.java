package com.example.toollib.http.function;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.exception.ExceptionHandle;
import com.example.toollib.http.exception.HttpError;
import com.example.toollib.util.DensityUtils;
import com.example.toollib.util.LoginInterceptor;

import io.reactivex.functions.Consumer;

/**
 * @author Administrator
 * @date 2019/7/12 0012
 */
public abstract class BaseHttpConsumer<T> implements Consumer<HttpResult<T>> {

    @Override
    public void accept(HttpResult<T> httpResult) throws Exception {
        if (Integer.parseInt(httpResult.getCode()) != HttpError.HTTP_SUCCESS.getCode()) {
            LoginInterceptor.tokenReLogin(new ApiException(DensityUtils.stringTypeInteger(httpResult.getCode()),
                    httpResult.getMsg(), httpResult.getData().toString()));
            throw ExceptionHandle.handleException(new ApiException(DensityUtils.stringTypeInteger(httpResult.getCode())
                    ,httpResult.getMsg(),httpResult.getData().toString()));
        }else{
            httpConsumerAccept(httpResult);
        }
    }

    /**
     *  accept
     * @param httpResult httpResult
     */
    public abstract void httpConsumerAccept(HttpResult<T> httpResult);

}
