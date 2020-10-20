package com.vieboo.vbankapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.util.DensityUtil;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.adapter.SpinnerAdapter;
import com.vieboo.vbankapp.data.SpinnerVO;
import com.vieboo.vbankapp.face.FaceRectView;
import com.vieboo.vbankapp.model.IAddPersonalModel;
import com.vieboo.vbankapp.model.IAddPersonalView;
import com.vieboo.vbankapp.model.impl.AddPersonalModel;
import com.vieboo.vbankapp.weight.IdCardDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 人员新增
 */
public class AddPersonalFragment extends BaseFragment<IAddPersonalModel> implements IAddPersonalView {

    @BindView(R.id.faceRectView)
    FaceRectView faceRectView;
    @BindView(R.id.ivPersonnelHead)
    ImageView ivPersonnelHead;
    @BindView(R.id.tvPersonalName)
    TextView tvPersonalName;
    @BindView(R.id.tvPersonalSex)
    TextView tvPersonalSex;
    @BindView(R.id.spinnerDepartment)
    Spinner spinnerDepartment;
    @BindView(R.id.spinnerPositions)
    Spinner spinnerPositions;
    @BindView(R.id.spinnerJurisdiction)
    Spinner spinnerJurisdiction;

    private int dropDownVerticalOffset = DensityUtil.dp2px(32f);

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
        //获取部门、职务、权限
        iModule.initData();
        //人脸识别相关
        initFace();
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
                break;
            case R.id.btnClose:
                showToast("取消");
                break;
        }
    }

    /**
     * 身份证
     */
    private void showIdCardDialog() {
        IdCardDialog instance = IdCardDialog.getInstance();
        instance.initView(getActivity())
                .setIdCardName("黄光广")
                .setIdCardSex("男")
                .setIdCardNation("汉")
                .setIdCardBirth("1978", "3", "10")
                .setIdCardAddress("北京市海定区姑姑咯咯社区明前街道")
                .setIdCardNumber("350102100000001200")
                .setIdCardHead("https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3521319392,1160740190&fm=26&gp=0.jpg")
                .showDialog();
        new Handler().postDelayed(instance::dismiss, 3000);
    }

    private void initFace() {

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


}
