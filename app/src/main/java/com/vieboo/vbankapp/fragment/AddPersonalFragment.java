package com.vieboo.vbankapp.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.TextureView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.enums.StaticExplain;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.HttpError;
import com.example.toollib.http.function.BaseHttpConsumer;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.DensityUtil;
import com.example.toollib.util.Log;
import com.sdses.idCard.IdCardHelper;
import com.sdses.idCard.IdInfo;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.adapter.SpinnerAdapter;
import com.vieboo.vbankapp.data.PersonImageBean;
import com.vieboo.vbankapp.data.SpinnerVO;
import com.vieboo.vbankapp.data.UserInfo;
import com.vieboo.vbankapp.data.db.DBHelper;
import com.vieboo.vbankapp.download.DownLoadUtil;
import com.vieboo.vbankapp.face.CameraHelper;
import com.vieboo.vbankapp.face.FaceRectView;
import com.vieboo.vbankapp.face.util.CameraListenerUtil;
import com.vieboo.vbankapp.face.util.FaceUtil;
import com.vieboo.vbankapp.face.util.IdCardFaceListenerUtil;
import com.vieboo.vbankapp.face.util.IdCardFaceView;
import com.vieboo.vbankapp.face.util.ImageUtil;
import com.vieboo.vbankapp.http.ServiceUrl;
import com.vieboo.vbankapp.model.IAddPersonalModel;
import com.vieboo.vbankapp.model.IAddPersonalView;
import com.vieboo.vbankapp.model.impl.AddPersonalModel;
import com.vieboo.vbankapp.utils.Constants;
import com.vieboo.vbankapp.utils.FaceAlgoUtils;
import com.vieboo.vbankapp.utils.PermissionUtil;
import com.vieboo.vbankapp.weight.IdCardDialog;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.vieboo.vbankapp.utils.Constants.ACTION_REQUEST_PERMISSIONS;

/**
 * 人员新增
 */
public class AddPersonalFragment extends BaseFragment<IAddPersonalModel> implements IAddPersonalView, IdCardFaceView,ViewTreeObserver.OnGlobalLayoutListener {

    @BindView(R.id.texture_preview)
    TextureView texturePreview;
    @BindView(R.id.faceRectView)
    FaceRectView faceRectView;
    @BindView(R.id.ivPersonnelHead)
    ImageView ivPersonnelHead;
    @BindView(R.id.tvPersonalName)
    TextView tvPersonalName;
    @BindView(R.id.tvPersonalSex)
    TextView tvPersonalSex;
    @BindView(R.id.tvPersonalNation)
    TextView tvPersonalNation;
    @BindView(R.id.tvDateBirth)
    TextView tvDateBirth;
    @BindView(R.id.tvIdCardAddress)
    TextView tvIdCardAddress;
    @BindView(R.id.tvIdCardNumber)
    TextView tvIdCardNumber;


    @BindView(R.id.spinnerDepartment)
    Spinner spinnerDepartment;
    @BindView(R.id.spinnerPositions)
    Spinner spinnerPositions;
    @BindView(R.id.spinnerJurisdiction)
    Spinner spinnerJurisdiction;

    private FaceUtil faceUtil;

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

    //异步方法的人脸算法引擎
    private FaceEngine asyncFaceEngine;
    private CameraHelper cameraHelper;

    private CameraListenerUtil cameraListenerUtil;
    private IdCardFaceListenerUtil faceListenerUtil;

    /**
     * 所需的所有权限信息
     */
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE
    };

    private int dropDownVerticalOffset = DensityUtil.dp2px(32f);

    private volatile byte[] idCardFeature;
    private volatile long idTime;
    private IdInfo idInfo;

    public static AddPersonalFragment newInstance() {
        Bundle args = new Bundle();
        AddPersonalFragment fragment = new AddPersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_add_personal;
    }

    @Override
    public void initView() {
        faceUtil = new FaceUtil();
        //人脸识别相关
        texturePreview.getViewTreeObserver().addOnGlobalLayoutListener(this);

        //获取部门、职务、权限
        iModule.initData();
        //身份证识别
        iModule.initIDCard();

        Log.e("线程 = " + Thread.currentThread().getName());
    }

    @Override
    public void setDepartment(List<SpinnerVO> spinnerVOS) {
        spinnerDepartment.setDropDownVerticalOffset(dropDownVerticalOffset);
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getActivity(), spinnerVOS);
        spinnerDepartment.setAdapter(spinnerAdapter);
    }

    @Override
    public void setPositions(List<SpinnerVO> spinnerVOS) {
        spinnerPositions.setDropDownVerticalOffset(dropDownVerticalOffset);
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getActivity(), spinnerVOS);
        spinnerPositions.setAdapter(spinnerAdapter);
    }

    @Override
    public void setJurisdiction(List<SpinnerVO> spinnerVOS) {
        spinnerJurisdiction.setDropDownVerticalOffset(dropDownVerticalOffset);
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getActivity(), spinnerVOS);
        spinnerJurisdiction.setAdapter(spinnerAdapter);
    }

    @Override
    public int getDepartment() {
        return (int) spinnerDepartment.getSelectedView().findViewById(R.id.tvSpinnerName).getTag();
    }

    @OnClick({R.id.ivAddPersonalBack, R.id.btnSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAddPersonalBack:
                popBackStack();
                break;
            case R.id.btnSave:
                //保存
                addPersontoDB();
                break;
            case R.id.btnClose:
                showToast("取消");
                break;
        }
    }

    private void addPersontoDB() {
        UserInfo userInfo = new UserInfo();
        userInfo.setImageUrl("");
        userInfo.setName(idInfo.getName());
        userInfo.setSex(1);
        userInfo.setNation(idInfo.getNational());
        userInfo.setAddress(idInfo.getAddress());
        userInfo.setIdCard(idInfo.getIdCardNum());
        userInfo.setPadFeature(idCardFeature);

        byte[] currentImgData = ImageUtil.Bitmap2Bytes(idInfo.getPhotoBmp());
        File fileFromBytes = ImageUtil.getFileFromBytes(currentImgData, DownLoadUtil.mSinglePath + "test.jpg");

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("file", fileFromBytes.getName(),
                    RequestBody.create(MediaType.parse("multipart/form-data"), fileFromBytes));

        builder.addFormDataPart("kind", "person");
        Observable<HttpResult<String>> httpResultObservable1 = ServiceUrl.getUserApi().upload(builder.build());
        RxUtils.getObservable(httpResultObservable1)
                .compose(this.bindToLifecycle())
                .doOnNext(new BaseHttpConsumer<String>() {
                    @Override
                    public void httpConsumerAccept(HttpResult<String> httpResult) {
                        userInfo.setImageUrl(httpResult.getData());
                    }
                }).concatMap(new Function<HttpResult<String>, ObservableSource<HttpResult<String>>>() {
                    @Override
                    public ObservableSource<HttpResult<String>> apply(HttpResult<String> httpResult) {
                        if (Integer.parseInt(httpResult.getCode()) != HttpError.HTTP_SUCCESS.getCode()) {
                            return null;
                        }
                        return RxUtils.getObservable(ServiceUrl.getUserApi().addPerson(userInfo.convert2RequestBody()));
                    }

                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseHttpRxObserver<String>(getActivity()) {
                    @Override
                    protected void onSuccess(String personId) {
                        userInfo.setPersonId(personId);
                        DBHelper.getInstance().getUserInfoDao().save(userInfo);
                    }
                });
    }

    /**
     * 身份证
     */
    private void showIdCardDialog(IdInfo idInfo) {
        IdCardDialog instance = IdCardDialog.getInstance();
        instance.initView(getActivity())
                .setIdCardName(idInfo.getName())
                .setIdCardSex(idInfo.getSex())
                .setIdCardNation(idInfo.getNational())
                .setIdCardBirth(idInfo.getBirthday())
                .setIdCardAddress(idInfo.getAddress())
                .setIdCardNumber(idInfo.getIdCardNum())
                .setIdCardHead(idInfo.getPhotoBmp())
                .showDialog();
        new Handler().postDelayed(instance::dismiss, 3000);
    }

    @Override
    protected IAddPersonalModel initModule() {
        return new AddPersonalModel();
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    //获取到身份证信息
    @Override
    public void getIdInfo(IdInfo idInfo) {
        if (idInfo == null) {
            return;
        }
        this.idInfo = idInfo;
        showIdCardDialog(idInfo);

        Bitmap bitmap = idInfo.getPhotoBmp();
        if (bitmap != null) {
            Log.d("bm1: " + bitmap.getByteCount());

            long m1 = System.currentTimeMillis();
            //用身份证的照片提取特征
            FaceFeature faceFeature = FaceAlgoUtils.getImageFeature(bitmap);
            long m2 = System.currentTimeMillis();
            Log.e((m2 - m1) + " ms---身份证特征耗时--" + Thread.currentThread().getName());

            if (faceFeature != null) {
                idTime = System.currentTimeMillis();
                idCardFeature = faceFeature.getFeatureData();
                faceListenerUtil.setIdCardFeature(idCardFeature);
            }
        }
    }

    @Override
    public void callback(Bitmap bitmap) {

        refreshIDCard(idInfo, bitmap);
        //addPersontoDB();
    }

    private void refreshIDCard(IdInfo idInfo, Bitmap bitmap) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvPersonalName.setText(idInfo.getName());
                tvPersonalSex.setText(idInfo.getSex());
                tvPersonalNation.setText(idInfo.getNational());
                tvDateBirth.setText(idInfo.getBirthday());
                tvIdCardAddress.setText(idInfo.getAddress());
                tvIdCardNumber.setText(idInfo.getIdCardNum());
                ivPersonnelHead.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void notObtained() {

    }

    @Override
    public void onGlobalLayout() {
        texturePreview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        if (!PermissionUtil.checkPermissions(getActivity(), NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(getActivity(), NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
        } else {
            initEngine();
            initCamera();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
            boolean isAllGranted = true;
            for (int grantResult : grantResults) {
                isAllGranted &= (grantResult == PackageManager.PERMISSION_GRANTED);
            }
            if (isAllGranted) {
                initEngine();
                initCamera();
            } else {
                showToast(getString(R.string.permission_denied));
            }
        }
    }

    private void initCamera() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        initFaceListenerUtil();
        initCameraListenerUtil();

        cameraHelper = new CameraHelper.Builder()
                .previewViewSize(new Point(Constants.CAMERA_PREVIEW_WIDTH, Constants.CAMERA_PREVIEW_HEIGHT))
                .rotation(getActivity().getWindowManager().getDefaultDisplay().getRotation())
                .specificCameraId(Constants.DEFAULT_CAMERA_ID)
                .isMirror(false)
                .previewOn(texturePreview)
                .cameraListener(cameraListenerUtil)
                .build();
        cameraHelper.init();
        cameraHelper.start();
    }

    private CameraListenerUtil initCameraListenerUtil() {
        return cameraListenerUtil = new CameraListenerUtil(texturePreview)
                .setFrEngine(frEngine)
                .setFlEngine(flEngine)
                .setFtEngine(ftEngine)
                .setFaceRectView(faceRectView)
                .setFaceListener(faceListenerUtil);
    }

    private IdCardFaceListenerUtil initFaceListenerUtil() {
        faceListenerUtil = new IdCardFaceListenerUtil(this);
        faceListenerUtil.setAsyncFaceEngine(asyncFaceEngine);
        return faceListenerUtil;
    }

    private List<PersonImageBean> getList(){
        return DBHelper.getInstance().getPersonImageBeanDao().queryBuilder().list();
    }

    /**
     * 初始化引擎
     */
    private void initEngine() {
        ftEngine = faceUtil.initEngineVideo(getActivity());
        frEngine = faceUtil.initEngineImage(getActivity());
        flEngine = faceUtil.initEngineImageLiven(getActivity());
        asyncFaceEngine = faceUtil.initAsyncFaceEngine(getActivity());
    }

    private void unInitEngine() {
        if (ftEngine != null) {
            synchronized (ftEngine) {
                int ftUnInitCode = ftEngine.unInit();
                Log.i("unInitEngine: " + ftUnInitCode);
            }
        }
        if (frEngine != null) {
            synchronized (frEngine) {
                int frUnInitCode = frEngine.unInit();
                Log.i("unInitEngine: " + frUnInitCode);
            }
        }
        if (flEngine != null) {
            synchronized (flEngine) {
                int flUnInitCode = flEngine.unInit();
                Log.i("unInitEngine: " + flUnInitCode);
            }
        }
        if (asyncFaceEngine != null) {
            synchronized (asyncFaceEngine) {
                int flUnInitCode = asyncFaceEngine.unInit();
                Log.i("unInitEngine: " + flUnInitCode);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (cameraHelper != null) {
            cameraHelper.release();
            cameraHelper = null;
        }
        unInitEngine();
        IdCardHelper.getInstance().close();
        cameraListenerUtil.clearLeftFace(null );
        super.onDestroy();
    }

}
