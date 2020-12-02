package com.vieboo.vbankapp.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.toollib.base.BaseListFragment;
import com.example.toollib.util.DateUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.adapter.SecurityOperateAdapter;
import com.vieboo.vbankapp.data.ChartXY;
import com.vieboo.vbankapp.data.SecureRecordVo;
import com.vieboo.vbankapp.data.SecureResultVO;
import com.vieboo.vbankapp.model.ISecurityOperateModel;
import com.vieboo.vbankapp.model.ISecurityOperateView;
import com.vieboo.vbankapp.model.impl.SecurityOperateModel;
import com.vieboo.vbankapp.utils.LineChartUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 安保运营
 */
public class SecurityOperateFragment extends BaseListFragment<ISecurityOperateModel, SecurityOperateAdapter> implements ISecurityOperateView {

    @BindView(R.id.rvBaseList)
    RecyclerView rvBaseList;
    @BindView(R.id.lineChart)
    LineChart lineChart;
    private SecurityOperateAdapter securityOperateAdapter;

    protected static String[] mLabels = new String[]{
            "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"
    };

    public static SecurityOperateFragment newInstance() {
        Bundle args = new Bundle();
        SecurityOperateFragment fragment = new SecurityOperateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_security_operate;
    }

    @Override
    public void initView() {
        super.initView();
        rvBaseList.setLayoutManager(new StaggeredGridLayoutManager(6, StaggeredGridLayoutManager.VERTICAL));
        //rvBaseList.addItemDecoration(new GridSpacingItemDecoration(5, 5, false));
        List<String> datalist = new ArrayList<>();
        for(int i = 0; i< 10; i++){
            for(int j = 0; j< 6; j++){
                if(j!=0){
                    datalist.add("");
                }else {
                    String dateStr = mLabels[i];
                    datalist.add(dateStr);
                }
            }
        }
        String tmpDate = mLabels[10];
        datalist.add(tmpDate);
        LineChartUtil.initLineChart(lineChart, datalist, datalist.size());
        iModule.getTodaySecureStatic();
    }

    @Override
    public void setSecureStatic(List<SecureRecordVo> secureRecordVoList) {
        List<ChartXY> chartXYList = new ArrayList<>();
        chartXYList = new ArrayList<>();
        for(int i = 0; i< 61; i++){
            ChartXY chartXY = new ChartXY();
            chartXY.setChartX(i);
            chartXY.setChartY(0);
            chartXYList.add(chartXY);
        }
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
                if(integerX>= 0 && integerX < 61){
                    chartXYList.get(integerX).setChartY(secureRecordVo.getCount());
                }
            }
        }
        LineChartUtil.setLineChartData(getActivity(), lineChart, chartXYList);
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        //刷新
        iModule.requestSecure();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        //加载
        iModule.requestSecure();
    }

    @Override
    public void refreshSecure(List<SecureResultVO> secureResultVOS) {
        securityOperateAdapter.setList(secureResultVOS);
    }

    @Override
    public void loadMoreSecure(List<SecureResultVO> secureResultVOS) {
        securityOperateAdapter.addData(secureResultVOS);
    }



    @Override
    protected SecurityOperateModel initModule() {
        return new SecurityOperateModel();
    }

    @Override
    public SecurityOperateAdapter getBaseListAdapter() {
        securityOperateAdapter = new SecurityOperateAdapter();
        return securityOperateAdapter;
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        popBackStack();
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
