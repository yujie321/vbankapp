package com.vieboo.vbankapp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.Record;

import org.jetbrains.annotations.NotNull;

public class RecordAdapter extends BaseQuickAdapter<Record, BaseViewHolder> {
    public RecordAdapter() {
        super(R.layout.item_record_plan);
    }
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Record record) {

    }
}
