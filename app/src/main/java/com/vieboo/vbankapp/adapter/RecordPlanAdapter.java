package com.vieboo.vbankapp.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.RecordPlan;

import org.jetbrains.annotations.NotNull;

public class RecordPlanAdapter extends BaseQuickAdapter<RecordPlan, BaseViewHolder> implements LoadMoreModule {
    public RecordPlanAdapter() {
        super(R.layout.item_record_plan_content);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RecordPlan recordPlan) {
        TextView tvTaskName = baseViewHolder.getView(R.id.tvTaskName);
        TextView tvStartTime = baseViewHolder.getView(R.id.tvStartTime);
        TextView tvEndTime = baseViewHolder.getView(R.id.tvEndTime);
        ImageView ivTaskControl = baseViewHolder.getView(R.id.ivTaskControl);

        tvTaskName.setText(recordPlan.getName());
        tvStartTime.setText(recordPlan.getBeginTime());
        tvEndTime.setText(recordPlan.getEndTime());
        ivTaskControl.setImageResource(R.drawable.ic_video_success);
        ivTaskControl.setTag(recordPlan);
    }
}
