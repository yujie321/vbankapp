package com.vieboo.vbankapp.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.toollib.base.BaseListFragment;
import com.example.toollib.util.DateUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.adapter.InoutManagerAdapter;
import com.vieboo.vbankapp.data.ChartXY;
import com.vieboo.vbankapp.data.PersonInOutVO;
import com.vieboo.vbankapp.data.SecureRecordVo;
import com.vieboo.vbankapp.model.IInoutManagerModel;
import com.vieboo.vbankapp.model.IInoutManagerView;
import com.vieboo.vbankapp.model.impl.InoutManagerModel;
import com.vieboo.vbankapp.utils.LineChartUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class InoutManagerFragment extends BaseListFragment<IInoutManagerModel, InoutManagerAdapter> implements IInoutManagerView {

    @BindView(R.id.lineChart)
    LineChart lineChart;
    @BindView(R.id.rvAccessRecord)
    RecyclerView rvAccessRecord;
    @BindView(R.id.rvBaseList)
    RecyclerView rvBaseList;
    private InoutManagerAdapter inoutManagerAdapter;

    protected static String[] mLabels = new String[]{
            "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"
    };

    public static InoutManagerFragment newInstance() {
        Bundle args = new Bundle();
        InoutManagerFragment fragment = new InoutManagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_inout_manager;
    }

    @Override
    public void initView() {
        super.initView();
        rvBaseList.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        //rvBaseList.addItemDecoration(new GridSpacingItemDecoration(6, 5, false));

        //实时进出记录
        rvAccessRecord.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
       //rvAccessRecord.addItemDecoration(new GridSpacingItemDecoration(7, 5, false));
        iModule.requestAccessPersonInOut();

        //获取当日进出记录趋势图
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
        LineChartUtil.initLineChart(lineChart, datalist);
        iModule.getTodayPersonInoutStatic();
    }

    @Override
    public void setTodayPersoninoutStatic(List<SecureRecordVo> secureRecordVoList) {
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
                chartXYList.get(integerX).setChartY(secureRecordVo.getCount());
            }
        }
        LineChartUtil.setLineChartData(getActivity(), lineChart, chartXYList);
    }

    @Override
    public void setAccessPersonInOut(List<PersonInOutVO> personInOutVOS) {
        //实时进出记录
        InoutManagerAdapter inoutAccessPersonAdapter = new InoutManagerAdapter();
        inoutAccessPersonAdapter.setList(personInOutVOS);
        rvAccessRecord.setAdapter(inoutAccessPersonAdapter);
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        //刷新
        iModule.requestPersonInOut();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        //加载
        iModule.requestPersonInOut();
    }

    @Override
    public void refreshPersonInOut(List<PersonInOutVO> personInOutVOS) {
        //历史进出记录
        inoutManagerAdapter.setList(personInOutVOS);
    }

    @Override
    public void loadMorePersonInOut(List<PersonInOutVO> personInOutVOS) {
        //历史进出记录
        inoutManagerAdapter.addData(personInOutVOS);
    }


    @Override
    protected IInoutManagerModel initModule() {
        return new InoutManagerModel();
    }

    @Override
    public InoutManagerAdapter getBaseListAdapter() {
        inoutManagerAdapter = new InoutManagerAdapter();
        return inoutManagerAdapter;
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
