package com.vieboo.vbankapp.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.toollib.base.BaseListFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.adapter.SecurityOperateAdapter;
import com.vieboo.vbankapp.data.SecureResultVO;
import com.vieboo.vbankapp.model.ISecurityOperateModel;
import com.vieboo.vbankapp.model.ISecurityOperateView;
import com.vieboo.vbankapp.model.impl.SecurityOperateModel;

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
        rvBaseList.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        //rvBaseList.addItemDecoration(new GridSpacingItemDecoration(5, 5, false));
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