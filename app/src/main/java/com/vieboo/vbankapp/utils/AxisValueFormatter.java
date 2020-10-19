package com.vieboo.vbankapp.utils;

import com.example.toollib.util.Log;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class AxisValueFormatter extends ValueFormatter {

    private String[] mMonths;

    AxisValueFormatter(String[] mMonths) {
        this.mMonths = mMonths;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        int index = (int) value;
        if (index >= 0 && index < mMonths.length) {
            return mMonths[index];
        }
        return String.valueOf((int) value);
    }
}
