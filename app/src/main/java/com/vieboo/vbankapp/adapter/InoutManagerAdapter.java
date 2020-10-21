package com.vieboo.vbankapp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.PersonInOutVO;

import org.jetbrains.annotations.NotNull;

public class InoutManagerAdapter extends BaseQuickAdapter<PersonInOutVO, BaseViewHolder> implements LoadMoreModule {
    public InoutManagerAdapter() {
        super(R.layout.item_in_out_manager);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, PersonInOutVO personInOutVO) {

    }
}
