package cn.tties.energy.chart;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

;import cn.tties.energy.R;
import cn.tties.energy.application.MyApplication;

/**
 * 登录
 * author chensi
 */
public class LineDataChart extends  LineChart {

    public LineDataChart(Context context) {
        super(context);
        setStyle();
    }

    public LineDataChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        setStyle();
    }

    public LineDataChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setStyle();
    }

    List<LineDataSet> setList = new ArrayList<LineDataSet>();

    public void clear() {
        super.clear();
        setList = new ArrayList<LineDataSet>();
    }

    public void clearData() {
        if (setList.size() > 0) {
            LineData data = getData();
            ILineDataSet set = data.getDataSetByIndex(0);
            set.clear();
        }
        setList = new ArrayList<LineDataSet>();

    }

    public LineDataSet setMuiltDataSet(ArrayList<Entry> values, String label, int color) {
        LineDataSet set = new LineDataSet(values, label);
        set.setColor(color);
        set.setCircleColor(Color.TRANSPARENT);
        set.setLineWidth(1f);//设置线宽
        set.setCircleRadius(3f);//设置焦点圆心的大小
        set.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
        set.setHighlightLineWidth(2f);//设置点击交点后显示高亮线宽
        set.setHighlightEnabled(true);//是否禁用点击高亮线
        set.setHighLightColor(Color.RED);//设置点击交点后显示交高亮线的颜色
        set.setValueTextSize(0f);//设置显示值的文字大小
        set.setDrawFilled(false);//设置禁用范围背景填充
        set.setDrawCircles(false);
        set.setFillColor(ContextCompat.getColor(MyApplication.getInstance(), R.color.chart_line_draw));

        setList.add(set);
        return set;
    }

    public LineDataSet setDataSet(ArrayList<Entry> values, String label) {
        LineDataSet set = new LineDataSet(values, label);
        int color = Color.parseColor("#38A6FE");
        //设置点击交点后显示交高亮线的颜色
        int HighLightcolor = Color.parseColor("#75BFF9");
        int Fillcolor = Color.parseColor("#D2EBFF");
        //设置线条的颜色
        set.setColor(color);
        set.setCircleColor(Color.TRANSPARENT);
        set.setLineWidth(3f);//设置线宽
        set.setCircleRadius(5f);//设置焦点圆心的大小
//        set.setCircleHoleRadius(8f);
        set.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
        set.setHighlightLineWidth(2f);//设置点击交点后显示高亮线宽
        set.setHighlightEnabled(false);//是否禁用点击高亮线
        set.setHighLightColor(HighLightcolor);//设置点击交点后显示交高亮线的颜色
        set.setValueTextSize(0f);//设置显示值的文字大小
        set.setDrawFilled(false);//设置禁用范围背景填充 设置包括的范围区域填充颜色
        set.setCubicIntensity(500);
//        set.setDrawValues(true);
        set.setDrawCircles(false);  //设置有圆点
//        set.setCircleColor(color);
        set.setFillColor(Fillcolor);
        //设置曲线值的圆点是实心还是空心
//        set.setDrawCircleHole(false);
        //线模式为圆滑曲线（默认折线）
        set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
//        set.setMode(LineDataSet.Mode.LINEAR);

        setList.add(set);
        return set;
    }

    public void loadChart() {
        if (setList.size() == 0) {
            clear();
            return;
        }
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (LineDataSet set : setList) {
            dataSets.add(set);
        }
        //创建LineData对象 属于LineChart折线图的数据集合
        LineData data = new LineData(dataSets);
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
        xAxis.setEnabled(true);//设置轴启用或禁用 如果禁用以下的设置全部不生效
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        //xAxis.setTextSize(20f);//设置字体
        xAxis.setTextColor(Color.BLACK);//设置字体颜色
        //设置竖线的显示样式为虚线
        //lineLength控制虚线段的长度
        //spaceLength控制线之间的空间
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setAxisMinimum(0f);//设置x轴的最小值
        xAxis.setAxisMaximum(11f);//设置最大值
        xAxis.setAvoidFirstLastClipping(false);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        //xAxis.setLabelRotationAngle(10f);//设置x轴标签的旋转角度
//        设置x轴显示标签数量  还有一个重载方法第二个参数为布尔值强制设置数量 如果启用会导致绘制点出现偏差
        xAxis.setLabelCount(12,true);
//        xAxis.setTextColor(Color.BLUE);//设置轴标签的颜色
//        xAxis.setTextSize(24f);//设置轴标签的大小
//        xAxis.setGridLineWidth(10f);//设置竖线大小
//        xAxis.setGridColor(Color.RED);//设置竖线颜色
//        xAxis.setAxisLineColor(Color.GREEN);//设置x轴线颜色
//        xAxis.setAxisLineWidth(5f);//设置x轴线宽度
//        xAxis.setValueFormatter();//格式化x轴标签显示字符
//        xAxis.setGranularity(2f);//设置x轴间隔
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
        //最大值，最小值
//        leftAxis.setMaxWidth(144000);
//        leftAxis.setMinWidth(0);
        //leftAxis.setEnabled(false);
        //设置网格线为虚线效果
        //leftAxis.enableGridDashedLine(10f, 10f, 0f);
        //是否绘制0所在的网格线
        leftAxis.setDrawZeroLine(false);
        leftAxis.setAxisLineWidth(0f);
        leftAxis.setDrawAxisLine(false); //无轴线
        // 设置y轴的标签数量
//        leftAxis.setLabelCount(6,true);
//        Y轴总会高出X轴一点，并没有从0点开始，因此需要对Y轴进行设置
//        leftAxis.setAxisMinimum(0f);

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
        Legend l = getLegend();//图例
        l.setEnabled(false);
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);//设置图例的位置
        l.setTextSize(10f);//设置文字大小
        l.setForm(Legend.LegendForm.CIRCLE);//正方形，圆形或线
        l.setFormSize(10f); // 设置Form的大小
        l.setWordWrapEnabled(true);//是否支持自动换行 目前只支持BelowChartLeft, BelowChartRight, BelowChartCenter
        l.setFormLineWidth(10f);//设置Form的宽度
        animateXY(1000, 1000);
    }

    public void reload() {
        for (int i = 0; i < setList.size(); i++) {
            LineDataSet set = (LineDataSet) getData().getDataSetByIndex(i);
            set.setValues(setList.get(i).getValues());
        }
        getData().notifyDataChanged();
        notifyDataSetChanged();
    }

    public void setDayXAxis(List<String> dayList) {
        XAxis xAxis = getXAxis();
        MyXValueFormatter ff = new MyXValueFormatter(dayList);
        xAxis.setValueFormatter(ff);
    }
    public void setDayYAxis(List<String> dayList) {
        YAxis yAxis =getAxisLeft();
        MyYValueFormatter ff = new MyYValueFormatter(dayList);
        yAxis.setValueFormatter(ff);
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
    public class MyYValueFormatter implements IAxisValueFormatter {

        private List<String> labels;

        public MyYValueFormatter(List<String> labels) {
            this.labels = labels;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return labels.get((int) (value%labels.size()));
        }
    }

}
