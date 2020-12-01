package com.vieboo.vbankapp.face.util;

import android.content.Context;

import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.enums.DetectFaceOrientPriority;
import com.arcsoft.face.enums.DetectMode;
import com.blankj.utilcode.util.ToastUtils;
import com.vieboo.vbankapp.face.ConfigUtil;

public class FaceUtil {

    /**
     * 最大线程数
     */
    private static final int MAX_DETECT_NUM = 10;
    /**
     * 当FR成功，活体未成功时，FR等待活体的时间
     */
    private static final int WAIT_LIVENESS_INTERVAL = 100;
    /**
     * 失败重试间隔时间（ms）
     */
    private static final long FAIL_RETRY_INTERVAL = 1000;
    /**
     * 出错重试最大次数
     */
    private static final int MAX_RETRY_TIME = 3;

    private int ftInitCode = -1;
    private int frInitCode = -1;
    private int flInitCode = -1;
    private int asyncFaceInitCode;

    public FaceEngine initEngineVideo(Context mContext) {
        FaceEngine ftEngine = new FaceEngine();
        ftInitCode = ftEngine.init(mContext, DetectMode.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(mContext),
                16, MAX_DETECT_NUM, FaceEngine.ASF_FACE_DETECT);
        if (ftInitCode != ErrorInfo.MOK) {
            String error = "人脸识别算法初始化异步，异常码：" + ftInitCode;
            ToastUtils.showShort(error);
            return null;
        }
        return ftEngine;
    }

    public FaceEngine initEngineImage(Context mContext) {
        FaceEngine  frEngineImage = new FaceEngine();
        frInitCode = frEngineImage.init(mContext, DetectMode.ASF_DETECT_MODE_IMAGE, DetectFaceOrientPriority.ASF_OP_0_ONLY,
                16, MAX_DETECT_NUM, FaceEngine.ASF_FACE_RECOGNITION);
        if (frInitCode != ErrorInfo.MOK) {
            String error = "人脸识别算法初始化异步，异常码：" + frInitCode;
            ToastUtils.showShort(error);
            return null;
        }
        return frEngineImage;
    }

    public FaceEngine initEngineImageLiven(Context mContext) {
        FaceEngine  frEngineImage = new FaceEngine();
        flInitCode = frEngineImage.init(mContext, DetectMode.ASF_DETECT_MODE_IMAGE, DetectFaceOrientPriority.ASF_OP_0_ONLY,
                16, MAX_DETECT_NUM, FaceEngine.ASF_LIVENESS);
        if (flInitCode != ErrorInfo.MOK) {
            String error = "人脸识别算法初始化异步，异常码：" + flInitCode;
            ToastUtils.showShort(error);
            return null;
        }
        return frEngineImage;
    }

    public FaceEngine initAsyncFaceEngine(Context mContext) {
        FaceEngine asyncFaceEngine = new FaceEngine();
        asyncFaceInitCode = asyncFaceEngine.init(mContext, DetectMode.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(mContext),
                16, MAX_DETECT_NUM, FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_FACE_RECOGNITION);
        if (asyncFaceInitCode != ErrorInfo.MOK) {
            String error = "人脸识别算法初始化异步，异常码：" + asyncFaceInitCode;
            ToastUtils.showShort(error);
            return null;
        }
        return asyncFaceEngine;
    }


}
