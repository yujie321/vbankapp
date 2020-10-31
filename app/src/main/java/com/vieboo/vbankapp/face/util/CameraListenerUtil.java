package com.vieboo.vbankapp.face.util;

import android.content.Context;
import android.hardware.Camera;
import android.view.TextureView;

import com.arcsoft.face.AgeInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.GenderInfo;
import com.arcsoft.face.LivenessInfo;
import com.example.toollib.util.Log;
import com.vieboo.vbankapp.application.VBankAppApplication;
import com.vieboo.vbankapp.face.CameraListener;
import com.vieboo.vbankapp.face.ConfigUtil;
import com.vieboo.vbankapp.face.DrawHelper;
import com.vieboo.vbankapp.face.DrawInfo;
import com.vieboo.vbankapp.face.FaceHelper;
import com.vieboo.vbankapp.face.FaceListener;
import com.vieboo.vbankapp.face.FacePreviewInfo;
import com.vieboo.vbankapp.face.FaceRectView;
import com.vieboo.vbankapp.face.LivenessType;
import com.vieboo.vbankapp.face.RecognizeColor;
import com.vieboo.vbankapp.face.RequestFeatureStatus;
import com.vieboo.vbankapp.face.RequestLivenessStatus;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.disposables.CompositeDisposable;

import static com.vieboo.vbankapp.face.util.FaceListenerUtil.livenessDetect;

public class CameraListenerUtil implements CameraListener {

    /**
     * 最大线程数
     */
    private static final int MAX_DETECT_NUM = 10;

    private FaceListener faceListener;
    private DrawHelper drawHelper;
    public FaceHelper faceHelper;
    private TextureView texturePreview;
    private Camera.Size previewSize;

    /**
     * VIDEO模式人脸检测引擎，用于预览帧人脸追踪
     */
    private FaceEngine ftEngine;
    /**
     * 用于特征提取的引擎
     */
    private FaceEngine frEngine;
    /**
     * IMAGE模式活体检测引擎，用于预览帧人脸活体检测
     */
    private FaceEngine flEngine;


    private FaceRectView faceRectView;

    /**
     * 用于记录人脸识别相关状态
     */
    public static ConcurrentHashMap<Integer, Integer> requestFeatureStatusMap = new ConcurrentHashMap<>();
    /**
     * 用于存储活体值
     */
    public static ConcurrentHashMap<Integer, Integer> livenessMap = new ConcurrentHashMap<>();
    /**
     * 用于记录人脸特征提取出错重试次数
     */
    public static ConcurrentHashMap<Integer, Integer> extractErrorRetryMap = new ConcurrentHashMap<>();
    /**
     * 用于存储活体检测出错重试次数
     */
    public static ConcurrentHashMap<Integer, Integer> livenessErrorRetryMap = new ConcurrentHashMap<>();
    public static CompositeDisposable getFeatureDelayedDisposables = new CompositeDisposable();

    public CameraListenerUtil(TextureView texturePreview) {
        this.texturePreview = texturePreview;
    }

    @Override
    public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {
        Log.e("onCameraOpened: " + cameraId + "  " + displayOrientation + " " + isMirror);
        previewSize = camera.getParameters().getPreviewSize();
        drawHelper = new DrawHelper(previewSize.width, previewSize.height, texturePreview.getWidth(), texturePreview.getHeight(), displayOrientation
                , cameraId, isMirror, false, false);
        if (faceHelper == null) {
            Integer trackedFaceCount = null;
            // 记录切换时的人脸序号
            if (faceHelper != null) {
                trackedFaceCount = faceHelper.getTrackedFaceCount();
                faceHelper.release();
            }
            faceHelper = new FaceHelper.Builder()
                    .ftEngine(ftEngine)
                    .frEngine(frEngine)
                    .flEngine(flEngine)
                    .frQueueSize(MAX_DETECT_NUM)
                    .flQueueSize(MAX_DETECT_NUM)
                    .previewSize(previewSize)
                    .faceListener(faceListener)
                    .trackedFaceCount(trackedFaceCount == null ? ConfigUtil.getTrackedFaceCount(VBankAppApplication.getInstance()) : trackedFaceCount)
                    .build();
        }
    }

    @Override
    public void onPreview(byte[] data, Camera camera) {
        if (faceRectView != null) {
            faceRectView.clearFaceInfo();
        }
        List<FacePreviewInfo> facePreviewInfoList = faceHelper.onPreviewFrame(data);
        if (facePreviewInfoList != null && faceRectView != null && drawHelper != null) {
            drawPreviewInfo(facePreviewInfoList);
        }
        clearLeftFace(facePreviewInfoList);
        if (facePreviewInfoList != null && facePreviewInfoList.size() > 0 && previewSize != null) {
            for (int i = 0; i < facePreviewInfoList.size(); i++) {
                Integer status = requestFeatureStatusMap.get(facePreviewInfoList.get(i).getTrackId());
                /**
                 * 在活体检测开启，在人脸识别状态不为成功或人脸活体状态不为处理中（ANALYZING）且不为处理完成（ALIVE、NOT_ALIVE）时重新进行活体检测
                 */
                if (livenessDetect && (status == null || status != RequestFeatureStatus.SUCCEED)) {
                    Integer liveness = livenessMap.get(facePreviewInfoList.get(i).getTrackId());
                    if (liveness == null
                            || (liveness != LivenessInfo.ALIVE && liveness != LivenessInfo.NOT_ALIVE && liveness != RequestLivenessStatus.ANALYZING)) {
                        livenessMap.put(facePreviewInfoList.get(i).getTrackId(), RequestLivenessStatus.ANALYZING);
                        faceHelper.requestFaceLiveness(data, facePreviewInfoList.get(i).getFaceInfo(), previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, facePreviewInfoList.get(i).getTrackId(), LivenessType.RGB);
                    }
                }
                /**
                 * 对于每个人脸，若状态为空或者为失败，则请求特征提取（可根据需要添加其他判断以限制特征提取次数），
                 * 特征提取回传的人脸特征结果在{@link FaceListener#onFaceFeatureInfoGet(FaceFeature, Integer, Integer)}中回传
                 */
                if (status == null
                        || status == RequestFeatureStatus.TO_RETRY) {
                    requestFeatureStatusMap.put(facePreviewInfoList.get(i).getTrackId(), RequestFeatureStatus.SEARCHING);
                    faceHelper.requestFaceFeature(data, facePreviewInfoList.get(i).getFaceInfo(), previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, facePreviewInfoList.get(i).getTrackId());
//                            Log.i(TAG, "onPreview: fr start = " + System.currentTimeMillis() + " trackId = " + facePreviewInfoList.get(i).getTrackedFaceCount());
                }
            }
        }
    }

    @Override
    public void onCameraClosed() {

    }

    @Override
    public void onCameraError(Exception e) {

    }

    @Override
    public void onCameraConfigurationChanged(int cameraID, int displayOrientation) {
        if (drawHelper != null) {
            drawHelper.setCameraDisplayOrientation(displayOrientation);
        }
        Log.e("onCameraConfigurationChanged: " + cameraID + "  " + displayOrientation);
    }


    /**
     * 绘画人脸框
     * @param facePreviewInfoList
     */
    private void drawPreviewInfo(List<FacePreviewInfo> facePreviewInfoList) {
        List<DrawInfo> drawInfoList = new ArrayList<>();
        for (int i = 0; i < facePreviewInfoList.size(); i++) {
            String name = faceHelper.getName(facePreviewInfoList.get(i).getTrackId());
            Integer liveness = livenessMap.get(facePreviewInfoList.get(i).getTrackId());
            Integer recognizeStatus = requestFeatureStatusMap.get(facePreviewInfoList.get(i).getTrackId());

            // 根据识别结果和活体结果设置颜色
            int color = RecognizeColor.COLOR_UNKNOWN;
            if (recognizeStatus != null) {
                if (recognizeStatus == RequestFeatureStatus.FAILED) {
                    color = RecognizeColor.COLOR_FAILED;
                }
                if (recognizeStatus == RequestFeatureStatus.SUCCEED) {
                    color = RecognizeColor.COLOR_SUCCESS;
                }
            }
            if (liveness != null && liveness == LivenessInfo.NOT_ALIVE) {
                color = RecognizeColor.COLOR_FAILED;
            }

            drawInfoList.add(new DrawInfo(drawHelper.adjustRect(facePreviewInfoList.get(i).getFaceInfo().getRect()),
                    GenderInfo.UNKNOWN, AgeInfo.UNKNOWN_AGE, liveness == null ? LivenessInfo.UNKNOWN : liveness, color,
                    name == null ? String.valueOf(facePreviewInfoList.get(i).getTrackId()) : name));
        }
        drawHelper.draw(faceRectView, drawInfoList);
    }

    /**
     * 删除已经离开的人脸
     * @param facePreviewInfoList 人脸和trackId列表
     */
    public void clearLeftFace(List<FacePreviewInfo> facePreviewInfoList) {
        if (facePreviewInfoList == null || facePreviewInfoList.size() == 0) {
            requestFeatureStatusMap.clear();
            livenessMap.clear();
            livenessErrorRetryMap.clear();
            extractErrorRetryMap.clear();
            if (getFeatureDelayedDisposables != null) {
                getFeatureDelayedDisposables.clear();
            }
            return;
        }
        Enumeration<Integer> keys = requestFeatureStatusMap.keys();
        while (keys.hasMoreElements()) {
            int key = keys.nextElement();
            boolean contained = false;
            for (FacePreviewInfo facePreviewInfo : facePreviewInfoList) {
                if (facePreviewInfo.getTrackId() == key) {
                    contained = true;
                    break;
                }
            }
            if (!contained) {
                requestFeatureStatusMap.remove(key);
                livenessMap.remove(key);
                livenessErrorRetryMap.remove(key);
                extractErrorRetryMap.remove(key);
            }
        }
    }

    public CameraListenerUtil setFaceRectView(FaceRectView faceRectView){
        this.faceRectView = faceRectView;
        return this;
    }

    public CameraListenerUtil setFtEngine(FaceEngine ftEngine) {
        this.ftEngine = ftEngine;
        return this;
    }

    public CameraListenerUtil setFrEngine(FaceEngine frEngine) {
        this.frEngine = frEngine;
        return this;
    }

    public CameraListenerUtil setFlEngine(FaceEngine flEngine) {
        this.flEngine = flEngine;
        return this;
    }

    public CameraListenerUtil setFaceListener(FaceListener faceListener) {
        this.faceListener = faceListener;
        return this;
    }

}
