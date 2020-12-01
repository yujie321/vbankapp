package com.vieboo.vbankapp.adapter;

import android.text.SpannableString;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.toollib.manager.GlideManager;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.PunchRecordVO;

import org.jetbrains.annotations.NotNull;

public class StaffStyleAdapter extends BaseQuickAdapter<PunchRecordVO, BaseViewHolder> implements LoadMoreModule {
    public StaffStyleAdapter() {
        super(R.layout.item_staff_style);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PunchRecordVO punchRecordVO) {
        ImageView ivPersonalHead = baseViewHolder.getView(R.id.ivPersonalHead);
        TextView tvPersonalName = baseViewHolder.getView(R.id.tvPersonalName);
        TextView tvPersonalPosition = baseViewHolder.getView(R.id.tvPersonalPosition);

        GlideManager.loadImage(getContext(), punchRecordVO.getImageUrl(), ivPersonalHead, R.drawable.bg_test);
        tvPersonalName.setText(punchRecordVO.getName());

        String text = punchRecordVO.getPositionName() + "\n" + punchRecordVO.getNumber();
        SpannableString s = new SpannableString(text);
        tvPersonalPosition.setText(s);
    }
}
