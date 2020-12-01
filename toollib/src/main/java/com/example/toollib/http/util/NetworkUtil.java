package com.example.toollib.http.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.toollib.ToolLib;

/**
 * @author Administrator
 * @date 2019/6/26 0026
 */
public class NetworkUtil {

    /**
     * check NetworkAvailable
     * @return boolean
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) ToolLib.getInstance().getContext().getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if (null == manager){
            return false;
        }
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (null == info || !info.isAvailable()){
            return false;
        }
        return true;
    }

}
