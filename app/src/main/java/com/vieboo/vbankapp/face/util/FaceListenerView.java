package com.vieboo.vbankapp.face.util;

import android.graphics.Bitmap;

import com.arcsoft.face.FaceFeature;

public interface FaceListenerView {
    void callback(FaceFeature faceFeature, Bitmap bitmap, String personId);
}
