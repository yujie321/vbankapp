package com.vieboo.vbankapp.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.Record;

import org.jetbrains.annotations.NotNull;

public class RecordAdapter extends BaseQuickAdapter<Record, BaseViewHolder> implements LoadMoreModule {
    public RecordAdapter() {
        super(R.layout.item_record_content);
    }
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Record record) {
        TextView tvRecordName = baseViewHolder.getView(R.id.tvRecordName);
        TextView tvStartTime = baseViewHolder.getView(R.id.tvStartTime);
        TextView tvEndTime = baseViewHolder.getView(R.id.tvEndTime);
        ImageView ivRecordControl = baseViewHolder.getView(R.id.ivRecordControl);

        tvRecordName.setText(record.getPlanName());
        tvStartTime.setText(record.getBeginTime());
        tvEndTime.setText(record.getEndTime());
        ivRecordControl.setImageResource(R.drawable.ic_video_success);
        ivRecordControl.setTag(record);
    }
}
