package com.vieboo.vbankapp.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.TextureView;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.arcsoft.face.FaceEngine;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.util.Log;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.adapter.PunchRecordAdapter;
import com.vieboo.vbankapp.adapter.StaffStyleAdapter;
import com.vieboo.vbankapp.data.PersonImageBean;
import com.vieboo.vbankapp.data.PunchRecordVO;
import com.vieboo.vbankapp.data.RecordVO;
import com.vieboo.vbankapp.data.UserInfo;
import com.vieboo.vbankapp.data.db.DBHelper;
import com.vieboo.vbankapp.face.CameraHelper;
import com.vieboo.vbankapp.face.FaceRectView;
import com.vieboo.vbankapp.face.util.CameraListenerUtil;
import com.vieboo.vbankapp.face.util.FaceListenerUtil;
import com.vieboo.vbankapp.face.util.FaceUtil;
import com.vieboo.vbankapp.face.util.IdCardFaceListenerUtil;
import com.vieboo.vbankapp.model.IPersonnelControlModel;
import com.vieboo.vbankapp.model.IPersonnelControlView;
import com.vieboo.vbankapp.model.impl.PersonnelControlModel;
import com.vieboo.vbankapp.utils.Constants;
import com.vieboo.vbankapp.utils.PermissionUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.vieboo.vbankapp.utils.Constants.ACTION_REQUEST_PERMISSIONS;

/**
 * 人员管控
 */
public class PersonnelControlFragment extends BaseFragment<IPersonnelControlModel> implements IPersonnelControlView , ViewTreeObserver.OnGlobalLayoutListener{
    @BindView(R.id.texture_preview)
    TextureView texturePreview;
    @BindView(R.id.faceRectView)
    FaceRectView faceRectView;
    @BindView(R.id.rvStaffStyle)
    RecyclerView rvStaffStyle;
    @BindView(R.id.rvPunchRecord)
    RecyclerView rvPunchRecord;

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
    private FaceListenerUtil faceListenerUtil;

    /**
     * 所需的所有权限信息
     */
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE
    };

    public static PersonnelControlFragment newInstance() {
        Bundle args = new Bundle();
        PersonnelControlFragment fragment = new PersonnelControlFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IPersonnelControlModel initModule() {
        return new PersonnelControlModel();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_personnel_control;
    }

    @Override
    public void initView() {
        faceUtil = new FaceUtil();
        //人脸识别相关
        texturePreview.getViewTreeObserver().addOnGlobalLayoutListener(this);
        //员工风采
        rvStaffStyle.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        //rvStaffStyle.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, false));
        iModule.getStaffStyle();
        //打卡记录
        rvPunchRecord.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        //rvPunchRecord.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, false));
        iModule.getPunchRecord();
    }

    @OnClick({R.id.ivBack, R.id.ivClockIn, R.id.ivClockOut, R.id.ivAddPersonal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                //返回
                popBackStack();
                break;
            case R.id.ivClockIn:
                //上班打卡
                iModule.clockIn();
                break;
            case R.id.ivClockOut:
                //下班打卡
                iModule.clockOut();
                break;
            case R.id.ivAddPersonal:
                //新增人员
                startFragment(AddPersonalFragment.newInstance());
                break;
        }
    }

    @Override
    public void setStaffStyle(List<PunchRecordVO> passengerVO) {
        //员工风采
        StaffStyleAdapter staffStyleAdapter = new StaffStyleAdapter();
        staffStyleAdapter.addData(passengerVO);
        rvStaffStyle.setAdapter(staffStyleAdapter);
    }

    @Override
    public void setPunchRecord(List<RecordVO> recordVOS) {
        PunchRecordAdapter passengerVO = new PunchRecordAdapter();
        passengerVO.addData(recordVOS);
        rvPunchRecord.setAdapter(passengerVO);
    }

    @Override
    public String getPersonalId() {
        // TODO: 2020/10/19 人脸比对成功后 返回 id
        return null;
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

    private FaceListenerUtil initFaceListenerUtil() {
        faceListenerUtil = new FaceListenerUtil();
        faceListenerUtil.setAsyncFaceEngine(asyncFaceEngine);
        faceListenerUtil.setUserInfoList(getUserInfoLIst());
        return faceListenerUtil;
    }

    private List<UserInfo> getUserInfoLIst(){
        return DBHelper.getInstance().getUserInfoDao().queryBuilder().list();
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
        super.onDestroy();
    }
}
