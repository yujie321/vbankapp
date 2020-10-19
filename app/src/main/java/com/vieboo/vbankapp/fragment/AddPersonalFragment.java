package com.vieboo.vbankapp.fragment;

import android.os.Bundle;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.vieboo.vbankapp.R;

/**
 * 人员新增
 */
public class AddPersonalFragment extends BaseFragment {

    public static AddPersonalFragment newInstance() {
        Bundle args = new Bundle();
        AddPersonalFragment fragment = new AddPersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_add_peraonal;
    }

    @Override
    public void initView() {

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
