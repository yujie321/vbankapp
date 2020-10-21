package com.vieboo.vbankapp.fragment;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.github.mikephil.charting.charts.LineChart;
import com.vieboo.vbankapp.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 网点运营
 */
public class BranchOperateFragment extends BaseFragment {


    @BindView(R.id.tvBranchPersonNum)
    TextView tvBranchPersonNum;
    @BindView(R.id.tvBranchVIP)
    TextView tvBranchVIP;
    @BindView(R.id.tvManyVisitsPerson)
    TextView tvManyVisitsPerson;
    @BindView(R.id.tvMonthPassenger)
    TextView tvMonthPassenger;
    @BindView(R.id.tvMonthAllPassenger)
    TextView tvMonthAllPassenger;
    @BindView(R.id.tvFrontSeasonPassenger)
    TextView tvFrontSeasonPassenger;
    @BindView(R.id.relBranch)
    LinearLayout relBranch;
    @BindView(R.id.lineChart2)
    LineChart lineChart2;
    @BindView(R.id.lineChart)
    LineChart lineChart;

    public static BranchOperateFragment newInstance() {
        Bundle args = new Bundle();
        BranchOperateFragment fragment = new BranchOperateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_branch_operate;
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        popBackStack();
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
