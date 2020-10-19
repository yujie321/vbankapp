package com.example.toollib.http.interceptor;


import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.toollib.util.Log;
import com.example.toollib.util.spf.SPUtils;
import com.example.toollib.util.spf.SpfConst;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Administrator
 * @date 2019/5/7 0007
 */
public class ReceivedCookiesInterceptor implements Interceptor {

    private static final String TOKEN_KEY = "Authorization";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String url = request.url().uri().toString();
        if (((url.indexOf("userLogin/login")) != -1)){
            String cookie = response.headers().get("Set-Cookie");
            if (cookie != null){
                Log.e("保存 cookie = " + cookie);
                SPUtils.getInstance().put("cookie" , cookie);
            }
        }
        String token = response.header(TOKEN_KEY);
        if (!TextUtils.isEmpty(token)) {
            assert token != null;
            SPUtils.getInstance().put(SpfConst.TOKEN , token);
        }
        return response;
    }
}
