package com.example.toollib.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class DensityUtil {
    public float density;

    public DensityUtil() {
        density = Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param dpValue 虚拟像素
     * @return 像素
     */
    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * @param pxValue 像素
     * @return 虚拟像素
     */
    public static float px2dp(int pxValue) {
        return (pxValue / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param dpValue 虚拟像素
     * @return 像素
     */
    public int dip2px(float dpValue) {
        return (int) (0.5f + dpValue * density);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * @param pxValue 像素
     * @return 虚拟像素
     */
    public float px2dip(int pxValue) {
        return (pxValue / density);
    }

    /**
     * dp转px
     * @param context ctx
     * @param dp dp
     * @return int
     */
    public static int dip2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * px转dp
     * @param context ctx
     * @param px px
     * @return int
     */
    public static int px2dip(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    /**
     *获取屏幕大小（px）
     * @param context context
     * @return getDisplayMetrics
     */
    public static DisplayMetrics getScreenSize(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * 保留两位小数
     * @param number double
     * @return string
     */
    public static String getDecimalFormat(double number){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(number);
    }
}
