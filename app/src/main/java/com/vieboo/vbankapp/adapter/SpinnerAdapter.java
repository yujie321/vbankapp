package com.vieboo.vbankapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.SpinnerVO;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    private List<SpinnerVO> mList;
    private Context mContext;

    public SpinnerAdapter(Context pContext, List<SpinnerVO> pList) {
        this.mContext = pContext;
        this.mList = pList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner_department, null);
        if (convertView != null) {
            SpinnerVO spinnerVO = mList.get(position);
            TextView tvSpinnerName = convertView.findViewById(R.id.tvSpinnerName);
            tvSpinnerName.setText(spinnerVO.getName());
            tvSpinnerName.setTag(spinnerVO.getId());
        }
        return convertView;
    }
}
