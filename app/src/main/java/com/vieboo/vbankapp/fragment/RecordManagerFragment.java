package com.vieboo.vbankapp.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.toollib.base.BaseListFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.adapter.RecordAdapter;
import com.vieboo.vbankapp.adapter.RecordPlanAdapter;
import com.vieboo.vbankapp.data.Record;
import com.vieboo.vbankapp.data.RecordPlan;
import com.vieboo.vbankapp.model.IRecordManagerModel;
import com.vieboo.vbankapp.model.IRecordManagerView;
import com.vieboo.vbankapp.model.impl.RecordManagerModel;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RecordManagerFragment extends BaseListFragment<IRecordManagerModel, RecordPlanAdapter> implements IRecordManagerView {
    @BindView(R.id.rvBaseRecordList)
    RecyclerView rvBaseRecordList;
    @BindView(R.id.refreshRecordList)
    SmartRefreshLayout refreshRecordList;
    @BindView(R.id.videoView1)
    VideoView videoView1;
    @BindView(R.id.videoView2)
    VideoView videoView2;
    @BindView(R.id.videoView3)
    VideoView videoView3;
    @BindView(R.id.videoView4)
    VideoView videoView4;
    @BindView(R.id.videoView5)
    VideoView videoView5;
    @BindView(R.id.videoView6)
    VideoView videoView6;
    private RecordPlanAdapter recordPlanAdapter;
    private RecordAdapter recordAdapter;

    public static RecordManagerFragment newInstance() {
        Bundle args = new Bundle();
        RecordManagerFragment fragment = new RecordManagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        recordAdapter = new RecordAdapter();
        View view = View.inflate(getActivity(), R.layout.item_record, null);
        recordAdapter.addHeaderView(view);

        refreshRecordList.setEnableRefresh(true);
        refreshRecordList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                iModule.requestRecordList();
            }
        });
        //Header标准高度（显示下拉高度>=标准高度 触发刷新）
        refreshRecordList.setHeaderHeight(100);
        refreshRecordList.setEnableLoadMoreWhenContentNotFull(true);
        if (rvBaseRecordList != null) {
            rvBaseRecordList.setLayoutManager(new LinearLayoutManager(getActivity()));
            if (recordAdapter != null) {
                rvBaseRecordList.setAdapter(recordAdapter);
                View notDataView = getLayoutInflater().inflate(R.layout.tool_lib_item_not_data, (ViewGroup) rvBaseRecordList.getParent(), false);
                recordAdapter.setEmptyView(notDataView);
                recordAdapter.getLoadMoreModule().setAutoLoadMore(getEnableLoadMore());
                //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
                recordAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
                recordAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                        Record record = (Record) view.findViewById(R.id.ivRecordControl).getTag();
                        iModule.requestRecordDetail(record.getId());
                    }
                });
                //baseListAdapter.getLoadMoreModule().setOnLoadMoreListener(this, rvBaseList);
                recordAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {

                    }
                });
            }
        }
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
        recordAdapter.setList(records);
    }

    @Override
    public void setRecordDetail(Record record) {
        Uri uri = Uri.parse(record.getRecordList().get(0).getFileUrl());
        videoView1.setVideoURI(uri);
        videoView1.setMediaController(null);
        videoView1.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                //播放结束后的动作
                videoView1.stopPlayback();
                videoView1.suspend();
            }
        });
        videoView1.start();
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
