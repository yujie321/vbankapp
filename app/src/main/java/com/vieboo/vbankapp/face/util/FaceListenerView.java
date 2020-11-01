package com.vieboo.vbankapp.face.util;

import android.graphics.Bitmap;

public interface FaceListenerView {
    void callback(Bitmap bitmap, String personId);
}
