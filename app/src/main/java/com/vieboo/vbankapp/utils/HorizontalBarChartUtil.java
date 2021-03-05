package com.vieboo.vbankapp.utils;

import android.graphics.Color;

import androidx.fragment.app.FragmentActivity;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.DeviceStatusVo;

import java.util.ArrayList;

public class HorizontalBarChartUtil {
    protected static String[] mLabels = new String[]{
            "报警主机", "摄像机", "门禁","存储设备","其他设备"
    };

    public static void initHorizontalBarChart(HorizontalBarChart horizontalBarChart) {
        horizontalBarChart.setDrawBarShadow(false);

        horizontalBarChart.setDrawValueAboveBar(true);

        horizontalBarChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        horizontalBarChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        horizontalBarChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // chart.setDrawBarShadow(true);

        horizontalBarChart.setDrawGridBackground(false);

        XAxis xl = horizontalBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(1f);
        xl.setCenterAxisLabels(true);

        YAxis yl = horizontalBarChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);
        yl.setTextColor(Color.WHITE);
        yl.setEnabled(false);

        YAxis yr = horizontalBarChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(true);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);
        yr.setTextColor(Color.WHITE);

        horizontalBarChart.setFitBars(true);
        horizontalBarChart.animateY(1500);

        Legend l = horizontalBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
        l.setTextColor(Color.WHITE);
    }

    public static void setHorizontalBarChartData(FragmentActivity activity, HorizontalBarChart horizontalBarChart, DeviceStatusVo deviceStatusVo) {
        float groupSpace = 0.18f;
        float barSpace = 0.00f; // x4 DataSet
        float barWidth = 0.10f; // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"
        float randomMultiplier = 50;

        horizontalBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(mLabels));
        //horizontalBarChart.getXAxis().setLabelCount(5);
        horizontalBarChart.getXAxis().setTextColor(Color.WHITE);

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals4 = new ArrayList<BarEntry>();

        BarDataSet set1, set2, set3, set4;

        for (int i = 0; i < 5; i++) { //每个公司每年的数据
            switch (i) {
                case 0: {
                    yVals1.add(new BarEntry(i, 10));
                    yVals2.add(new BarEntry(i, 20));
                    yVals3.add(new BarEntry(i, 30));
                    yVals4.add(new BarEntry(i, 40));
                }
                break;
                case 1: {
                    yVals1.add(new BarEntry(i, deviceStatusVo.getOnlinenum()));
                    yVals2.add(new BarEntry(i, deviceStatusVo.getOfflinenum()));
                    yVals3.add(new BarEntry(i, deviceStatusVo.getNormalnum()));
                    yVals4.add(new BarEntry(i, deviceStatusVo.getAbnormalnum()));
                }
                break;
                case 2: {
                    yVals1.add(new BarEntry(i, 10));
                    yVals2.add(new BarEntry(i, 20));
                    yVals3.add(new BarEntry(i, 30));
                    yVals4.add(new BarEntry(i, 40));
                }
                break;
                case 3: {
                    yVals1.add(new BarEntry(i, 10));
                    yVals2.add(new BarEntry(i, 20));
                    yVals3.add(new BarEntry(i, 30));
                    yVals4.add(new BarEntry(i, 40));
                }
                break;
                case 4: {
                    yVals1.add(new BarEntry(i, 10));
                    yVals2.add(new BarEntry(i, 20));
                    yVals3.add(new BarEntry(i, 30));
                    yVals4.add(new BarEntry(i, 40));
                }
                break;
                default:
                    break;
            }
        }


        if (horizontalBarChart.getData() != null && horizontalBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) horizontalBarChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) horizontalBarChart.getData().getDataSetByIndex(1);
            set3 = (BarDataSet) horizontalBarChart.getData().getDataSetByIndex(2);
            set4 = (BarDataSet) horizontalBarChart.getData().getDataSetByIndex(3);
            set1.setValues(yVals1); //A公司的所有年份数据
            set2.setValues(yVals2);
            set3.setValues(yVals3); //A公司的所有年份数据
            set4.setValues(yVals4);
            horizontalBarChart.getData().notifyDataChanged();
            horizontalBarChart.notifyDataSetChanged();
        } else {
            // create 4 DataSets
            set1 = new BarDataSet(yVals1, "在线");
            set1.setColor(activity.getResources().getColor(R.color.tool_lib_color_03ABD6));
            set1.setValueTextColor(activity.getResources().getColor(R.color.tool_lib_color_00C6FF));
            set2 = new BarDataSet(yVals2, "离线");
            set2.setColor(activity.getResources().getColor(R.color.tool_lib_color_BCBCBC));
            set2.setValueTextColor(activity.getResources().getColor(R.color.tool_lib_color_00C6FF));
            set3 = new BarDataSet(yVals3, "正常");
            set3.setColor(activity.getResources().getColor(R.color.tool_lib_color_0EC274));
            set3.setValueTextColor(activity.getResources().getColor(R.color.tool_lib_color_00C6FF));
            set4 = new BarDataSet(yVals4, "故障");
            set4.setColor(activity.getResources().getColor(R.color.tool_lib_color_E09C08));
            set4.setValueTextColor(activity.getResources().getColor(R.color.tool_lib_color_00C6FF));

            BarData data = new BarData(set1, set2, set3, set4); //,set4
            horizontalBarChart.setData(data);
        }


        //设置柱状图的宽度及字体大小等等
        horizontalBarChart.getBarData().setBarWidth(barWidth);
        horizontalBarChart.getBarData().setValueTextSize(7f);
        // restrict the x-axis range
        horizontalBarChart.getXAxis().setAxisMinimum(0);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        horizontalBarChart.getXAxis().setAxisMaximum(0 + horizontalBarChart.getBarData().getGroupWidth(groupSpace, barSpace) * 5);
        horizontalBarChart.groupBars(0, groupSpace, barSpace);
        horizontalBarChart.invalidate(); //刷新图表
    }
}
