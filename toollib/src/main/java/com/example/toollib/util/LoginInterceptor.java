package com.example.toollib.util;

import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.version.MessageEvent;
import com.example.toollib.http.version.Version;
import com.example.toollib.http.version.VersionEnums;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Administrator
 * @date 2019/8/6 0006
 *
 * 登录拦截
 */
public class LoginInterceptor {

    /**
     * token 过期 或 重复登录
     */
    public static boolean tokenReLogin(ApiException apiException){
        boolean flag = apiException.getStatus() == VersionEnums.LOGIN_STATUS.getCode()
                || apiException.getStatus() == VersionEnums.LOGIN_STATUS_.getCode()
                || apiException.getStatus() == VersionEnums.LOGIN_OTHER_STATUS.getCode();
        if (flag) {
            EventBus.getDefault().post(new MessageEvent(apiException.getStatus() , new Version()));
        }
        return flag;
    }
}
