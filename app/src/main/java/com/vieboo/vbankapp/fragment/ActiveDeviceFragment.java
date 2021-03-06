package com.vieboo.vbankapp.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.blankj.utilcode.util.ToastUtils;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.service.FaceServer;
import com.vieboo.vbankapp.utils.Constants;
import com.vieboo.vbankapp.utils.FaceAlgoUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ActiveDeviceFragment extends BaseFragment {

    @BindView(R.id.etActiveKey)
    TextView etActiveKey;
    public static ActiveDeviceFragment newInstance() {
        Bundle args = new Bundle();
        ActiveDeviceFragment fragment = new ActiveDeviceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_active_device;
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.btnSubmit)
    public void onClick() {
        //激活
        String key = etActiveKey.getText().toString();
        if (TextUtils.isEmpty(key)) {
            ToastUtils.showShort("请输入授权码");
        }

        int activeCode = FaceEngine.activeOnline(getActivity(), key, Constants.APP_ID, Constants.SDK_KEY);
        if(activeCode == ErrorInfo.MOK){
            boolean isExist = FaceAlgoUtils.copyLicenseCodeToCard(getActivity());
            ToastUtils.showShort(getString(R.string.active_success));
            //重新初始化同步服务的算法引擎
            FaceServer.getInstance().initEngine(getActivity());
            startFragmentAndDestroyCurrent(HomeFragment.newInstance());

        } else if (activeCode == ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
            ToastUtils.showShort(getString(R.string.already_activated));
        } else {
            ToastUtils.showShort(getString(R.string.active_failed, activeCode));
        }

    }

    @Override
    protected IBaseModule initModule() {
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
}
