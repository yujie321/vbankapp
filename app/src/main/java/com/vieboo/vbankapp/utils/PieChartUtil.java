package com.vieboo.vbankapp.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.vieboo.vbankapp.data.PassengerVO;

import java.util.ArrayList;

public class PieChartUtil {

    public static void setPieChart(PieChart chart){
        chart.setUsePercentValues(true);
        //是否显示  Legend
        chart.getDescription().setEnabled(false);
        chart.setDrawSliceText(false);
//        chart.setExtraOffsets(30.f, 30.f, 30.f, 30.f);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setCenterText(generateCenterSpannableText());
        //饼图中心区域为空
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);
        //没有数据时显示
        chart.setNoDataText("没有数据");
        //饼图区分角度
        chart.setRotationAngle(90);
        // enable rotation of the chart by touch
        //是否可以手势触发旋转
        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(true);
        chart.setEntryLabelTextSize(12f);
        chart.animateY(1400, Easing.EaseInOutQuad);

        Legend mLegend = chart.getLegend();
        mLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        mLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        mLegend.setOrientation(Legend.LegendOrientation.VERTICAL);
        mLegend.setDrawInside(false);
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(0f);
        mLegend.setYOffset(0f);
        mLegend.setTextColor(Color.WHITE);
    }

    private static SpannableString generateCenterSpannableText() {
//        String text = "100\n当日安保\n运营记录";
//        SpannableString s = new SpannableString("100\n当日安保\n运营记录");
//        s.setSpan(new RelativeSizeSpan(2f), 0, 3, 0);
//        s.setSpan(new ForegroundColorSpan(Color.parseColor("#3739BD")),
//                0, 3 , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        s.setSpan(new RelativeSizeSpan(1f), 0, text.length(), 0);
//        s.setSpan(new ForegroundColorSpan(Color.parseColor("#3739BD")),
//                3, text.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        String text = "当日安保\n运营记录";
        SpannableString s = new SpannableString(text);
        s.setSpan(new RelativeSizeSpan(1f), 0, text.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#3739BD")),
                0, text.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    public static PieData setData(Context mContext ,PieChart chart, PassengerVO passengerVO) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        int abnormalGuardNum = passengerVO.getAbnormalGuardNum();
        entries.add(new PieEntry(passengerVO.getAbnormalGuardNum(), "未完成 " + abnormalGuardNum + "次"));
        int normalGuardNum = passengerVO.getNormalGuardNum();
        entries.add(new PieEntry(passengerVO.getNormalGuardNum(), "已完成 " + normalGuardNum + "次"));
        PieDataSet dataSet = new PieDataSet(entries, "");
        //圆上的分割线
        dataSet.setSliceSpace(3f);
        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(mContext,  android.R.color.holo_blue_light));
        colors.add(ContextCompat.getColor(mContext,  android.R.color.holo_red_light));
        dataSet.setColors(colors);
        dataSet.setFormSize(5f);
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.2f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.WHITE);
        return data;
    }

}
