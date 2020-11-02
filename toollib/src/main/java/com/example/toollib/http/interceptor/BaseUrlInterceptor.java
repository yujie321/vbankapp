package com.example.toollib.http.interceptor;

import android.text.TextUtils;

import com.example.toollib.ToolLib;
import com.example.toollib.util.spf.SPUtils;
import com.example.toollib.util.spf.SpfConst;

import java.io.IOException;

import io.reactivex.rxjava3.annotations.NonNull;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BaseUrlInterceptor implements Interceptor {

    private String mBaseUrl;
    private String mNewBaseUrl;
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request oldRequest = chain.request();
        HttpUrl requestUrl = oldRequest.url();
        String oldUrl = requestUrl.toString();

        mBaseUrl = ToolLib.getInstance().getBaseUrl();
        mNewBaseUrl = SPUtils.getInstance().getString(SpfConst.BASE_URL, ToolLib.getInstance().getBaseUrl());

        if (TextUtils.isEmpty(mNewBaseUrl)
                || TextUtils.equals(mBaseUrl, mNewBaseUrl)) {
            return chain.proceed(chain.request());
        }

        String newUrl = mNewBaseUrl + oldUrl.substring(mBaseUrl.length());
        Request newRequest = oldRequest
                .newBuilder()
                .url(newUrl)
                .build();
        return chain.proceed(newRequest);
    }
}
