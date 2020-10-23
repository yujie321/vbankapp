package com.vieboo.vbankapp.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
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
import com.example.toollib.util.DensityUtil;
import com.example.toollib.util.Log;
import com.sdses.idCard.IdCardHelper;
import com.sdses.idCard.IdInfo;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.adapter.SpinnerAdapter;
import com.vieboo.vbankapp.data.PersonImageBean;
import com.vieboo.vbankapp.data.SpinnerVO;
import com.vieboo.vbankapp.data.db.DBHelper;
import com.vieboo.vbankapp.face.CameraHelper;
import com.vieboo.vbankapp.face.FaceRectView;
import com.vieboo.vbankapp.face.util.CameraListenerUtil;
import com.vieboo.vbankapp.face.util.FaceUtil;
import com.vieboo.vbankapp.face.util.IdCardFaceListenerUtil;
import com.vieboo.vbankapp.face.util.IdCardFaceView;
import com.vieboo.vbankapp.model.IAddPersonalModel;
import com.vieboo.vbankapp.model.IAddPersonalView;
import com.vieboo.vbankapp.model.impl.AddPersonalModel;
import com.vieboo.vbankapp.utils.Constants;
import com.vieboo.vbankapp.utils.FaceAlgoUtils;
import com.vieboo.vbankapp.utils.PermissionUtil;
import com.vieboo.vbankapp.weight.IdCardDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
        //DBHelper.getInstance().getUserInfoDao();
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
        refreshIDCard(idInfo);
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
//                FaceFeature faceFeature1 = faceListenerUtil.getFaceFeature();
//                FaceSimilar faceSimilar = faceListenerUtil.compareVideoSimilar(idCardFeature, faceFeature1.getFeatureData());
//                float maxSimilar = 0f;
//                if (faceSimilar != null) {
//                    if (faceSimilar.getScore() >= Constants.FACE_MIN_SIMLAR && faceSimilar.getScore() > maxSimilar) {
//                        maxSimilar = faceSimilar.getScore();
//                    }
//                    Log.i("checkingFace:姓名:"  + "-相似度:" + faceSimilar.getScore());
//                }
            }
        }
    }

    private void refreshIDCard(IdInfo idInfo) {
        tvPersonalName.setText(idInfo.getName());
        tvPersonalSex.setText(idInfo.getSex());
        tvPersonalNation.setText(idInfo.getNational());
        tvDateBirth.setText(idInfo.getBirthday());
        tvIdCardAddress.setText(idInfo.getAddress());
        tvIdCardNumber.setText(idInfo.getIdCardNum());
        ivPersonnelHead.setImageBitmap(idInfo.getPhotoBmp());
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
        faceListenerUtil.setDutyPersonFeatureList(getDutyPersonFeatureList());
        return faceListenerUtil;
    }

    private List<PersonImageBean> getDutyPersonFeatureList(){
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        IdCardHelper.getInstance().close();
    }

    @Override
    public void callback() {
        int refffff = 0;
    }
}
