package com.vieboo.vbankapp.application;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.example.toollib.ToolLib;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.usc.tts.TTSPlayer;
import com.vieboo.vbankapp.service.FaceServer;

public class VBankAppApplication extends Application {

    private static VBankAppApplication vBankAppApplication;


//    public static final String BASE_URL = "http://192.168.3.2:8082/";
//    public static final String BASE_URL = "http://192.168.1.39:8082/rest/";
    public static final String BASE_URL = "http://192.168.1.254:8286/";

    public static VBankAppApplication getInstance() {
        return vBankAppApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        vBankAppApplication = this;

        QMUISwipeBackActivityManager.init(vBankAppApplication);

        ToolLib.getInstance().init(vBankAppApplication)
                .setBaseUrl(BASE_URL);

        registerActivityLifecycleCallbacks(new LifecycleCallbacks());

        FaceServer.getInstance().initEngine(vBankAppApplication);

        TTSPlayer.getInstance().initTts(vBankAppApplication);

        initDownLoad();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initDownLoad() {
        FileDownloader.setupOnApplicationOnCreate(vBankAppApplication)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(60)
                        .readTimeout(60)))
                .commit();
    }

}
