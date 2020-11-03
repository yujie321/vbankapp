package com.vieboo.vbankapp.utils;

import android.os.Environment;

import com.blankj.utilcode.util.SDCardUtils;

import java.io.File;

/**
 * 🙏 GOD BLESS NO BUG !!! 🙏
 * Author： vieboo
 * Date： 2020-01-10 15:34
 * Description：
 */
public class Constants {

    /**
     * 附件存储路径，包括apk升级包
     */
    public static String BASE_FILE_PATH = SDCardUtils.getSDCardPathByEnvironment() + File.separator + "store" + File.separator;
    public static String UPDATE_PATH = BASE_FILE_PATH + "update/store.apk";

    //授权路径
    public static String LICENSE_PATH = BASE_FILE_PATH + "ArcFacePro32.dat";

    public static final int PAGE_SIZE = 20;

    public static final String APP_ID = "57hpVQwbVUzBwqLDgANvQ9q984ztKgzsmcRTMmk6RQuQ"; //官网申请的APP_ID
    public static final String SDK_KEY = "6ojLuCp38B2j9pwx8N7twdDAdBbKFbcoqapmP3NggDC"; //官网申请的SDK_KEY

    //设备授权码
    //public static final String ACTIVE_KEY = "85T2-112T-DYR2-V7FJ";
    //1:N比对相似度
    public static final float FACE_MIN_SIMLAR = 0.3f;

    //人证比对相似度
    public static final float FACE_TO_ID_SIMILARITY = 0.4f;

    /**
     * Handle消息状态
     */
    public static final int MSG_READCARD_OK = 0X31;

    public static final int MSG_ERROR = -2;

    public static final int MSG_REFRESH = -3;

    public static final int MSG_REFRESH_ID = -4;

    /**
     * 视频画面宽度
     */
    public static final int CAMERA_PREVIEW_WIDTH = 800;
    /**
     * 视频画面高度
     */
    public static final int CAMERA_PREVIEW_HEIGHT = 600;

    /**
     * 默认的摄像头id
     */
    public static final int DEFAULT_CAMERA_ID = 1;

    public static String PRINTER_APK_NAME = "printer.apk";
    public static String PRINTER_PACKAGE_NAME = "com.dynamixsoftware.printershare";
    public static String PRINTER_PDF_NAME = "com.dynamixsoftware.printershare.ActivityPrintPDF";
    public static String PRINTER_APK_PATH = Environment.getExternalStorageDirectory().getPath() + "/store/base/" + PRINTER_APK_NAME;


    public static final int ACTION_REQUEST_PERMISSIONS = 0x001;
}
