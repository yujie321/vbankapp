package com.vieboo.vbankapp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.RecordPlan;

import org.jetbrains.annotations.NotNull;

public class RecordPlanAdapter extends BaseQuickAdapter<RecordPlan, BaseViewHolder> {
    public RecordPlanAdapter() {
        super(R.layout.item_record_plan);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RecordPlan recordPlan) {

    }
}
