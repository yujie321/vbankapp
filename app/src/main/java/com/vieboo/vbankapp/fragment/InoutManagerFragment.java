package com.vieboo.vbankapp.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toollib.base.BaseListFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.adapter.InoutManagerAdapter;
import com.vieboo.vbankapp.data.PersonInOutVO;
import com.vieboo.vbankapp.model.IInoutManagerModel;
import com.vieboo.vbankapp.model.IInoutManagerView;
import com.vieboo.vbankapp.model.impl.InoutManagerModel;
import com.vieboo.vbankapp.utils.GridSpacingItemDecoration;

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
        rvBaseList.setLayoutManager(new GridLayoutManager(getActivity(), 6));
        rvBaseList.addItemDecoration(new GridSpacingItemDecoration(6, 5, false));

        //实时进出记录
        rvAccessRecord.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        rvAccessRecord.addItemDecoration(new GridSpacingItemDecoration(7, 5, false));
        iModule.requestAccessPersonInOut();
    }

    @Override
    public void setAccessPersonInOut(List<PersonInOutVO> personInOutVOS) {
        //实时进出记录
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
    }

    @Override
    public void loadMorePersonInOut(List<PersonInOutVO> personInOutVOS) {
        //历史进出记录
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
