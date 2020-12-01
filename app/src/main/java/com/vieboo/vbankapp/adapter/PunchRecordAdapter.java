package com.vieboo.vbankapp.adapter;

import android.text.SpannableString;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.toollib.manager.GlideManager;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.RecordVO;

import org.jetbrains.annotations.NotNull;

public class PunchRecordAdapter extends BaseQuickAdapter<RecordVO, BaseViewHolder> implements LoadMoreModule {
    public PunchRecordAdapter() {
        super(R.layout.item_puch_record);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RecordVO recordVO) {
        ImageView ivRecordPersonalHead = baseViewHolder.getView(R.id.ivRecordPersonalHead);
        TextView tvRecordPersonalName = baseViewHolder.getView(R.id.tvRecordPersonalName);
        TextView tvRecordPersonalPosition = baseViewHolder.getView(R.id.tvRecordPersonalPosition);
        TextView tvRecordDate = baseViewHolder.getView(R.id.tvRecordDate);

        GlideManager.loadImage(getContext(), recordVO.getImageUrl(), ivRecordPersonalHead);
        tvRecordPersonalName.setText(recordVO.getName());

        String text = recordVO.getPositionName() + "  "+ recordVO.getNumber();
        SpannableString s = new SpannableString(text);
        tvRecordPersonalPosition.setText(s);

        tvRecordDate.setText(recordVO.getClockTime());
    }
}
