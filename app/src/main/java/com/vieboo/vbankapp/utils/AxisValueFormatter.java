package com.vieboo.vbankapp.utils;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

public class AxisValueFormatter extends ValueFormatter {
    private List<String> labels;

    AxisValueFormatter(List<String> labels) {
        this.labels = labels;
    }

    @Override
    public String getFormattedValue(float value) {
        if (value >= 0) {
            return labels.get((int) value % labels.size());
        } else {
            return "";
        }
    }
}
