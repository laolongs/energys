package cn.tties.energy.chart;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

import cn.tties.energy.R;
import cn.tties.energy.application.MyApplication;


/**
 * 登录
 * author chensi
 */
public class PieDataChart extends PieChart {

    public PieDataChart(Context context) {
        super(context);
        setStyle();
    }

    public PieDataChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        setStyle();
    }

    public PieDataChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setStyle();
    }

    public void loadChart() {
        //刷新
       invalidate();
    }

    public void setDataSet(ArrayList<PieEntry> entries) {
        if (entries.size() > 0) {
            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);

            //数据和颜色
            ArrayList<Integer> colors = new ArrayList<Integer>();
            colors.add(ContextCompat.getColor(MyApplication.getInstance(), R.color.chart_pie_color1));
            colors.add(ContextCompat.getColor(MyApplication.getInstance(), R.color.chart_pie_color2));
            colors.add(ContextCompat.getColor(MyApplication.getInstance(), R.color.chart_pie_color3));
            colors.add(ContextCompat.getColor(MyApplication.getInstance(), R.color.chart_pie_color4));
            dataSet.setColors(colors);
            PieData data = new PieData(dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.WHITE);
            setData(data);
        } else {
            clear();
        }

    }
    public void setStyle() {
        setUsePercentValues(true);
        getDescription().setEnabled(false);
       // setExtraOffsets(5, 10, 5, 5);

        setDragDecelerationFrictionCoef(0.95f);
        //设置中间文件
        setDrawHoleEnabled(true);
        setHoleColor(Color.WHITE);

        setTransparentCircleColor(Color.WHITE);
        setTransparentCircleAlpha(110);

        setHoleRadius(50f);
        setTransparentCircleRadius(51f);
        setDrawCenterText(true);

        setRotationAngle(0);
        // 触摸旋转
        setRotationEnabled(true);
        setHighlightPerTapEnabled(true);

        animateY(1400, Easing.EasingOption.EaseInOutQuad);

        setDrawEntryLabels(false);
        setEntryLabelTypeface(Typeface.SANS_SERIF);


        Legend l = getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTextSize(12);
        setNoDataText("无数据");
        setNoDataTextColor(R.color.greenyellow);
        highlightValues(null);
    }
}
