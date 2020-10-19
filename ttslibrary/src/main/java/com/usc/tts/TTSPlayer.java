package com.usc.tts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.unisound.client.SpeechConstants;
import com.unisound.client.SpeechSynthesizer;
import com.unisound.client.SpeechSynthesizerListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;


public class TTSPlayer {
    private TTSPlayer() {

    }

    private static class TTSPlayerHolder {
        private static TTSPlayer ttsPlayer = new TTSPlayer();
    }

    public static TTSPlayer getInstance() {
        return TTSPlayer.TTSPlayerHolder.ttsPlayer;
    }

    private String TAG = "TTSPlayer";
    /**
     * 播放状态
     */
    private boolean TTS_PLAY_FLAGE = false;

    private SpeechSynthesizer mTTSPlayer;

    public static void copyAssetsPath(Context context, String assetsFilePath, String dstPath) {
        String[] files;
        try {
            //注意：在assets文件夹下影藏了三个带文件的文件夹，分别是images、sounds、webkit
            //返回数组files里面会包含这三个文件夹
            files = context.getResources().getAssets().list(assetsFilePath);
//			files = context.getResources().getAssets().getLocales();
        } catch (Exception e1) {
            return;
        }

//        String File_PATH = "/sdcard/unisound/tts/";
//        String File_PATH = Environment.getExternalStorageDirectory()+"/unisound/tts/";
        File mWorkingPath = new File(dstPath);
        if (!mWorkingPath.exists()) {
            mWorkingPath.mkdirs();
        }

        for (int i = 0; i < files.length; i++) {
            try {
                String fileName = files[i];
                File outFile = new File(mWorkingPath, fileName);
                if (outFile.exists()) {
                    continue;
                }

                InputStream in = context.getAssets().open(assetsFilePath + "/" + fileName);
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                in.close();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 初始化本地离线TTS
     * @param context
     */
    @SuppressLint("WrongConstant")
    public void initTts(Context context) {

        //语言模型文件路径
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/store/tts/";
        //拷贝语音文件到指定路径

        copyAssetsPath(context,"OfflineTTSModels", path);

        // 初始化语音合成对象
        mTTSPlayer = new SpeechSynthesizer(Utils.getApp(), Config.appKey, Config.secret);
        // 设置本地合成
        mTTSPlayer.setOption(SpeechConstants.TTS_SERVICE_MODE, SpeechConstants.TTS_SERVICE_MODE_LOCAL);

        // 设置前端模型
        mTTSPlayer.setOption(SpeechConstants.TTS_KEY_FRONTEND_MODEL_PATH, path + "frontend_model");
        // 设置后端模型wu zi
        mTTSPlayer.setOption(SpeechConstants.TTS_KEY_BACKEND_MODEL_PATH, path + "backend_lzl");
        // 设置回调监听
        mTTSPlayer.setTTSListener(new SpeechSynthesizerListener() {

            @Override
            public void onEvent(int type) {
                switch (type) {
                    case SpeechConstants.TTS_EVENT_INIT:
                        // 初始化成功回调
                        Log.i(TAG, "onInitFinish");
                        break;
                    case SpeechConstants.TTS_EVENT_SYNTHESIZER_START:
                        // 开始合成回调
                        Log.i(TAG, "beginSynthesizer");
                        break;
                    case SpeechConstants.TTS_EVENT_SYNTHESIZER_END:
                        // 合成结束回调
                        Log.i(TAG, "endSynthesizer");
                        break;
                    case SpeechConstants.TTS_EVENT_BUFFER_BEGIN:
                        // 开始缓存回调
                        Log.i(TAG, "beginBuffer");
                        break;
                    case SpeechConstants.TTS_EVENT_BUFFER_READY:
                        // 缓存完毕回调
                        Log.i(TAG, "bufferReady");
                        break;
                    case SpeechConstants.TTS_EVENT_PLAYING_START:
                        // 开始播放回调
                        Log.i(TAG, "onPlayBegin");
                        break;
                    case SpeechConstants.TTS_EVENT_PLAYING_END:
                        // 播放完成回调
                        Log.i(TAG, "onPlayEnd");
                        TTS_PLAY_FLAGE = false;
                        break;
                    case SpeechConstants.TTS_EVENT_PAUSE:
                        // 暂停回调
                        Log.i(TAG, "pause");
                        break;
                    case SpeechConstants.TTS_EVENT_RESUME:
                        // 恢复回调
                        Log.i(TAG, "resume");
                        break;
                    case SpeechConstants.TTS_EVENT_STOP:
                        // 停止回调
                        Log.i(TAG, "stop");
                        break;
                    case SpeechConstants.TTS_EVENT_RELEASE:
                        // 释放资源回调
                        Log.i(TAG, "release");
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onError(int type, String errorMSG) {
                // 语音合成错误回调
                Log.i(TAG, "onError");
                TTS_PLAY_FLAGE = false;
            }
        });
        // 初始化合成引擎
        mTTSPlayer.init("");
    }


    /**
     * 语音播报
     *
     * @param text
     */
    public void playText(final String text) {

        if (mTTSPlayer!=null&&!TTS_PLAY_FLAGE) {
            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                    Log.d("TTSPlayer123",Thread.currentThread().getName());
                    mTTSPlayer.playText(text);
                }
            }).subscribeOn(Schedulers.computation()).subscribe();

        }
    }


}
