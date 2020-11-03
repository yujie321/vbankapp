package com.example.toollib.http;

import com.example.toollib.ToolLib;
import com.example.toollib.http.converter.MyConverterFactory;
import com.example.toollib.http.interceptor.BaseUrlInterceptor;
import com.example.toollib.http.interceptor.CacheInterceptor;
import com.example.toollib.http.interceptor.ReceivedCookiesInterceptor;
import com.example.toollib.http.interceptor.SaveCookiesInterceptor;
import com.example.toollib.util.Log;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Administrator
 * @date 2019/6/24 0024
 */
public class RetrofitUtils {

    private Retrofit retrofitBuild;
    private static RetrofitUtils mInstance;

    public static RetrofitUtils getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitUtils();
        }
        return mInstance;
    }

    public RetrofitUtils retrofit() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(ToolLib.getInstance().getConnectTimeOut(), TimeUnit.SECONDS)
                .writeTimeout(ToolLib.getInstance().getWriteTimeOut(), TimeUnit.SECONDS)
                .readTimeout(ToolLib.getInstance().getReadTimeOut(), TimeUnit.SECONDS);
        //okHttpBuilder.addInterceptor(new CacheInterceptor());
        //okHttpBuilder.addInterceptor(new ReceivedCookiesInterceptor());
        //okHttpBuilder.addInterceptor(new SaveCookiesInterceptor());
        okHttpBuilder.addInterceptor(new BaseUrlInterceptor());
        okHttpBuilder.addInterceptor(logInterceptor);
        retrofitBuild = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                .baseUrl(ToolLib.getInstance().getBaseUrl())
                .addConverterFactory(MyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return this;
    }

    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        if (retrofitBuild == null) {
            throw new RuntimeException("retrofit service is null");
        }
        return retrofitBuild.create(service);
    }

    public class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            Log.e(message);
        }
    }

}
