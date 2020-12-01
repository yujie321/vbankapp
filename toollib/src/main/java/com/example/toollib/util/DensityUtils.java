package com.example.toollib.util;

import android.content.Context;
import android.view.WindowManager;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public class DensityUtils {

    public static int stringTypeInteger(String character) {
        int i = 0;
        try {
            return Integer.parseInt(character);
        } catch (Exception e) {
            return i;
        }
    }

    public static Long stringTypeLong(String character) {
        long i = 0;
        try {
            return Long.parseLong(character);
        } catch (Exception e) {
            return i;
        }
    }

    public static double stringTypeDouble(String character) {
        double i = 0;
        try {
            return Double.parseDouble(character);
        } catch (Exception e) {
            return i;
        }
    }

    public static float stringTypeFloat(String character) {
        float i = 0;
        try {
            return Float.parseFloat(character);
        } catch (Exception e) {
            return i;
        }
    }


    public static int getPhoneHeight(Context mContext){
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }


}
