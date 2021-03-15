package com.vieboo.vbankapp.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.toollib.base.BaseListFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.adapter.RecordPlanAdapter;
import com.vieboo.vbankapp.data.Record;
import com.vieboo.vbankapp.data.RecordPlan;
import com.vieboo.vbankapp.model.IRecordManagerModel;
import com.vieboo.vbankapp.model.IRecordManagerView;
import com.vieboo.vbankapp.model.impl.RecordManagerModel;

import java.util.List;

import butterknife.OnClick;

public class RecordManagerFragment  extends BaseListFragment<IRecordManagerModel, RecordPlanAdapter> implements IRecordManagerView {
    RecordPlanAdapter recordPlanAdapter;

    public static RecordManagerFragment newInstance() {
        Bundle args = new Bundle();
        RecordManagerFragment fragment = new RecordManagerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_record_manager;
    }


    @Override
    protected IRecordManagerModel initModule() {
        return new RecordManagerModel();
    }

    @Override
    public RecordPlanAdapter getBaseListAdapter() {
        recordPlanAdapter = new RecordPlanAdapter();
        View view = View.inflate(getActivity(), R.layout.item_record_plan, null);
        recordPlanAdapter.addHeaderView(view);
        return recordPlanAdapter;
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        iModule.requestRecordPlan();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        iModule.requestRecordPlan();
    }

    @Override
    public void refreshRecordPlan(List<RecordPlan> recordPlans) {
        recordPlanAdapter.setList(recordPlans);
    }

    @Override
    public void loadMoreRecordPlan(List<RecordPlan> recordPlans) {
        recordPlanAdapter.addData(recordPlans);
    }

    @Override
    public void setRecordList(List<Record> records) {

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
