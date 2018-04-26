package cn.tties.energy.chart;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import cn.tties.energy.R;


/**
 * Created by li on 2018/4/8
 * description：
 * author：guojlli
 */

public class BarDataChart extends BarChart {
    public BarDataChart(Context context) {
        super(context);
        setStyle();
    }



    public BarDataChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        setStyle();
    }

    public BarDataChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setStyle();
    }
    List<BarDataSet> setList = new ArrayList<BarDataSet>();

    public void clear() {
        super.clear();
        setList = new ArrayList<BarDataSet>();
    }
    public void clearData() {
        if (setList.size() > 0) {
            BarData data = getData();
            IBarDataSet dataSetByIndex = data.getDataSetByIndex(0);
            dataSetByIndex.clear();
        }
        setList = new ArrayList<BarDataSet>();

    }
    public  BarDataSet setDataSet(List<BarEntry> list, String title) {
        int color = Color.parseColor("#FF7247");
        //创建BarDateSet对象，其实就是一组柱形数据
         BarDataSet barSet = new BarDataSet(list,title);
//        barSet.setColor(color); //设置是否显示柱子上面的数值
//         barSet.setColor(color); //设置是否显示柱子上面的数值
         barSet.setDrawValues(false); //设置柱子阴影颜色
         barSet.setBarShadowColor(Color.GRAY); //创建集合，存放所有组的柱形数据
         barSet.setBarBorderColor(color);
         barSet.setValueTextSize(9f);
         barSet.setFormLineWidth(1f);
         barSet.setFormSize(15.f);
         barSet.setHighlightEnabled(false);
         setList.add(barSet);
         return barSet;
    }
    public void loadChart() {
        if (setList.size() == 0) {
            clear();
            return;
        }
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        for (BarDataSet set : setList) {
            dataSets.add(set);
    }
        //创建LineData对象 属于LineChart折线图的数据集合
        BarData data = new BarData(dataSets);
        // 添加到图表中
        setData(data);
        //刷新
        invalidate();
    }

    public void setStyle() {
        Description description = new Description();
        description.setEnabled(false);
        setDescription(description);//设置图表描述信息
        setDrawGridBackground(false);//chart 绘图区后面的背景矩形将绘制
        setDrawBorders(false);//禁止绘制图表边框的线
        //setBorderColor(); //设置 chart 边框线的颜色。
        //setBorderWidth(); //设置 chart 边界线的宽度，单位 dp。
        setNoDataText("加载中...");
        setNoDataTextColor(R.color.greenyellow);
        //获取此图表的x轴
        XAxis xAxis = getXAxis();
        xAxis.setEnabled(false);//设置轴启用或禁用 如果禁用以下的设置全部不生效
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);//设置x轴的显示位置
        //xAxis.setTextSize(20f);//设置字体
        //xAxis.setTextColor(Color.BLACK);//设置字体颜色
        //设置竖线的显示样式为虚线
        //lineLength控制虚线段的长度
        //spaceLength控制线之间的空间
        xAxis.enableGridDashedLine(10f, 10f, 0f);
//        xAxis.setAxisMinimum(0f);//设置x轴的最小值
//        xAxis.setAxisMaximum(10f);//设置最大值
        xAxis.setAvoidFirstLastClipping(false);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        //xAxis.setLabelRotationAngle(10f);//设置x轴标签的旋转角度
//        设置x轴显示标签数量  还有一个重载方法第二个参数为布尔值强制设置数量 如果启用会导致绘制点出现偏差
        xAxis.setLabelCount(12);
//        xAxis.setLabelRotationAngle(-50);
        int color = Color.parseColor("#9A9A9A");
        xAxis.setTextColor(color);//设置轴标签的颜色
//        xAxis.setTextSize(24f);//设置轴标签的大小
//        xAxis.setGridLineWidth(10f);//设置竖线大小
//        xAxis.setGridColor(Color.RED);//设置竖线颜色
//        xAxis.setAxisLineColor(Color.GREEN);//设置x轴线颜色
//        xAxis.setAxisLineWidth(5f);//设置x轴线宽度
//        xAxis.setValueFormatter();//格式化x轴标签显示字符
        /**
         * Y轴默认显示左右两个轴线
         */
        //获取右边的轴线
        YAxis rightAxis = getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        //设置网格线为虚线效果
//       rightAxis.enableGridDashedLine(10f, 10f, 0f);
        //是否绘制0所在的网格线
        rightAxis.setDrawZeroLine(false);
        rightAxis.setAxisLineWidth(0f);
        rightAxis.setDrawAxisLine(false); //无轴线

        //获取左边的轴线
        YAxis leftAxis = getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        //最大值，最小值
//        leftAxis.setMaxWidth(0f);
//        leftAxis.setMinWidth(-20000f);
        //设置开始值
//        leftAxis.setStartAtZero(true);
        leftAxis.setEnabled(false);
        //设置网格线为虚线效果
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        //是否绘制0所在的网格线
        leftAxis.setDrawZeroLine(false);
        leftAxis.setAxisLineWidth(0f);
        leftAxis.setDrawAxisLine(false); //无轴线
        //设置据上面距离
        leftAxis.setSpaceTop(0f);
        leftAxis.setSpaceBottom(1f);
//        leftAxis.setDrawGridLines(false);
        // 设置y轴的标签数量
//        leftAxis.setLabelCount(5,true);
//        setEnabled(false);//是否启用轴，如果禁用，关于轴的设置所有属性都将被忽略
        setTouchEnabled(false); // 设置是否可以触摸
        setDragEnabled(false);// 是否可以拖拽
        setScaleEnabled(false);// 是否可以缩放 x和y轴, 默认是true
        setScaleXEnabled(false); //是否可以缩放 仅x轴
        setScaleYEnabled(false); //是否可以缩放 仅y轴
        setPinchZoom(false);  //设置x轴和y轴能否同时缩放。默认是否
        setDoubleTapToZoomEnabled(false);//设置是否可以通过双击屏幕放大图表。默认是true
        setHighlightPerDragEnabled(false);//能否拖拽高亮线(数据点与坐标的提示线)，默认是true
        setDragDecelerationEnabled(false);//拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
        setDragDecelerationFrictionCoef(0.99f);//与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止。
        //设置图列文字颜色

        Legend l = getLegend();//图例
        l.setEnabled(false);
//        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);//设置图例的位置
//        l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);//设置图例的位置
        l.setTextSize(10f);//设置文字大小
        l.setForm(Legend.LegendForm.LINE);//正方形，圆形或线
        l.setFormSize(10f); // 设置Form的大小
        l.setWordWrapEnabled(true);//是否支持自动换行 目前只支持BelowChartLeft, BelowChartRight, BelowChartCenter
        l.setFormLineWidth(3f);//设置Form的宽度
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        animateXY(1000, 1000);
    }
    public void setDayXAxis(List<String> dayList) {
        XAxis xAxis = getXAxis();
        MyXValueFormatter ff = new MyXValueFormatter(dayList);
        xAxis.setValueFormatter(ff);
    }
    public class MyXValueFormatter implements IAxisValueFormatter {

        private List<String> labels;

        public MyXValueFormatter(List<String> labels) {
            this.labels = labels;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return labels.get((int) value % labels.size());
        }
    }
}
