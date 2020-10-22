package com.vieboo.vbankapp.face.util;


import android.util.Base64;

public class Base64Utils {

    //base64字符串转byte[]
    public static byte[] base64String2ByteFun(String base64Str) {
        return Base64.decode(base64Str, Base64.DEFAULT);
    }

    //byte[]转base64
    public static String byte2Base64StringFun(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
