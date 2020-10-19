package com.vieboo.vbankapp.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;
import com.arcsoft.face.VersionInfo;
import com.arcsoft.face.enums.DetectFaceOrientPriority;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.imageutil.ArcSoftImageFormat;
import com.arcsoft.imageutil.ArcSoftImageUtil;
import com.arcsoft.imageutil.ArcSoftImageUtilError;
import com.vieboo.vbankapp.face.ConfigUtil;
import com.vieboo.vbankapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.FaceEngine.ASF_FACE_RECOGNITION;
import static com.arcsoft.face.enums.DetectFaceOrientPriority.ASF_OP_ALL_OUT;

/**
 * 算法管理类
 */
public class FaceServer {


    private FaceEngine faceEngine;
    private int faceEngineCode = -1;
    private int type = 0; //0-图片,1-视频
    private Context context;

    private FaceEngine videoFaceEngine;

    private static final String TAG = "DataSyncService";
    private static FaceServer faceServer = null;
    private static FaceServer faceServerVideo = null;
    private static Object object = new Object();
    private static Object objectVideo = new Object();

    /**
     * @param _type 0-图片,1-视频
     */
    private FaceServer(int _type) {
        this.type = _type;
    }

    public static FaceServer getInstance() {
        if (faceServer == null) {
            synchronized (object) {
                if (faceServer == null) {
                    faceServer = new FaceServer(0);
                }
            }
        }
        return faceServer;
    }

    public static FaceServer getVideoInstance() {
        if (faceServerVideo == null) {
            synchronized (objectVideo) {
                if (faceServerVideo == null) {
                    faceServerVideo = new FaceServer(1);
                }
            }
        }
        return faceServerVideo;
    }

    public boolean initEngine(Context context) {
        if (faceEngine == null){
            faceEngine = new FaceEngine();
        }

        /**
         * VIDEO模式：处理连续帧的图像数据
         * IMAGE模式：处理单张的图像数据
         */
        DetectMode detectMode = null;

        /**
         *    识别的最小人脸比例（图片长边与人脸框长边的比值）
         *    VIDEO模式取值范围[2,32]，推荐值为16
         *    IMAGE模式取值范围[2,32]，推荐值为32
         */
        int detectFaceScaleVal = 0;

        if (this.type == 0) {
            //图片模式，推荐值32
            detectFaceScaleVal = 32;
            detectMode = DetectMode.ASF_DETECT_MODE_IMAGE;
        } else {
            detectFaceScaleVal = 16; //视频模式推荐值16
            detectMode = DetectMode.ASF_DETECT_MODE_VIDEO;
        }
        faceEngineCode = faceEngine.init(context, detectMode, DetectFaceOrientPriority.ASF_OP_0_ONLY,
                detectFaceScaleVal, 1, ASF_FACE_RECOGNITION | FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_LIVENESS | FaceEngine.ASF_IR_LIVENESS);
        VersionInfo versionInfo = new VersionInfo();
        faceEngine.getVersion(versionInfo);

        if (faceEngineCode != ErrorInfo.MOK) {
            Log.e(TAG, "initEngine: 初始化引擎失败,状态码：" + faceEngineCode);
            return false;
        } else {
            Log.i(TAG, "initEngine: 初始化引擎成功,状态码：" + faceEngineCode + "  version:" + versionInfo);
            return true;
        }
    }

    /**
     * 销毁引擎
     */
    public void unInitEngine() {
        if (faceEngine != null) {
            faceEngineCode = faceEngine.unInit();
            faceEngine = null;
            Log.i(TAG, "unInitEngine: " + faceEngineCode);
        }
    }


    /**
     * 将Bitmap转化为人脸特征提取需要的bgr24数据
     *
     * @param bitmap
     * @return
     */
    public byte[] bitmapToBgrData(Bitmap bitmap) {
        try {
            byte[] bgr24 = ArcSoftImageUtil.createImageData(bitmap.getWidth(), bitmap.getHeight(), ArcSoftImageFormat.BGR24);
            int transformCode = ArcSoftImageUtil.bitmapToImageData(bitmap, bgr24, ArcSoftImageFormat.BGR24);
            if (transformCode == ArcSoftImageUtilError.CODE_SUCCESS) {
                return bgr24;
            } else {
                Log.e(TAG, "bitmapToBgrData: 异常,错误码:" + transformCode);
                return null;
            }
        } catch (Exception ex) {
            Log.e(TAG, "bitmapToBgrData: 异常,错误码:" + ex.getMessage());
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * 将Bitmap转化为人脸特征提取需要的bgr24数据
     *
     * @param bitmap
     * @return
     */
    public byte[] bitmapToNV21Data(Bitmap bitmap) {
        try {
            byte[] nv21 = ArcSoftImageUtil.createImageData(bitmap.getWidth(), bitmap.getHeight(), ArcSoftImageFormat.NV21);
            int transformCode = ArcSoftImageUtil.bitmapToImageData(bitmap, nv21, ArcSoftImageFormat.NV21);
            if (transformCode == ArcSoftImageUtilError.CODE_SUCCESS) {
                return nv21;
            } else {
                Log.e(TAG, "bitmapToBgrData: 异常,错误码:" + transformCode);
                return null;
            }
        } catch (Exception ex) {
            Log.e(TAG, "bitmapToBgrData: 异常,错误码:" + ex.getMessage());
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * 人脸检测,获取人脸坐标数据
     *
     * @param nv21
     * @param width
     * @param height
     * @return
     */
    public List<FaceInfo> getFaceInfo(byte[] nv21, int width, int height) {
        List<FaceInfo> faceInfos = null;
        if (faceEngine != null) {
            if (nv21 != null) {
                faceInfos = new ArrayList<>();
                int detectCode = faceEngine.detectFaces(nv21, width, height, FaceEngine.CP_PAF_NV21, faceInfos);
                Log.e(TAG, "face detection finished, code is " + detectCode + ", face num is " + faceInfos.size());
                if (detectCode != 0 || faceInfos.size() == 0) {
                    faceInfos = null;
                }
            }
        }
        return faceInfos;
    }

    /**
     * 人脸特征提取
     *
     * @param
     * @return
     */
    public FaceFeature getFaceFeature(byte[] bgr24, int width, int height, FaceInfo faceInfos) {
        FaceFeature mainFeature = null;
        if (faceEngine != null) {
            mainFeature = new FaceFeature();
            long s1 = System.currentTimeMillis();
            int res = faceEngine.extractFaceFeature(bgr24, width, height, FaceEngine.CP_PAF_NV21, faceInfos, mainFeature);
            long s2 = System.currentTimeMillis();
            Log.d(TAG, "提取特征值耗时： " + (s2 - s1) + " ms");
            if (res != ErrorInfo.MOK) {
                mainFeature = null;
                Log.e(TAG, "getFaceFeature失败,异常码:" + res);
            }
        }
        return mainFeature;
    }

    public FaceSimilar compareSimilar(byte[] featureDataOne, byte[] featureDataTwo) {
        FaceSimilar faceSimilar = new FaceSimilar();
        if (faceEngine != null) {
            FaceFeature faceFeatureOne = new FaceFeature();
            faceFeatureOne.setFeatureData(featureDataOne);
            FaceFeature faceFeatureTwo = new FaceFeature();
            faceFeatureTwo.setFeatureData(featureDataTwo);
            int res = faceEngine.compareFaceFeature(faceFeatureOne, faceFeatureTwo, faceSimilar);
            if (res != ErrorInfo.MOK) {
                Log.e(TAG, "compareSimilar失败,异常码:" + res);
            }
        }
        return faceSimilar;
    }

    /**
     * 在线激活，同一台设备只需要激活一次就行
     *
     * @param context    上下文
     * @param active_key 授权码
     * @return
     */
    public int activeOnline(Context context, String active_key) {
        int activeCode = -1;
        if (faceEngine != null) {
            activeCode = faceEngine.activeOnline(context, active_key, Constants.APP_ID, Constants.SDK_KEY);
        }
        return activeCode;
    }


    //视频相关接口


    /**
     * 视频单独初始化一个引擎，不然视频特别卡
     *
     * @param context
     */
    public void initVideoEngine(Context context) {

        videoFaceEngine = new FaceEngine();
        ConfigUtil.setFtOrient(context, ASF_OP_ALL_OUT);
        int afCode = videoFaceEngine.init(context, DetectMode.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(context),
                16, 1, FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_FACE_RECOGNITION | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_LIVENESS);
        VersionInfo versionInfo = new VersionInfo();
        videoFaceEngine.getVersion(versionInfo);
        if (afCode != ErrorInfo.MOK) {
            Log.e(TAG, "initEngine: 初始化引擎失败,状态码：" + faceEngineCode);
        } else {
            Log.i(TAG, "initEngine: 初始化引擎成功,状态码：" + faceEngineCode + "  version:" + versionInfo);
        }
    }

    public void unInitVideoEngine() {
        if (videoFaceEngine != null) {
            int code = videoFaceEngine.unInit();
            videoFaceEngine = null;
            Log.i(TAG, "unInitEngine: " + code);
        }
    }


    /**
     * 获取视频人脸信息
     *
     * @param nv21
     * @param width
     * @param height
     * @return
     */
    public List<FaceInfo> getVideoFaceInfo(byte[] nv21, int width, int height) {
        List<FaceInfo> faceInfoList = new ArrayList<>();
        if (videoFaceEngine == null) {
            return faceInfoList;
        }
        int code = videoFaceEngine.detectFaces(nv21, width, height, FaceEngine.CP_PAF_NV21, faceInfoList);
        if (code == ErrorInfo.MOK && faceInfoList.size() > 0) {
            code = videoFaceEngine.process(nv21, width, height, FaceEngine.CP_PAF_NV21, faceInfoList, FaceEngine.ASF_LIVENESS);
            if (code != ErrorInfo.MOK) {
                return null;
            }
        } else {
            return null;
        }
        return faceInfoList;
    }

    /**
     * 人脸特征提取
     *
     * @param
     * @return
     */
    public FaceFeature getVideoFaceFeature(byte[] nv21, int width, int height, FaceInfo faceInfos) {
        FaceFeature mainFeature = null;
        if (videoFaceEngine != null) {
            mainFeature = new FaceFeature();
            int res = videoFaceEngine.extractFaceFeature(nv21, width, height, FaceEngine.CP_PAF_NV21, faceInfos, mainFeature);
            if (res != ErrorInfo.MOK) {
                mainFeature = null;
                Log.e(TAG, "getFaceFeature失败,异常码:" + res);
            }
        }
        return mainFeature;
    }

    public FaceSimilar compareVideoSimilar(byte[] featureDataOne, byte[] featureDataTwo) {
        FaceSimilar faceSimilar = new FaceSimilar();
        if (videoFaceEngine != null) {
            FaceFeature faceFeatureOne = new FaceFeature();
            faceFeatureOne.setFeatureData(featureDataOne);
            FaceFeature faceFeatureTwo = new FaceFeature();
            faceFeatureTwo.setFeatureData(featureDataTwo);
            int res = videoFaceEngine.compareFaceFeature(faceFeatureOne, faceFeatureTwo, faceSimilar);
            if (res != ErrorInfo.MOK) {
                Log.e(TAG, "compareSimilar失败,异常码:" + res);
            }
        }
        return faceSimilar;
    }


    public FaceEngine getVideoFaceEngine() {
        return videoFaceEngine;
    }
}
