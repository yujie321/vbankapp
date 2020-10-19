package com.example.toollib.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.toollib.ToolLib;

/**
 * @author Administrator
 * @date 2019/6/24 0024
 */
public class Utils {

    public static <T> void checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
    }


    /**
     * @return 获取版本号
     */
    public static int getVersionCode(){
        try {
            PackageManager packageManager = ToolLib.getInstance().getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    ToolLib.getInstance().getContext().getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
