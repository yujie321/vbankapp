package com.vieboo.vbankapp.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.base.BaseListFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.adapter.RegulationsAdapter;
import com.vieboo.vbankapp.data.RegulationsVO;
import com.vieboo.vbankapp.model.IRegulationsModel;
import com.vieboo.vbankapp.model.IRegulationsView;
import com.vieboo.vbankapp.model.impl.RegulationsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RegulationsFragment extends BaseListFragment<IRegulationsModel, RegulationsAdapter> implements IRegulationsView {

    @BindView(R.id.searchView)
    EditText searchView;
    @BindView(R.id.rvBaseList)
    RecyclerView rvBaseList;

    private RegulationsAdapter regulationsAdapter;

    public static RegulationsFragment newInstance() {
        Bundle args = new Bundle();
        RegulationsFragment fragment = new RegulationsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_regulations;
    }

    @Override
    public void initView() {
        super.initView();
        rvBaseList.setLayoutManager(new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL));
        iModule.initData();
    }


    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        iModule.findRegulations();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        iModule.findRegulations();
    }

    @Override
    public void refreshRegulationsSuccess(List<RegulationsVO> regulationsVOList) {
        regulationsAdapter.setList(regulationsVOList);
    }

    @Override
    public void loadMoreRegulationsSuccess(List<RegulationsVO> regulationsVOList) {
        regulationsAdapter.addData(regulationsVOList);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        RegulationsVO regulationsVO = (RegulationsVO) view.findViewById(R.id.tvRegulationsTitle).getTag();
        startFragment(PDFFragment.newInstance(regulationsVO.getContent()));
    }

    @OnClick(R.id.ivRegulationsBack)
    public void onClick() {
        popBackStack();
    }

    @Override
    public RegulationsAdapter getBaseListAdapter() {
        regulationsAdapter = new RegulationsAdapter();
        return regulationsAdapter;
    }

    @Override
    public EditText getEditText() {
        return searchView;
    }
    @Override
    protected IRegulationsModel initModule() {
        return new RegulationsModel();
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
