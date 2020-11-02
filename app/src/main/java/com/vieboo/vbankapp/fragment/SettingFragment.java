package com.vieboo.vbankapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.util.spf.SPUtils;
import com.example.toollib.util.spf.SpfConst;
import com.vieboo.vbankapp.R;

import butterknife.BindView;
import butterknife.OnClick;

import static com.vieboo.vbankapp.application.VBankAppApplication.BASE_URL;

public class SettingFragment extends BaseFragment {

    @BindView(R.id.tvServerApiUri)
    EditText tvServerApiUri;
    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initView() {
        tvServerApiUri.setText(SPUtils.getInstance().getString(SpfConst.BASE_URL, BASE_URL));
    }

    @OnClick({R.id.btnSubmit, R.id.btnResetData})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSubmit: {
                SPUtils.getInstance().put(SpfConst.BASE_URL,tvServerApiUri.getText().toString());
                setFragmentResult(1 , new Intent());
                popBackStack();
                break;
            }
            case R.id.btnResetData: {
                popBackStack();
                break;
            }
        }
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
    protected IBaseModule initModule() {
        return null;
    }


}
