package com.vieboo.vbankapp.http;


import com.example.toollib.http.RetrofitUtils;

public class ServiceUrl {

    private static IApi apiUtils;

    public static IApi getUserApi() {
        if (apiUtils == null) {
            apiUtils = RetrofitUtils.getInstance().retrofit().create(IApi.class);
        }
        return apiUtils;
    }

}
