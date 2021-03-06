package com.vieboo.vbankapp.fragment;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.util.DateUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.ChartXY;
import com.vieboo.vbankapp.data.PassengerSummeryVo;
import com.vieboo.vbankapp.data.SecureRecordVo;
import com.vieboo.vbankapp.data.TodyPassengerVO;
import com.vieboo.vbankapp.model.IBranchOperateModel;
import com.vieboo.vbankapp.model.IBranchOperateView;
import com.vieboo.vbankapp.model.impl.BranchOperateModel;
import com.vieboo.vbankapp.utils.DoubleLineChartUtil;
import com.vieboo.vbankapp.utils.LineChartUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        }
        for(int i = 0; i< 61; i++){
            ChartXY chartXY = new ChartXY();
            chartXY.setChartX(i);
            chartXY.setChartY(0);
            mVipchartXYList.add(chartXY);
        }

        List<String> datalist = new ArrayList<>();
        for(int i = 0; i< 7; i++){
            for(int j = 0; j< 10; j++){
                if(j!=0){
                    datalist.add("");
                }else {
                    String dateStr = m7DayLabels[i];
                    datalist.add(dateStr);
                }
            }
        }

        List<String> datalist2 = new ArrayList<>();
        for(int i = 0; i< 10; i++){
            for(int j = 0; j< 6; j++){
                if(j!=0){
                    datalist2.add("");
                }else {
                    String dateStr = mLabels[i];
                    datalist2.add(dateStr);
                }
            }
        }
        String tmpDate = mLabels[10];
        datalist2.add(tmpDate);

        LineChartUtil.initLineChart(lineChart, datalist, 11);
        DoubleLineChartUtil.initDoubleLineChart(lineChart2, datalist2);
        iModule.getTodayPassengerSummery();
        iModule.getLast7dayPeriodStatic();
        iModule.getTodayPassengerStatic();

    }

    @Override
    public void setLast7dayPeriodStatic(List<SecureRecordVo> secureRecordVoList) {
        Map<String, ChartXY> chartXYMap = new HashMap<>();
        List<ChartXY> chartXYList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        for(int i = 0; i< 7; i++){
            c.setTime(new Date());
            c.add(Calendar.DATE, i-6);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.MILLISECOND, 0);
            Date zeroDate = c.getTime();

            for(int j = 0; j< 10; j++){
                c.set(Calendar.HOUR_OF_DAY, 8 + j);
                String dateStr = DateUtil.dateToString(c.getTime(), "yyyy-MM-dd HH:mm:ss");
                ChartXY chartXY = new ChartXY();
                chartXY.setChartX(i*10 + j);
                chartXY.setChartY(0);
                chartXYList.add(chartXY);
                chartXYMap.put(dateStr, chartXY);
            }
        }

        if (secureRecordVoList != null && secureRecordVoList.size() > 0) {
            for (SecureRecordVo secureRecordVo : secureRecordVoList) {
                if( chartXYMap.get(secureRecordVo.getTime()) != null){
                    chartXYMap.get(secureRecordVo.getTime()).setChartY(secureRecordVo.getCount());
                }
            }
        }
        LineChartUtil.setLineChartData(getActivity(), lineChart, chartXYList);
    }

    @Override
    public void setTodayPassenger(TodyPassengerVO todyPassengerVO) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long zeroTime = cal.getTimeInMillis();
        if (todyPassengerVO.getTodayPassengerStatic() != null && todyPassengerVO.getTodayPassengerStatic().size() > 0) {
            for (SecureRecordVo secureRecordVo : todyPassengerVO.getTodayPassengerStatic()) {
                long date = DateUtil.dateToCurrentTimeMilli(secureRecordVo.getTime(),"yyyy-MM-dd HH:mm:ss");
                Integer integerX = (int)(date - zeroTime)/(10*60*1000);
                if(integerX >= 0 && integerX < 61){
                    mChartXYList.get(integerX).setChartY(secureRecordVo.getCount());
                }
            }
        }

        if (todyPassengerVO.getTodayVipPassengerStatic() != null && todyPassengerVO.getTodayVipPassengerStatic().size() > 0) {
            for (SecureRecordVo secureRecordVo : todyPassengerVO.getTodayVipPassengerStatic()) {
                long date = DateUtil.dateToCurrentTimeMilli(secureRecordVo.getTime(),"yyyy-MM-dd HH:mm:ss");
                Integer integerX = (int)(date - zeroTime)/(10*60*1000);
                if(integerX >= 0 && integerX < 61) {
                    mVipchartXYList.get(integerX).setChartY(secureRecordVo.getCount());
                }
            }
        }

        DoubleLineChartUtil.setTodayPassengerStatic(getActivity(), lineChart2, mChartXYList, mVipchartXYList);
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
