package com.vieboo.vbankapp.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.example.toollib.base.BaseFragment;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.model.ISplashModel;
import com.vieboo.vbankapp.model.ISplashView;
import com.vieboo.vbankapp.model.impl.SplashModel;
import com.vieboo.vbankapp.utils.Constants;
import com.vieboo.vbankapp.utils.FaceAlgoUtils;

import butterknife.BindView;

/**
 * 启动过渡
 */
public class SplashFragment extends BaseFragment<ISplashModel> implements ISplashView {

    @BindView(R.id.tvVersion)
    TextView tvVersion;

    private boolean libraryExists = true;

    public static SplashFragment newInstance() {
        Bundle args = new Bundle();
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_splash;
    }

    @Override
    protected ISplashModel initModule() {
        return new SplashModel();
    }

    @Override
    public void initView() {
        initFaceLib();
    }

    private void initFaceLib() {
        tvVersion.setText(getString(R.string.version , AppUtils.getAppVersionName()));
        if (getActivity() != null) {
            libraryExists = FaceAlgoUtils.checkSoFile(getActivity());
        }
        iModule.activeEngine();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isAllGranted = true;
        for (int grantResult : grantResults) {
            isAllGranted &= (grantResult == PackageManager.PERMISSION_GRANTED);
        }
        if (requestCode == Constants.ACTION_REQUEST_PERMISSIONS) {
            if (isAllGranted) {
                iModule.activeEngine();
            } else {
                showToast(getString(R.string.permission_denied));
            }
        }
    }

    @Override
    public void startHomeFragment() {
        startFragmentAndDestroyCurrent(HomeFragment.newInstance());
    }

    @Override
    public void startActiveDeviceFragment() {
        startFragmentAndDestroyCurrent(ActiveDeviceFragment.newInstance());
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
    public boolean getLibraryExists() {
        return libraryExists;
    }

}
