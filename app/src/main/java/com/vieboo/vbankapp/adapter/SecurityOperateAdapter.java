package com.vieboo.vbankapp.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.SecureResultVO;

import org.jetbrains.annotations.NotNull;

public class SecurityOperateAdapter extends BaseQuickAdapter<SecureResultVO, BaseViewHolder> implements LoadMoreModule {

    public SecurityOperateAdapter() {
        super(R.layout.item_security_operate);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, SecureResultVO secureResultVO) {
        ImageView ivSecurityHead = viewHolder.getView(R.id.ivSecurityHead);

        //GlideManager.loadImage(getContext(), secureResultVO.getImageUrl(), ivSecurityHead);

        TextView tvSecurityName = viewHolder.getView(R.id.tvSecurityName);
        tvSecurityName.setText(secureResultVO.getPersonName());

        TextView tvSecurityDate = viewHolder.getView(R.id.tvSecurityDate);
        tvSecurityDate.setText(secureResultVO.getTime());

    }
}
