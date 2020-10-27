package com.vieboo.vbankapp.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.RegulationsVO;

import org.jetbrains.annotations.NotNull;

public class RegulationsAdapter extends BaseQuickAdapter<RegulationsVO, BaseViewHolder> implements LoadMoreModule {
    public RegulationsAdapter() {
        super(R.layout.item_regulations);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, RegulationsVO regulationsVO) {
        TextView tvRegulationsTitle = viewHolder.getView(R.id.tvRegulationsTitle);
        tvRegulationsTitle.setText(regulationsVO.getTitle());
        tvRegulationsTitle.setTag(regulationsVO);
    }
}
