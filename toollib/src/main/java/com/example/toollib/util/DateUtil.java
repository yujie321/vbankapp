package com.example.toollib.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 */
public class DateUtil {

    @SuppressLint("SimpleDateFormat")
    public static String getDate(String oldDate, String formatStr) {
        if (TextUtils.isEmpty(oldDate)) {
            return "";
        }
        Date date1 = null;
        DateFormat df2 = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df.parse(oldDate);
            SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat(formatStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (df2 != null) {
            return df2.format(date1);
        }
        return "";
    }

    public static String dateToStr(String date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.UK);
        try {
            Date parse = simpleDateFormat.parse(date);
            return simpleDateFormat.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date strToDate_(String date, String pattern) {
        Date parse = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.UK);
        try {
            parse = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    public static String getDateToString(long milSecond, String pattern) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static Long dateToCurrentTimeMillis(String date , String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.UK);
            Date parse = simpleDateFormat.parse(date);
            return parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * @param time    1541569323155
     * @param pattern yyyy-MM-dd HH:mm:ss
     * @return 2018-11-07 13:42:03
     */
    public static String getDate2String(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(date);
    }

    /**
     * Date类型转为指定格式的String类型
     */
    public static String dateToString(Date source, String pattern) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(source);
    }

    /**
     * 将 时间 转换 成指定格式
     * @param date 时间
     * @param pattern 时间格式
     * @param formatPattern 转换的指定格式
     * @return string
     */
    public static String dateToFormat(String date , String pattern , String formatPattern){
        try {
            Date date1 = strToDate_(date, pattern);
            return dateToString(date1, formatPattern);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 年份
     */
    public static Integer getYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    /**
     * 月份
     */
    public static Integer getMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH);
    }

    /**
     * 几号
     */
    public static Integer getDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取 hours小时后的时间
     *
     * @param hours     小时
     * @param formatStr 时间格式
     * @return string
     */
    public static Calendar getHours(int hours, String formatStr) {
        DateTime dt = new DateTime();
        DateTime y = dt.plusHours(hours);
        String dateStr = y.toString(formatStr);
        DateTime date = strToDate(dateStr, formatStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date.toDate());
        return calendar;
    }

    /**
     * 获取 month 个月后的时间
     *
     * @param month     month 个月后的时间
     * @param formatStr 时间格式
     * @return string
     */
    public static Calendar getMonth(int month, String formatStr) {
        DateTime dt = new DateTime();
        DateTime y = dt.plusMonths(month);
        String dateStr = y.toString(formatStr);
        DateTime date = strToDate(dateStr, formatStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date.toDate());
        return calendar;
    }

    /***
     * 将string字符串转化为Date类型的字符串
     * @param dateTimeStr 需要转化的string类型的字符串
     * @param formatStr 转化规则
     * @return 返回转化后的Date类型的时间
     */
    public static DateTime strToDate(String dateTimeStr, String formatStr) {
        try {
            if (TextUtils.isEmpty(dateTimeStr) || TextUtils.isEmpty(formatStr)){
                return new DateTime();
            }
            //根据时间表达式生成DateTimeFormatter对象
            DateTimeFormatter fmt = DateTimeFormat.forPattern(formatStr);
            //2019-10-28T10:23:12.000+08:00
            return fmt.parseDateTime(dateTimeStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new DateTime();
    }


    public static Long dateToCurrentTimeMilli(String date , String pattern){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.UK);
            Date parse = simpleDateFormat.parse(date);
            return parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static boolean isDateCompare(Long appointmentTime) {
        long l = System.currentTimeMillis();
        String date2String = DateUtil.getDate2String(l, "yyyy-MM-dd HH");
        Long aLong = DateUtil.dateToCurrentTimeMilli(date2String, "yyyy-MM-dd HH");
        return appointmentTime - aLong >= 7200000;
    }

    public static String getWeek(String slottingEndLength) {
        if (slottingEndLength.equals("1")) {
            return "一";
        } else if (slottingEndLength.equals("2")) {
            return "二";
        } else if (slottingEndLength.equals("3")) {
            return "三";
        } else if (slottingEndLength.equals("4")) {
            return "四";
        } else if (slottingEndLength.equals("5")) {
            return "五";
        } else if (slottingEndLength.equals("6")) {
            return "六";
        } else if (slottingEndLength.equals("7")) {
            return "七";
        }
        return "";
    }

    public static int getTimeZero(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static int getTimeTwentyFour(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis()/1000);
    }
}
