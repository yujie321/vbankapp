package com.vieboo.vbankapp.application;

import android.app.Activity;
import android.app.Application;
import android.app.smdt.SmdtManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class LifecycleCallbacks implements Application.ActivityLifecycleCallbacks {


    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if(SmdtManager.create(activity) != null){
            SmdtManager.create(activity).smdtSetStatusBar(activity, false);
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
}
