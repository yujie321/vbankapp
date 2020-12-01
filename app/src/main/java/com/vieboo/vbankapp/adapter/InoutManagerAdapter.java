package com.vieboo.vbankapp.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.toollib.manager.GlideManager;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.PersonInOutVO;

import org.jetbrains.annotations.NotNull;

public class InoutManagerAdapter extends BaseQuickAdapter<PersonInOutVO, BaseViewHolder> implements LoadMoreModule {
    public InoutManagerAdapter() {
        super(R.layout.item_in_out_manager);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, PersonInOutVO personInOutVO) {
        ImageView ivInoutHead = viewHolder.getView(R.id.ivInoutHead);
        GlideManager.loadImage(getContext(), personInOutVO.getImageUrl(), ivInoutHead);

        TextView tvInoutName = viewHolder.getView(R.id.tvInoutName);
        tvInoutName.setText(personInOutVO.getPersonName());

        TextView tvInoutDate = viewHolder.getView(R.id.tvInoutDate);
        tvInoutDate.setText(personInOutVO.getTime());

        ImageView ivInoutVideo = viewHolder.getView(R.id.ivInoutVideo);

    }

    

}
