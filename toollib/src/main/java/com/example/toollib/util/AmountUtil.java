package com.example.toollib.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 *  计算相关工具类
 */
public class AmountUtil {

    /***
     * Double类型相加
     * @param val1 1
     * @param val2 2
     * @param scale 保留scale位小数
     * @return Double
     */
    public static Double add(Double val1, Double val2, int scale) {
        if (null == val1) {
            val1 = Double.valueOf(String.valueOf(0));
        }
        if (null == val2) {
            val2 = Double.valueOf(String.valueOf(0));
        }
        return new BigDecimal(Double.toString(val1)).add(new BigDecimal(Double.toString(val2))).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /***
     * Double类型相减
     * @param val1 val1
     * @param val2 val2
     * @param scale 保留scale位小数
     * @return double
     */
    public static Double subtract(Double val1, Double val2, int scale) {
        if (null == val1) {
            val1 = Double.valueOf(String.valueOf(0));
        }
        if (null == val2) {
            val2 = Double.valueOf(String.valueOf(0));
        }
        return new BigDecimal(Double.toString(val1)).subtract(new BigDecimal(Double.toString(val2))).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 提供精确的除法运算方法div
     *
     * @param value1 被除数
     * @param value2 除数
     * @param scale  精确范围
     * @return 两个参数的商
     */
    public static double div(double value1, double value2, int scale) {
        //如果精确范围小于0，抛出异常信息
        if(value2 == 0){
            return 1;
        }
        if (scale < 0) {
            try {
                throw new IllegalAccessException("精确度不能小于0");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        //默认保留两位会有错误，这里设置保留小数点后4位
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的乘法运算方法div
     */
    public static double multiply(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        //默认保留两位会有错误，这里设置保留小数点后4位
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 计算百分比
     * @param d  double
     * @param d1 double
     * @return string
     */
    public static String percentage(Double d, Double d1) {
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format(d / d1 * 100);
    }


    /**
     * 保留 newScale 位小数
     * @param bigDecimal double
     * @param newScale int
     * @return double
     */
    public static double getDoubleValue(double bigDecimal , int newScale){
        BigDecimal bg = new BigDecimal(bigDecimal);
        return bg.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
