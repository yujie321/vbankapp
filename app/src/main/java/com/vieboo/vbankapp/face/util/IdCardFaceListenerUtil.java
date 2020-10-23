package com.vieboo.vbankapp.face.util;

import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceSimilar;
import com.example.toollib.util.Log;
import com.vieboo.vbankapp.utils.Constants;

import java.util.Date;

public class IdCardFaceListenerUtil extends FaceListenerUtil{


    private volatile byte[] idCardFeature;
    private boolean isProcessing = false;

    private IdCardFaceView idCardFaceView;
    public IdCardFaceListenerUtil(IdCardFaceView idCardFaceView) {
        this.idCardFaceView = idCardFaceView;
    }

    @Override
    public String checkingFace(FaceFeature faceFeature) {
        if (isProcessing || idCardFeature == null) {
            return "";
        }
        isProcessing = true;
        String personId = "";
        Log.e("开始进行人脸对比，对比库数量:"  + "--" + new Date().getTime());
        float maxSimilar = 0f;
        FaceSimilar faceSimilar = compareVideoSimilar(faceFeature.getFeatureData(), idCardFeature);
        if (faceSimilar != null) {
            if (faceSimilar.getScore() >= Constants.FACE_MIN_SIMLAR && faceSimilar.getScore() > maxSimilar) {
                maxSimilar = faceSimilar.getScore();
                idCardFaceView.callback();
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