package com.vieboo.vbankapp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.Navigation;

import org.jetbrains.annotations.NotNull;

public class NavigationAdapter extends BaseQuickAdapter<Navigation, BaseViewHolder> {

    public NavigationAdapter() {
        super(R.layout.item_navigation);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Navigation navigation) {
        baseViewHolder.getView(R.id.ivNavigation).setBackgroundResource(navigation.getIcon());
    }
}
