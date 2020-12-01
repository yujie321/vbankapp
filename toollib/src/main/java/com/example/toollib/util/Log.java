package com.example.toollib.util;

import java.util.Locale;

/**
 * @author Administrator
 * @date 2019/6/25 0025
 * log 日志
 */
public class Log {

    public static String getTag() {
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String callingClass = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(Log.class)) {
                callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                break;
            }
        }
        return callingClass;
    }

    private static String buildMessage(String message) {
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String caller = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (clazz.equals(Log.class)) {
                caller = trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s : %s", Thread.currentThread().getId(),
                caller, message);
    }

    private static final boolean LOG_V = true;
    private static final boolean LOG_D = true;
    private static final boolean LOG_I = true;
    private static final boolean LOG_W = true;
    private static final boolean LOG_E = true;

    public static void v(String message) {
        if (LOG_V) {
            android.util.Log.v(getTag(), buildMessage(message));
        }
    }

    public static void d(String message) {
        if (LOG_D) {
            android.util.Log.d(getTag(), buildMessage(message));
        }
    }

    public static void w(String message) {
        if (LOG_W) {
            android.util.Log.w(getTag(), buildMessage(message));
        }
    }

    public static void i(String message) {
        if (LOG_I) {
            android.util.Log.i(getTag(), buildMessage(message));
        }
    }

    public static void e(String message) {
        if (LOG_E) {
            android.util.Log.e(getTag(), buildMessage(message));
        }
    }
}
