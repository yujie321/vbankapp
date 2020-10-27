package com.example.toollib.base;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.example.toollib.R;
import com.example.toollib.R2;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.data.base.IBaseListView;
import com.example.toollib.enums.StaticExplain;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import io.reactivex.annotations.Nullable;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public class BaseListFragment<M extends IBaseModule, T extends BaseQuickAdapter> extends BaseFragment<M> implements OnRefreshListener, IBaseListView,
        OnItemClickListener, OnLoadMoreListener{

    @BindView(R2.id.refreshBaseList)
    SmartRefreshLayout refreshLayout;

    @Nullable
    @BindView(R2.id.rvBaseList)
    RecyclerView rvBaseList;

    /**
     * page 默认值
     */
    private int page = StaticExplain.PAGE_NUMBER.getCode();
    public T baseListAdapter;

    @Override
    public void initView() {
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(this);
        //Header标准高度（显示下拉高度>=标准高度 触发刷新）
        refreshLayout.setHeaderHeight(100);
        refreshLayout.setEnableLoadMoreWhenContentNotFull(true);
        if (rvBaseList != null) {
            rvBaseList.setLayoutManager(new LinearLayoutManager(getActivity()));
            baseListAdapter = getBaseListAdapter();
            if (baseListAdapter != null) {
                rvBaseList.setAdapter(baseListAdapter);
                View notDataView = getEmptyView();
                baseListAdapter.setEmptyView(notDataView);
                baseListAdapter.getLoadMoreModule().setAutoLoadMore(getEnableLoadMore());
                //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
                baseListAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
                baseListAdapter.setOnItemClickListener(this);
                //baseListAdapter.getLoadMoreModule().setOnLoadMoreListener(this, rvBaseList);
                baseListAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
            }
        }
        refresh();
    }

    public void refresh() {
        refreshLayout.autoRefresh();
    }

    public T getBaseListAdapter() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.tool_lib_fragment_base_list;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //刷新
        page = 1;
        if (baseListAdapter != null) {
            baseListAdapter.getLoadMoreModule().setEnableLoadMore(false);
        }
        onBaseRefresh(refreshLayout);
    }

    public void onBaseRefresh(RefreshLayout refreshLayout) {
        //刷新
    }

    @Override
    public void onLoadMore() {
        //上拉加载
        page++;
        //加载时 不能刷新
        refreshLayout.setEnableRefresh(false);
        onBaseLoadMoreRequested();
    }

    public void onBaseLoadMoreRequested() {
        //加载更多
    }

    @Override
    public void finishRefresh() {
        refreshLayout.finishRefresh();
        if (baseListAdapter != null) {
            //刷新完成 可以上拉加载
            baseListAdapter.getLoadMoreModule().setEnableLoadMore(getEnableLoadMore());
        }
    }

    @Override
    public void loadError() {
        if (getPage() > StaticExplain.PAGE_NUMBER.getCode()) {
            //加载失败 page -1
            page--;
        }
        loadMoreEnd();
    }

    @Override
    public void loadMoreEnd() {
        //加载完成 可以刷新
        if (baseListAdapter != null) {
            baseListAdapter.getLoadMoreModule().loadMoreEnd();
            baseListAdapter.getLoadMoreModule().setEnableLoadMore(false);
        }
        refreshLayout.setEnableRefresh(true);
    }

    @Override
    public void loadMoreComplete() {
        if (baseListAdapter != null) {
            baseListAdapter.getLoadMoreModule().loadMoreComplete();
        }
        refreshLayout.setEnableRefresh(true);
    }

    @Override
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    /**
     * 默认空布局
     * @return layout
     */
    private View getEmptyView() {
        return getLayoutInflater().inflate(R.layout.tool_lib_item_not_data, (ViewGroup) rvBaseList.getParent(), false);
    }

    /**
     * 是否开启上拉加载  默认开启
     * @return boolean
     */
    public boolean getEnableLoadMore(){
        return true;
    }

    public int getIvNotDataBackground(){
        return 0;
    }

    public String getTvNotData(){
        return "";
    }

    @Override
    protected M initModule() {
        return null;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
