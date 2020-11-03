package com.vieboo.vbankapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.util.DateUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.ChartXY;
import com.vieboo.vbankapp.data.PassengerSummeryVo;
import com.vieboo.vbankapp.data.SecureRecordVo;
import com.vieboo.vbankapp.model.IBranchOperateModel;
import com.vieboo.vbankapp.model.IBranchOperateView;
import com.vieboo.vbankapp.model.impl.BranchOperateModel;
import com.vieboo.vbankapp.utils.DoubleLineChartUtil;
import com.vieboo.vbankapp.utils.LineChartUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    @BindView(R.id.tvFrontPassenger)
    TextView tvFrontPassenger;
    @BindView(R.id.tvMonthPassenger)
    TextView tvMonthPassenger;
    @BindView(R.id.tvMonthAllPassenger)
    TextView tvMonthAllPassenger;
    @BindView(R.id.tvFrontSeasonPassenger)
    TextView tvFrontSeasonPassenger;
    @BindView(R.id.tvBranchDate)
    TextView tvBranchDate;
    @BindView(R.id.relBranch)
    LinearLayout relBranch;
    @BindView(R.id.lineChart2)
    LineChart lineChart2;
    @BindView(R.id.lineChart)
    LineChart lineChart;

    protected static String[] m7DayLabels = new String[]{
            "周一", "周二", "周三", "周四", "周五", "周六", "周日"
    };

    protected static String[] mLabels = new String[]{
            "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"
    };

    List<ChartXY> mChartXYList;
    List<ChartXY> mVipchartXYList;
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
        mChartXYList = new ArrayList<>();
        mVipchartXYList = new ArrayList<>();
        for(int i = 0; i< 61; i++){
            ChartXY chartXY = new ChartXY();
            chartXY.setChartX(i);
            chartXY.setChartY(0);
            mChartXYList.add(chartXY);
            mVipchartXYList.add(chartXY);
        }

        LineChartUtil.initLineChart(lineChart, m7DayLabels);
        DoubleLineChartUtil.initDoubleLineChart(lineChart2, mLabels);
        iModule.getTodayPassengerSummery();
        iModule.getLast7dayPeriodStatic();
        iModule.getTodayPassengerStatic();
        iModule.getTodayVipPassengerStatic();

    }

    @Override
    public void setLast7dayPeriodStatic(List<SecureRecordVo> secureRecordVoList) {
//        List<ChartXY> chartXYList = new ArrayList<>();
//        chartXYList = new ArrayList<>();
//        for(int i = 0; i< 61; i++){
//            ChartXY chartXY = new ChartXY();
//            chartXY.setChartX(i);
//            chartXY.setChartY(0);
//            chartXYList.add(chartXY);
//        }
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.HOUR_OF_DAY, 8);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.MILLISECOND, 0);
//        long zeroTime = cal.getTimeInMillis();
//        if (secureRecordVoList != null && secureRecordVoList.size() > 0) {
//            for (SecureRecordVo secureRecordVo : secureRecordVoList) {
//                long date = DateUtil.dateToCurrentTimeMilli(secureRecordVo.getTime(),"yyyy-MM-dd HH:mm:ss");
//                Integer integerX = (int)(date - zeroTime)/(10*60*1000);
//                chartXYList.get(integerX).setChartY(secureRecordVo.getCount());
//            }
//        }
//        LineChartUtil.setLineChartData(getActivity(), lineChart, chartXYList);
    }

    @Override
    public void setTodayPassengerStatic(List<SecureRecordVo> secureRecordVoList) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long zeroTime = cal.getTimeInMillis();
        if (secureRecordVoList != null && secureRecordVoList.size() > 0) {
            for (SecureRecordVo secureRecordVo : secureRecordVoList) {
                long date = DateUtil.dateToCurrentTimeMilli(secureRecordVo.getTime(),"yyyy-MM-dd HH:mm:ss");
                Integer integerX = (int)(date - zeroTime)/(10*60*1000);
                mChartXYList.get(integerX).setChartY(secureRecordVo.getCount());
            }
        }
        DoubleLineChartUtil.setTodayPassengerStatic(getActivity(), lineChart2, mChartXYList, mVipchartXYList);
    }

    @Override
    public void setTodayVipPassengerStatic(List<SecureRecordVo> secureRecordVoList) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long zeroTime = cal.getTimeInMillis();
        if (secureRecordVoList != null && secureRecordVoList.size() > 0) {
            for (SecureRecordVo secureRecordVo : secureRecordVoList) {
                long date = DateUtil.dateToCurrentTimeMilli(secureRecordVo.getTime(),"yyyy-MM-dd HH:mm:ss");
                Integer integerX = (int)(date - zeroTime)/(10*60*1000);
                mVipchartXYList.get(integerX).setChartY(secureRecordVo.getCount());
            }
        }
        //DoubleLineChartUtil.setTodayPassengerStatic(getActivity(), lineChart2, mChartXYList, mVipchartXYList);
    }

    @Override
    public void setTodayPassengerSummery(PassengerSummeryVo passengerSummeryVo) {
        tvBranchPersonNum.setText(String.valueOf(passengerSummeryVo.getPassengernum()));
        tvBranchVIP.setText(String.valueOf(passengerSummeryVo.getVipnum()));
        tvManyVisitsPerson.setText(String.valueOf(passengerSummeryVo.getMultivisitsnum()));
        tvFrontPassenger.setText(String.valueOf(passengerSummeryVo.getLastdaypassengernum()));
        tvMonthPassenger.setText(String.valueOf(passengerSummeryVo.getLastmonthtodaynum()));
        tvMonthAllPassenger.setText(String.valueOf(passengerSummeryVo.getLastmonthavgnum()));
        tvFrontSeasonPassenger.setText(String.valueOf(passengerSummeryVo.getLast90dayavgnum()));
        String currenttime = TimeUtils.date2String(new Date(System.currentTimeMillis()), new SimpleDateFormat("yyyy-MM-dd"));
        tvBranchDate.setText(currenttime);
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
