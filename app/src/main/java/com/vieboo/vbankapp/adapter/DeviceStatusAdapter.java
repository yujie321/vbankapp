package com.vieboo.vbankapp.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.VQDResultVO;

import org.jetbrains.annotations.NotNull;

public class DeviceStatusAdapter extends BaseQuickAdapter<VQDResultVO, BaseViewHolder> implements LoadMoreModule {
    public DeviceStatusAdapter() {
        super(R.layout.item_device_status_content);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, VQDResultVO vqdResultVO) {
        //0:未知 1:正常 2:警告 4:异常 8:诊断失败
        Integer status = vqdResultVO.getStatus();
        ImageView ivDiagnosisStatus = viewHolder.getView(R.id.ivDiagnosisStatus);
        switch (status) {
            case 1:
                ivDiagnosisStatus.setImageResource(R.drawable.ic_diagnosis_status_success);
                break;
            case 2:
                ivDiagnosisStatus.setImageResource(R.drawable.ic_diagnosis_status_warning);
                break;
            case 4:
                ivDiagnosisStatus.setImageResource(R.drawable.ic_diagnosis_status_error);
                break;
            case 8:
                ivDiagnosisStatus.setImageResource(R.drawable.ic_diagnosis_status_fail);
                break;
            default: {
                ivDiagnosisStatus.setImageResource(R.drawable.ic_diagnosis_status_fail);
            }
        }

        TextView tvDeviceName = viewHolder.getView(R.id.tvDeviceName);
        tvDeviceName.setText(vqdResultVO.getCameraName());

        TextView tvDiagnosisContent = viewHolder.getView(R.id.tvDiagnosisContent);
        tvDiagnosisContent.setText(vqdResultVO.getErrName());

        ImageView ivNetworkConnectivity = viewHolder.getView(R.id.ivNetworkConnectivity);
        Integer isOnline = vqdResultVO.getIsOnline();
        if (isOnline == 1) {
            ivNetworkConnectivity.setImageResource(R.drawable.ic_network_connectivity);
        } else {
            ivNetworkConnectivity.setImageResource(R.drawable.ic_network_connectivity_error);
        }

        TextView tvDiagnosisDate = viewHolder.getView(R.id.tvDiagnosisDate);
        tvDiagnosisDate.setText(vqdResultVO.getTime());

        ImageView ivVideoStatus = viewHolder.getView(R.id.ivVideoStatus);
        Integer recordStatus = vqdResultVO.getRecordStatus();
        if (recordStatus == 1) {
            ivVideoStatus.setImageResource(R.drawable.ic_video_success);
        } else {
            ivVideoStatus.setImageResource(R.drawable.ic_video_error);
        }
    }
}
