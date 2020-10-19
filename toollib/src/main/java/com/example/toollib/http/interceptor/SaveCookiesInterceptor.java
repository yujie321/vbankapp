package com.example.toollib.http.interceptor;



import androidx.annotation.NonNull;

import com.example.toollib.util.Log;
import com.example.toollib.util.Utils;
import com.example.toollib.util.spf.SPUtils;
import com.example.toollib.util.spf.SpfConst;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * @author Administrator
 * @date 2019/5/7 0007
 *
 * 头部token 保存
 */
public class SaveCookiesInterceptor implements Interceptor {


    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder()
                .addHeader("Accept-Language", "zh-CN");
        String authorization = SPUtils.getInstance().getString(SpfConst.TOKEN);
        Log.e("拦截器 authorization = " + authorization);
        request.addHeader("Authorization" , authorization);
        String cookie = SPUtils.getInstance().getString("cookie");
        Log.e("读取cookie = " + cookie);
        request.addHeader("Cookie" , cookie);
        //版本号
        request.addHeader("VersionNumber" , String.valueOf(Utils.getVersionCode()));
        //Android
        request.addHeader("PlatformSource" , "1");
        request.addHeader("SessionId" , cookie);

        return chain.proceed(request.build());
    }



}
