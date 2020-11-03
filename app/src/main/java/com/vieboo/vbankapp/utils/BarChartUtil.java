package com.vieboo.vbankapp.utils;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.application.VBankAppApplication;
import com.vieboo.vbankapp.data.StaticTodaySummeryVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 柱状图设置
 */
public class BarChartUtil {

    public static void setBarChartData(Context mContext, BarChart barChart, StaticTodaySummeryVo staticTodaySummeryVo) {
        float groupSpace = 0.1f;
        float barSpace = 0f;
        float barWidth = 0.45f;
        List<Integer> xAxisValue = getXAxisValue();
        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();
        List<Integer> yAxisValue1 = getYAxisValue1(staticTodaySummeryVo);
        List<Integer> yAxisValue2 = getYAxisValue2(staticTodaySummeryVo);
        for (int i = 0, n = yAxisValue1.size(); i < n; ++i) {
            entries1.add(new BarEntry(xAxisValue.get(i), yAxisValue1.get(i)));
            entries2.add(new BarEntry(xAxisValue.get(i), yAxisValue2.get(i)));
        }
        BarDataSet dataSet1, dataSet2;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            dataSet1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            dataSet2 = (BarDataSet) barChart.getData().getDataSetByIndex(1);
            dataSet1.setValues(entries1);
            dataSet2.setValues(entries2);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            dataSet1 = new BarDataSet(entries1, "");
            //柱子上方值得颜色
            dataSet1.setValueTextColor(mContext.getResources().getColor(R.color.tool_lib_color_FFAE00));
            dataSet2 = new BarDataSet(entries2, "");
            //柱子上方值得颜色
            dataSet2.setValueTextColor(mContext.getResources().getColor(R.color.tool_lib_color_FFAE00));
            dataSet1.setGradientColors(getGradientColor1());
            dataSet2.setColor(Color.rgb(181, 194, 202));
            dataSet2.setGradientColors(getGradientColor2());
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet1);
            dataSets.add(dataSet2);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            data.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return String.valueOf(value);
                }
            });
            barChart.setData(data);
        }
        barChart.getBarData().setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinimum(xAxisValue.get(0));
        barChart.getXAxis().setAxisMaximum(barChart.getBarData().getGroupWidth(groupSpace, barSpace) * xAxisValue.size() + xAxisValue.get(0));
        barChart.groupBars(xAxisValue.get(0), groupSpace, barSpace);
    }


    public static void setBarChartAxis(Context mContext, BarChart barChart, String[] xAxisValue) {
        // 设置x轴
        XAxis xAxis = barChart.getXAxis();
        // 设置x轴显示在下方，默认在上方
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 将此设置为true，绘制该轴的网格线。
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        // 设置x轴上的标签个数
        xAxis.setLabelCount(xAxisValue.length);
        xAxis.setTextColor(mContext.getResources().getColor(R.color.tool_lib_white_FFFFFF));
        // 设置标签对x轴的偏移量，垂直方向
        xAxis.setYOffset(5);
        //设置标签居中
        xAxis.setCenterAxisLabels(true);
        // 设置x轴显示的值的格式
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValue));


        // 设置y轴，y轴有两条，分别为左和右
        YAxis yAxis_right = barChart.getAxisRight();
        // 不显示右边的y轴
        yAxis_right.setEnabled(false);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        // 设置y轴的标签大小
        leftAxis.setTextSize(8f);
        leftAxis.setTextColor(mContext.getResources().getColor(R.color.tool_lib_white_FFFFFF));

        // 设置图例
        Legend legend = barChart.getLegend();
        // 设置图例在图中
        legend.setDrawInside(false);
        legend.setForm(Legend.LegendForm.NONE);
    }

    private static List<Integer> getXAxisValue() {
        List<Integer> xAxisValue = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            xAxisValue.add(i);
        }
        return xAxisValue;
    }

    private static List<Integer> getYAxisValue1(StaticTodaySummeryVo staticTodaySummeryVo) {
        List<Integer> yAxisValue1 = new ArrayList<>();
        //设备在线情况
        yAxisValue1.add( staticTodaySummeryVo.getDeviceStatic().getNormalNum());
        //yAxisValue1.add(20f);
        //人员打卡情况
        yAxisValue1.add( staticTodaySummeryVo.getDutyStatic().getNormalNum());
        //yAxisValue1.add(30f);
        //通知公告阅读情况
        yAxisValue1.add( staticTodaySummeryVo.getNoticeStatic().getNormalNum());
        //yAxisValue1.add(50f);
        //规章制度阅读情况
        yAxisValue1.add( staticTodaySummeryVo.getRuleStatic().getNormalNum());
        //yAxisValue1.add(20f);
        //进出情况
        yAxisValue1.add( staticTodaySummeryVo.getInoutStatic().getNormalNum());
        //yAxisValue1.add(30f);
        //呼叫接听情况
        yAxisValue1.add( staticTodaySummeryVo.getCallStatic().getNormalNum());
        //yAxisValue1.add(70f);
        return yAxisValue1;
    }
    private static List<Integer> getYAxisValue2(StaticTodaySummeryVo staticTodaySummeryVo) {
        List<Integer> yAxisValue2 = new ArrayList<>();
        //设备在线情况
        yAxisValue2.add( staticTodaySummeryVo.getDeviceStatic().getAbnormalNum());
        //yAxisValue2.add(10f);
        //人员打卡情况
        yAxisValue2.add( staticTodaySummeryVo.getDutyStatic().getAbnormalNum());
        //yAxisValue2.add(12f);
        //通知公告阅读情况
        yAxisValue2.add( staticTodaySummeryVo.getNoticeStatic().getAbnormalNum());
        //yAxisValue2.add(13f);
        //规章制度阅读情况
        yAxisValue2.add( staticTodaySummeryVo.getRuleStatic().getAbnormalNum());
        //yAxisValue2.add(18f);
        //进出情况
        yAxisValue2.add( staticTodaySummeryVo.getInoutStatic().getAbnormalNum());
        //yAxisValue2.add(14f);
        //呼叫接听情况
        yAxisValue2.add( staticTodaySummeryVo.getCallStatic().getAbnormalNum());
        //yAxisValue2.add(19f);
        return yAxisValue2;
    }

    private static List<GradientColor> getGradientColor1(){
        List<GradientColor> gradientColors = new ArrayList<>();
        gradientColors.add(new GradientColor(VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_0079E0) ,
                VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_68E0CF)));
        gradientColors.add(new GradientColor(VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_04B2A5) ,
                VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_8F6B82)));
        gradientColors.add(new GradientColor(VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_387002) ,
                VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_4DFC6A)));
        gradientColors.add(new GradientColor(VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_8789E0) ,
                VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_11D314)));
        gradientColors.add(new GradientColor(VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_0D6E40) ,
                VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_C7FC57)));
        gradientColors.add(new GradientColor(VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_6333AB) ,
                VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_BDC53D)));
        return gradientColors;
    }

    private static List<GradientColor> getGradientColor2(){
        List<GradientColor> gradientColors = new ArrayList<>();
        gradientColors.add(new GradientColor(VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_7A1B7D) ,
                VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_FB8254)));
        gradientColors.add(new GradientColor(VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_BCBAF7) ,
                VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_B7436D)));
        gradientColors.add(new GradientColor(VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_928E3A) ,
                VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_10C63B)));
        gradientColors.add(new GradientColor(VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_22F6AA) ,
                VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_521E02)));
        gradientColors.add(new GradientColor(VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_FB499A) ,
                VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_902B83)));
        gradientColors.add(new GradientColor(VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_4D3A10) ,
                VBankAppApplication.getInstance().getResources().getColor(R.color.tool_lib_color_85BFF8)));
        return gradientColors;
    }

}
