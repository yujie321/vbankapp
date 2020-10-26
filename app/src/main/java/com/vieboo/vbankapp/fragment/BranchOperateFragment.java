package com.vieboo.vbankapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.github.mikephil.charting.charts.LineChart;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.SecureRecordVo;
import com.vieboo.vbankapp.model.IBranchOperateModel;
import com.vieboo.vbankapp.model.IBranchOperateView;
import com.vieboo.vbankapp.model.impl.BranchOperateModel;
import com.vieboo.vbankapp.utils.DoubleLineChartUtil;
import com.vieboo.vbankapp.utils.LineChartUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 网点运营
 */
public class BranchOperateFragment extends BaseFragment<IBranchOperateModel> implements IBranchOperateView {


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
        LineChartUtil.initLineChart(lineChart);
        DoubleLineChartUtil.initDoubleLineChart(lineChart2);
        iModule.getLast7dayPeriodStatic();
        iModule.getTodayPassengerStatic();
    }

    @Override
    public void setLast7dayPeriodStatic(List<SecureRecordVo> secureRecordVoList) {
        LineChartUtil.setLineChartData(getActivity(), lineChart, secureRecordVoList);
    }

    @Override
    public void setTodayPassengerStatic(List<SecureRecordVo> secureRecordVoList) {
        DoubleLineChartUtil.setTodayPassengerStatic(getActivity(), lineChart2, secureRecordVoList);
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        popBackStack();
    }

    @Override
    protected IBranchOperateModel initModule() {
        return new BranchOperateModel() ;
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
