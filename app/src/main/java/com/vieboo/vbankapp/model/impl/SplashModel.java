package com.vieboo.vbankapp.model.impl;

import android.Manifest;
import android.app.Activity;

import androidx.core.app.ActivityCompat;

import com.arcsoft.face.ActiveFileInfo;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.example.toollib.data.BaseModule;
import com.example.toollib.util.Log;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.model.ISplashModel;
import com.vieboo.vbankapp.model.ISplashView;
import com.vieboo.vbankapp.utils.Constants;
import com.vieboo.vbankapp.utils.FaceAlgoUtils;
import com.vieboo.vbankapp.utils.PermissionUtil;

public class SplashModel extends BaseModule<ISplashView> implements ISplashModel {

    // 在线激活所需的权限
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    // 离线激活所需的权限
    private static final String[] NEEDED_PERMISSIONS_OFFLINE = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    public void activeEngine() {
        boolean isLibraryExists = mViewRef.get().getLibraryExists();
        if (!isLibraryExists) {
            mViewRef.get().showToast(mContext.get().getString(R.string.library_not_found));
        } else if (!PermissionUtil.checkPermissions(mContext.get(), NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions((Activity) mContext.get(), NEEDED_PERMISSIONS, Constants.ACTION_REQUEST_PERMISSIONS);
        } else {
            ActiveFileInfo activeFileInfo = new ActiveFileInfo();
            int code = FaceEngine.getActiveFileInfo(mContext.get(), activeFileInfo);
            if (code == ErrorInfo.MOK) {
                //保存激活码到SD卡
                FaceAlgoUtils.copyLicenseCodeToCard(mContext.get());
                Log.e("已激活 设备授权码 : " + activeFileInfo.getActiveKey());
                mViewRef.get().startHomeFragment();
            } else {
                //未激活,则先从本地SD卡加载，如果没有，则线上激活
                Log.e("getActiveFileInfo failed, code is : " + code);
                boolean isExist = FaceAlgoUtils.copyLicenseCodeToActiveFile(mContext.get());
                if (!isExist) {
                    mViewRef.get().showToast("激活失败 : " + code);
                    mViewRef.get().startActiveDeviceFragment();
                } else {
                    mViewRef.get().showToast("设备授权码：" + activeFileInfo.getActiveKey());
                }
            }
        }

    }
}
