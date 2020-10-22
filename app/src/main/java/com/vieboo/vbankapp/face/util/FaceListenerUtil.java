package com.vieboo.vbankapp.face.util;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceSimilar;
import com.arcsoft.face.LivenessInfo;
import com.example.toollib.util.Log;
import com.usc.tts.TTSPlayer;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.application.VBankAppApplication;
import com.vieboo.vbankapp.data.PersonImageBean;
import com.vieboo.vbankapp.face.FaceListener;
import com.vieboo.vbankapp.face.RequestFeatureStatus;
import com.vieboo.vbankapp.utils.Constants;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.vieboo.vbankapp.face.util.CameraListenerUtil.extractErrorRetryMap;
import static com.vieboo.vbankapp.face.util.CameraListenerUtil.faceHelper;
import static com.vieboo.vbankapp.face.util.CameraListenerUtil.getFeatureDelayedDisposables;
import static com.vieboo.vbankapp.face.util.CameraListenerUtil.livenessErrorRetryMap;
import static com.vieboo.vbankapp.face.util.CameraListenerUtil.livenessMap;
import static com.vieboo.vbankapp.face.util.CameraListenerUtil.requestFeatureStatusMap;

public class FaceListenerUtil implements FaceListener {


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
    /**
     * 活体检测的开关
     */
    public static boolean livenessDetect = true;
    private CompositeDisposable delayFaceTaskCompositeDisposable = new CompositeDisposable();

    private List<PersonImageBean> dutyPersonFeatureList;

    private boolean isProcessing = false;
    //异步方法的人脸算法引擎
    private FaceEngine asyncFaceEngine;

    @Override
    public void onFail(Exception e) {

    }

    @Override
    public void onFaceFeatureInfoGet(@Nullable FaceFeature faceFeature, Integer requestId, Integer errorCode) {
        Log.e("onFaceFeatureInfoGet 执行 -------------------------------------------------------------");
        if (faceFeature != null) {
            Integer liveness = livenessMap.get(requestId);
            //不做活体检测的情况，直接搜索
            if (!livenessDetect) {
                searchFace(faceFeature, requestId);
            }
            //活体检测通过，搜索特征
            else if (liveness != null && liveness == LivenessInfo.ALIVE) {
                searchFace(faceFeature, requestId);
            }
            else {
                //活体检测未出结果，或者非活体，延迟执行该函数
                if (requestFeatureStatusMap.containsKey(requestId)) {
                    Observable.timer(WAIT_LIVENESS_INTERVAL, TimeUnit.MILLISECONDS)
                            .subscribe(new Observer<Long>() {
                                Disposable disposable;

                                @Override
                                public void onSubscribe(Disposable d) {
                                    disposable = d;
                                    getFeatureDelayedDisposables.add(disposable);
                                }

                                @Override
                                public void onNext(Long aLong) {
                                    onFaceFeatureInfoGet(faceFeature, requestId, errorCode);
                                }

                                @Override
                                public void onError(Throwable e) {
                                }

                                @Override
                                public void onComplete() {
                                    getFeatureDelayedDisposables.remove(disposable);
                                }
                            });
                }
            }

        } else {
            //特征提取失败
            if (increaseAndGetValue(extractErrorRetryMap, requestId) > MAX_RETRY_TIME) {
                extractErrorRetryMap.put(requestId, 0);
                String msg;
                // 传入的FaceInfo在指定的图像上无法解析人脸，此处使用的是RGB人脸数据，一般是人脸模糊
                if (errorCode != null && errorCode == ErrorInfo.MERR_FSDK_FACEFEATURE_LOW_CONFIDENCE_LEVEL) {
                    msg = VBankAppApplication.getInstance().getString(R.string.low_confidence_level);
                } else {
                    msg = "ExtractCode:" + errorCode;
                }
                faceHelper.setName(requestId, VBankAppApplication.getInstance().getString(R.string.recognize_failed_notice, msg));
                // 在尝试最大次数后，特征提取仍然失败，则认为识别未通过
                requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
                retryRecognizeDelayed(requestId);
            } else {
                requestFeatureStatusMap.put(requestId, RequestFeatureStatus.TO_RETRY);
            }
        }
    }

    @Override
    public void onFaceLivenessInfoGet(@Nullable LivenessInfo livenessInfo, Integer requestId, Integer errorCode) {
        if (livenessInfo != null) {
            int liveness = livenessInfo.getLiveness();
            livenessMap.put(requestId, liveness);
            // 非活体，重试
            if (liveness == LivenessInfo.NOT_ALIVE) {
                faceHelper.setName(requestId, VBankAppApplication.getInstance().getString(R.string.recognize_failed_notice, "NOT_ALIVE"));
                // 延迟 FAIL_RETRY_INTERVAL 后，将该人脸状态置为UNKNOWN，帧回调处理时会重新进行活体检测
                retryLivenessDetectDelayed(requestId);
            }
        } else {
            if (increaseAndGetValue(livenessErrorRetryMap, requestId) > MAX_RETRY_TIME) {
                livenessErrorRetryMap.put(requestId, 0);
                String msg;
                // 传入的FaceInfo在指定的图像上无法解析人脸，此处使用的是RGB人脸数据，一般是人脸模糊
                if (errorCode != null && errorCode == ErrorInfo.MERR_FSDK_FACEFEATURE_LOW_CONFIDENCE_LEVEL) {
                    msg = "人脸置信度低";
                } else {
                    msg = "ProcessCode:" + errorCode;
                }
                faceHelper.setName(requestId, VBankAppApplication.getInstance().getString(R.string.recognize_failed_notice, msg));
                retryLivenessDetectDelayed(requestId);
            } else {
                livenessMap.put(requestId, LivenessInfo.UNKNOWN);
            }
        }
    }


    private void searchFace(final FaceFeature frFace, final Integer requestId) {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) {
//                        Log.i(TAG, "subscribe: fr search start = " + System.currentTimeMillis() + " trackId = " + requestId);
                        String personId = checkingFace(frFace);
//                        Log.i(TAG, "subscribe: fr search end = " + System.currentTimeMillis() + " trackId = " + requestId);
                        emitter.onNext(personId);

                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String compareResult) {
                        if (TextUtils.isEmpty(compareResult)) {
                            requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
                            faceHelper.setName(requestId, "VISITOR " + requestId);
                            return;
                        }
                        requestFeatureStatusMap.put(requestId, RequestFeatureStatus.SUCCEED);
                        faceHelper.setName(requestId, VBankAppApplication.getInstance().getString(R.string.recognize_success_notice, compareResult));
                        checkingSuccess(compareResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        faceHelper.setName(requestId, VBankAppApplication.getInstance().getString(R.string.recognize_failed_notice, "NOT_REGISTERED"));
                        retryRecognizeDelayed(requestId);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 延迟 FAIL_RETRY_INTERVAL 重新进行人脸识别
     *
     * @param requestId 人脸ID
     */
    private void retryRecognizeDelayed(final Integer requestId) {
        requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
        Observable.timer(FAIL_RETRY_INTERVAL, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Long>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        delayFaceTaskCompositeDisposable.add(disposable);
                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        // 将该人脸特征提取状态置为FAILED，帧回调处理时会重新进行活体检测
                        faceHelper.setName(requestId, Integer.toString(requestId));
                        requestFeatureStatusMap.put(requestId, RequestFeatureStatus.TO_RETRY);
                        delayFaceTaskCompositeDisposable.remove(disposable);
                    }
                });
    }

    /**
     * 延迟 FAIL_RETRY_INTERVAL 重新进行活体检测
     *
     * @param requestId 人脸ID
     */
    private void retryLivenessDetectDelayed(final Integer requestId) {
        Observable.timer(FAIL_RETRY_INTERVAL, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Long>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        delayFaceTaskCompositeDisposable.add(disposable);
                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        // 将该人脸状态置为UNKNOWN，帧回调处理时会重新进行活体检测
                        if (livenessDetect) {
                            faceHelper.setName(requestId, Integer.toString(requestId));
                        }
                        livenessMap.put(requestId, LivenessInfo.UNKNOWN);
                        delayFaceTaskCompositeDisposable.remove(disposable);
                    }
                });
    }


    /**
     * 人脸比对
     */
    private String checkingFace(FaceFeature faceFeature) {
        if (isProcessing || asyncFaceEngine == null) {
            return "";
        }
        isProcessing = true;
        String personId = "";
        if (dutyPersonFeatureList != null && dutyPersonFeatureList.size() > 0) {
            Log.e("开始进行人脸对比，对比库数量:" + dutyPersonFeatureList.size() + "--" + new Date().getTime());
            float maxSimilar = 0f;

            for (PersonImageBean person : dutyPersonFeatureList) {
                if (!TextUtils.isEmpty(person.getPadFeature())) {
                    FaceSimilar faceSimilar = compareVideoSimilar(faceFeature.getFeatureData(), Base64Utils.base64String2ByteFun(person.getPadFeature()));
                    if (faceSimilar != null) {
                        if (faceSimilar.getScore() >= Constants.FACE_MIN_SIMLAR && faceSimilar.getScore() > maxSimilar) {
                            maxSimilar = faceSimilar.getScore();
                            personId = person.getPersonId();
                        }
                        Log.e("checkingFace:姓名:" + person.getPersonId() + "-相似度:" + faceSimilar.getScore());
                    }
                }
            }
            Log.e("完成进行人脸对比" + "--" + new Date().getTime());

            if (TextUtils.isEmpty(personId)) {
                TTSPlayer.getInstance().playText("您不是库管员");
            }
        }
        isProcessing = false;
        return personId;

    }

    /**
     * 人脸对比成功
     */
    private void checkingSuccess(String personId) {

    }

    /**
     * 1：1人脸特征比对
     *
     * @param featureDataOne
     * @param featureDataTwo
     * @return
     */
    private FaceSimilar compareVideoSimilar(byte[] featureDataOne, byte[] featureDataTwo) {
        FaceSimilar faceSimilar = new FaceSimilar();
        if (asyncFaceEngine != null) {
            FaceFeature faceFeatureOne = new FaceFeature();
            faceFeatureOne.setFeatureData(featureDataOne);
            FaceFeature faceFeatureTwo = new FaceFeature();
            faceFeatureTwo.setFeatureData(featureDataTwo);
            int res = asyncFaceEngine.compareFaceFeature(faceFeatureOne, faceFeatureTwo, faceSimilar);
            if (res != ErrorInfo.MOK) {
                Log.e("compareSimilar失败,异常码:" + res);
            }
        }
        return faceSimilar;
    }

    /**
     * 将map中key对应的value增1回传
     *
     * @param countMap map
     * @param key      key
     * @return 增1后的value
     */
    public int increaseAndGetValue(Map<Integer, Integer> countMap, int key) {
        if (countMap == null) {
            return 0;
        }
        Integer value = countMap.get(key);
        if (value == null) {
            value = 0;
        }
        countMap.put(key, ++value);
        return value;
    }


    public FaceListenerUtil setAsyncFaceEngine(FaceEngine asyncFaceEngine) {
        this.asyncFaceEngine = asyncFaceEngine;
        return this;
    }

    public FaceListenerUtil setDutyPersonFeatureList(List<PersonImageBean> dutyPersonFeatureList) {
        this.dutyPersonFeatureList = dutyPersonFeatureList;
        return this;
    }

}
