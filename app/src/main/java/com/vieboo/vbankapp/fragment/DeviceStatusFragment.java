package com.vieboo.vbankapp.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.toollib.base.BaseListFragment;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.adapter.DeviceStatusAdapter;
import com.vieboo.vbankapp.data.VQDResultVO;
import com.vieboo.vbankapp.model.IDeviceStatusModel;
import com.vieboo.vbankapp.model.IDeviceStatusView;
import com.vieboo.vbankapp.model.impl.DeviceStatusModel;
import com.vieboo.vbankapp.utils.HorizontalBarChartUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设备状态
 */
public class DeviceStatusFragment extends BaseListFragment<IDeviceStatusModel, DeviceStatusAdapter> implements IDeviceStatusView {

    @BindView(R.id.horizontalBarChart)
    HorizontalBarChart horizontalBarChart;

    private DeviceStatusAdapter deviceStatusAdapter;

    public static DeviceStatusFragment newInstance() {
        Bundle args = new Bundle();
        DeviceStatusFragment fragment = new DeviceStatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_device_status;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        //刷新
        iModule.requestVQD();
        HorizontalBarChartUtil.initHorizontalBarChart(horizontalBarChart);
        iModule.getDeviceStatus();
    }

    @Override
    public void setDeviceStatus() {
        HorizontalBarChartUtil.setHorizontalBarChartData(getActivity(), horizontalBarChart);
    }

    @Override
    public void onBaseLoadMoreRequested() {
        //加载
        iModule.requestVQD();
    }

    @Override
    public void refreshVQD(List<VQDResultVO> vqdResultVOS) {
        deviceStatusAdapter.setList(vqdResultVOS);
    }

    @Override
    public void loadMoreVQD(List<VQDResultVO> vqdResultVOS) {
        deviceStatusAdapter.addData(vqdResultVOS);
    }



    @Override
    public DeviceStatusAdapter getBaseListAdapter() {
        deviceStatusAdapter = new DeviceStatusAdapter();
        View view = View.inflate(getActivity(), R.layout.item_device_status, null);
        deviceStatusAdapter.addHeaderView(view);
        return deviceStatusAdapter;
    }


    @OnClick(R.id.ivBack)
    public void onClick() {
        popBackStack();
    }

    @Override
    protected IDeviceStatusModel initModule() {
        return new DeviceStatusModel();
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
