package com.vieboo.vbankapp.face.util;

import android.graphics.Bitmap;

import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceSimilar;
import com.example.toollib.util.Log;
import com.vieboo.vbankapp.utils.Constants;

import java.util.Date;

public class IdCardFaceListenerUtil extends FaceListenerUtil{

    private volatile byte[] idCardFeature;
    private boolean isProcessing = false;

    public IdCardFaceListenerUtil(FaceListenerView faceListenerView) {
        super(faceListenerView);
    }

    @Override
    public String checkingFace(FaceFeature faceFeature, Bitmap bitmap) {
        if (isProcessing || idCardFeature == null) {
            return "";
        }
        isProcessing = true;
        String personId = "";
        Log.e("开始进行人脸对比，对比库数量:"  + "--" + new Date().getTime());
        float maxSimilar = 0f;
        FaceSimilar faceSimilar = compareVideoSimilar(faceFeature.getFeatureData(), idCardFeature);
        if (faceSimilar != null) {
            if (faceSimilar.getScore() >= Constants.FACE_TO_ID_SIMILARITY && faceSimilar.getScore() > maxSimilar) {
                maxSimilar = faceSimilar.getScore();
                faceListenerView.callback(faceFeature, bitmap, personId);
            }
            Log.e("checkingFace:姓名:"  + "-相似度:" + faceSimilar.getScore());
        }
        isProcessing = false;
        return personId;
    }

    public void setIdCardFeature(byte[] idCardFeature) {
        this.idCardFeature = idCardFeature;
    }
}
