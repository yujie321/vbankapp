package com.vieboo.vbankapp.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.RouteNodeInfo;

import org.jetbrains.annotations.NotNull;

public class AmEscortAdapter extends BaseQuickAdapter<RouteNodeInfo, BaseViewHolder> {


    public AmEscortAdapter() {
        super(R.layout.item_am_escort);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RouteNodeInfo routeNodeInfo) {
        RelativeLayout relEscort = baseViewHolder.getView(R.id.relEscort);
        //预计到达时间
        TextView tvEstimatedTime = baseViewHolder.getView(R.id.tvEstimatedTime);
        //到达状态
        ImageView ivEscortStatus = baseViewHolder.getView(R.id.ivEscortStatus);
        ImageView ivEscortSpot = baseViewHolder.getView(R.id.ivEscortSpot);
        //名称
        TextView tvEscort = baseViewHolder.getView(R.id.tvEscort);

        //是否是当前位置
        boolean current = routeNodeInfo.isCurrent();
        if (current) {
            tvEstimatedTime.setTextColor(getContext().getResources().getColor(R.color.tool_lib_white_FFFFFF));
            tvEstimatedTime.setText(getContext().getText(R.string.current_location));
        } else {
            //实际到达时间为null 则使用预计到达时间
            String arriveTime = routeNodeInfo.getArriveTime();
            String estimateArriveTime = routeNodeInfo.getEstimateArriveTime();
            if (TextUtils.isEmpty(arriveTime)) {
                arriveTime = estimateArriveTime;
            }
            tvEstimatedTime.setText(getContext().getText(R.string.estimated_time_of_arrival_) + "\n" + arriveTime);
            tvEstimatedTime.setTextColor(getContext().getResources().getColor(R.color.tool_lib_gray_888888));
        }

        Integer status = routeNodeInfo.getStatus();
        if (status == 0){
            //已经经过
            ivEscortStatus.setBackgroundResource(R.drawable.ic_arrive);
            ivEscortSpot.setBackgroundResource(R.drawable.ic_gray_spot);
            tvEscort.setTextColor(getContext().getResources().getColor(R.color.tool_lib_gray_888888));
        }else if (status == 1){
            //正在交接
            ivEscortStatus.setBackgroundResource(R.drawable.ic_current);
            ivEscortSpot.setBackgroundResource(R.drawable.ic_yellow_spot);
            tvEscort.setTextColor(getContext().getResources().getColor(R.color.tool_lib_color_FFB500));
        }else {
            //未经过
            ivEscortStatus.setBackgroundResource(R.drawable.ic_not_arrive);
            ivEscortSpot.setBackgroundResource(R.drawable.ic_gray_spot);
            tvEscort.setTextColor(getContext().getResources().getColor(R.color.tool_lib_white_FFFFFF));
        }
        tvEscort.setText(routeNodeInfo.getName());
    }

}
