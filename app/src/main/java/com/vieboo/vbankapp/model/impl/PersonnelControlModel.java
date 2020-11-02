package com.vieboo.vbankapp.model.impl;

import android.app.Activity;
import android.graphics.Bitmap;

import com.example.toollib.data.BaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.HttpError;
import com.example.toollib.http.function.BaseHttpConsumer;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.Log;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.PunchRecordVO;
import com.vieboo.vbankapp.data.RecordVO;
import com.vieboo.vbankapp.download.DownLoadUtil;
import com.vieboo.vbankapp.face.util.ImageUtil;
import com.vieboo.vbankapp.http.ServiceUrl;
import com.vieboo.vbankapp.model.IPersonnelControlModel;
import com.vieboo.vbankapp.model.IPersonnelControlView;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PersonnelControlModel extends BaseModule<IPersonnelControlView> implements IPersonnelControlModel {

    @Override
    public void getStaffStyle() {
        RxUtils.getObservable(ServiceUrl.getUserApi().findPerson("employee", 1, 10000))
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<List<PunchRecordVO>>() {
                    @Override
                    protected void onSuccess(List<PunchRecordVO> passengerVO) {


                        mViewRef.get().setStaffStyle(passengerVO);
                    }
                });
    }

    @Override
    public void getPunchRecord() {
        RxUtils.getObservable(ServiceUrl.getUserApi().findPunchRecord())
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<List<RecordVO>>() {
                    @Override
                    protected void onSuccess(List<RecordVO> recordVOS) {
                        mViewRef.get().setPunchRecord(recordVOS);
                    }
                });
    }

    @Override
    public void clockIn(Bitmap bitmap, String personId) {
        byte[] currentImgData = ImageUtil.Bitmap2Bytes(bitmap);
        File fileFromBytes = ImageUtil.getFileFromBytes(currentImgData, DownLoadUtil.mSinglePath + "face.jpg");

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("file", fileFromBytes.getName(),
                RequestBody.create(MediaType.parse("multipart/form-data"), fileFromBytes));
        builder.addFormDataPart("kind", "person");

        RxUtils.getObservable(ServiceUrl.getUserApi().upload(builder.build()))
                .compose(mViewRef.get().bindLifecycle())
                .doOnNext(new BaseHttpConsumer<String>() {
                    @Override
                    public void httpConsumerAccept(HttpResult<String> httpResult) {
                        Log.e("httpResult --- " + httpResult.toString());
                    }
                }).concatMap(new Function<HttpResult<String>, ObservableSource<HttpResult<String>>>() {
            @Override
            public ObservableSource<HttpResult<String>> apply(HttpResult<String> httpResult) {
                if (Integer.parseInt(httpResult.getCode()) != HttpError.HTTP_SUCCESS.getCode()) {
                    return null;
                }
                Observable<HttpResult<String>> httpResultObservable = ServiceUrl.getUserApi().clockIn(httpResult.getData(), personId);
                return RxUtils.getObservable(httpResultObservable);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseHttpRxObserver<String>(mContext.get()) {
                    @Override
                    protected void onSuccess(String personId) {
                        Log.e("httpResult --- " + personId);
                    }
                });
    }

    @Override
    public void clockOut() {
        String id = mViewRef.get().getPersonalId();
        RxUtils.getObservable(ServiceUrl.getUserApi().clockOut("", id))
                .compose(mViewRef.get().bindLifecycle())
                .subscribe(new BaseHttpRxObserver<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        mViewRef.get().showToast(mContext.get().getResources().getString(R.string.clock_out_success));
                    }
                });
    }

}
