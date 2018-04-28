package cn.tties.energy.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.tties.energy.R;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.chart.BarDataThreeChart;
import cn.tties.energy.chart.LineDataChart;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.MyAllTimeYear;
import cn.tties.energy.common.MyChartXList;
import cn.tties.energy.common.MyHint;
import cn.tties.energy.common.MyNoDoubleClickListener;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.Data_Nobean;
import cn.tties.energy.presenter.Data_NoPresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.utils.ToastUtil;
import cn.tties.energy.view.dialog.BottomStyleDialog;
import cn.tties.energy.view.dialog.BottomStyleDialogTwo;

import cn.tties.energy.view.dialog.MyTimePickerWheelTwoDialog;
import cn.tties.energy.view.iview.IData_NoView;

/**
 * 电流不平衡
 */
public class Data_NoActivity extends BaseActivity<Data_NoPresenter> implements IData_NoView {
    private static final String TAG = "Data_NoActivity";
    LinearLayout toolbarLl;
    ImageView toolbarLeft;
    TextView toolbarText;
    Toolbar dataToolbar;
    LineDataChart dataNoChart1;
    LineDataChart dataNoChart2;
    BarDataThreeChart dataNoChart3;
    TextView dataNoTimeTv;
    LinearLayout dataNoTime;
    LinearLayout dataNoAllelectric;
    TextView dataNoEleTv;
    private BottomStyleDialogTwo dialog;
    MyTimePickerWheelTwoDialog dialogtime;
    MyAllTimeYear timeYear=new MyAllTimeYear();
    private int days;
    int  months;
    int years;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFindView();
        initView();
    }

    private void initFindView() {
        toolbarLl= findViewById(R.id.toolbar_ll);
        toolbarLeft= findViewById(R.id.toolbar_left);
        toolbarText= findViewById(R.id.toolbar_text);
        dataToolbar= findViewById(R.id.data_toolbar);
        dataNoChart1= findViewById(R.id.data_no_chart1);
        dataNoChart2= findViewById(R.id.data_no_chart2);
        dataNoChart3= findViewById(R.id.data_no_chart3);
        dataNoTimeTv= findViewById(R.id.data_no_time_tv);
        dataNoTime= findViewById(R.id.data_no_time);
        dataNoAllelectric= findViewById(R.id.data_no_allelectric);
        dataNoEleTv= findViewById(R.id.data_no_ele_tv);
    }


    private void initView() {
        mPresenter.getAllElectricityData();
        dataNoTimeTv.setText(DateUtil.getCurrentYear()+"年"+DateUtil.getCurrentMonth()+"月");
        months=DateUtil.getCurrentMonth();
        years=DateUtil.getCurrentYear();
        days = DateUtil.getDays(DateUtil.getCurrentYear(), DateUtil.getCurrentMonth());
        dialogtime = new MyTimePickerWheelTwoDialog(Data_NoActivity.this);
        toolbarText.setText("电流不平衡");
        toolbarLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dataNoTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyNoDoubleClickListener.isFastClick()){

                }dialogtime.show();
                dialogtime.setOnCliekTime(new MyTimePickerWheelTwoDialog.OnCliekTime() {
                    @Override
                    public void OnCliekTimeListener(int poaiton) {
                        String tiemMonthBase = timeYear.getTiemMonthBase(poaiton);
                        dataNoTimeTv.setText(tiemMonthBase);
                        String month = StringUtil.getMonth(tiemMonthBase);
                        String year = StringUtil.getYear(tiemMonthBase);
                        months=(Integer.parseInt(month));
                        years=(Integer.parseInt(year));
                        days = DateUtil.getDays(Integer.parseInt(year), (Integer.parseInt(month)-1));
                        mPresenter.getData_NoData();
                    }
                });

            }
        });
        dataNoAllelectric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyNoDoubleClickListener.isFastClick()){
                    if (dialog != null) {
                        dialog.show();
                    }
                }
            }
        });


    }

    @Override
    protected void createPresenter() {
        mPresenter = new Data_NoPresenter(Data_NoActivity.this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data__no;
    }

    @Override
    public void setData_NoData(Data_Nobean bean) {
        if (bean.getMaxTimeData().size()>0) {
            dataNoChart2.clearData();
            //不平衡最大值
            ArrayList<Entry> values1 = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            MyChartXList myChartXList = new MyChartXList();
            Map map = myChartXList.get(years, months);
            Map XMap = (Map) map.get("XMap");
            for (int i = 0; i < bean.getMaxTimeData().size()-1; i++) {
                    String split = StringUtil.substring(bean.getMaxTimeData().get(i).getFREEZETIME(),5,10);
                    String[] split1 = StringUtil.split(bean.getMaxTimeData().get(i).getIUMAXTIME(), ":");
                    Integer index = (int)XMap.get(split);
                    values1.add(new Entry(index, (float)Float.parseFloat(split1[0])/24*100));


//                values1.add(new Entry(index, 0));
            }

            XAxis xAxis = dataNoChart2.getXAxis();
            xAxis.setLabelCount(bean.getMaxTimeData().size(),true);
            xAxis.setLabelRotationAngle(-50);
            xAxis.setAxisMinimum(0f);
            xAxis.setAxisMaximum(days - 1);
            YAxis axisLeft = dataNoChart2.getAxisLeft();
            axisLeft.setLabelCount(7,true);
            axisLeft.setStartAtZero(true);
            dataNoChart2.setDataSet(values1, "");
            dataNoChart2.setDayXAxis((List)map.get("X"));
            dataNoChart2.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    int i = (int) value;
                    String str = i*24/100 + ":00";
                    return str;
                }
            });
            dataNoChart2.loadChart();
        }else {
            MyHint.myHintDialog(this);
        }
        if(bean.getMaxData().size()>0){
            dataNoChart1.clearData();
            //不平衡最大值发生时间
            ArrayList<Entry> values2 = new ArrayList<>();
            List<String> listDate2 = new ArrayList<String>();
            MyChartXList myChartXList = new MyChartXList();
            Map map = myChartXList.get(years, months);
            Map XMap = (Map) map.get("XMap");
            for (int i = 0; i < bean.getMaxData().size()-1; i++) {
                String split = StringUtil.substring(bean.getMaxData().get(i).getFREEZETIME(),5,10);
                listDate2.add(split);
                int index = (int) XMap.get(split);
                values2.add(new Entry(index, (float) bean.getMaxData().get(i).getIUMAX()));


            }
            XAxis xAxis = dataNoChart1.getXAxis();
            xAxis.setLabelCount(bean.getMaxData().size(),true);
            xAxis.setLabelRotationAngle(-50);
            xAxis.setAxisMinimum(0f);
            xAxis.setAxisMaximum(days-1);
            dataNoChart1.setDataSet(values2, "");
            dataNoChart1.setDayXAxis((List)map.get("X"));
            dataNoChart1.loadChart();
        }else {
            MyHint.myHintDialog(this);
        }
        if(bean.getLimitData().size()>0){
            dataNoChart3.clearData();
            //不平衡度越限日累计时间
            ArrayList<BarEntry> values3 = new ArrayList<>();
            MyChartXList myChartXList = new MyChartXList();
            Map map = myChartXList.get(years, months);
            Map XMap = (Map) map.get("XMap");
            for (int i = 0; i < bean.getLimitData().size()-1; i++) {
                String split = StringUtil.substring(bean.getLimitData().get(i).getFREEZETIME(),5,10);
                int index = (int) XMap.get(split);
                values3.add(new BarEntry(index, (float) bean.getLimitData().get(i).getIULIMIT()));
            }
            XAxis xAxis = dataNoChart3.getXAxis();
            xAxis.setLabelCount(bean.getLimitData().size(),true);
            xAxis.setLabelRotationAngle(-50);
            xAxis.setAxisMinimum(1f);
            xAxis.setAxisMaximum(days-1);
            dataNoChart3.setDataSet(values3, "");
            dataNoChart3.setDayXAxis((List)map.get("X"));
            dataNoChart3.loadChart();
        }else {
            MyHint.myHintDialog(this);
        }
    }

    @Override
    public void setAllElectricity(final AllElectricitybean allElectricitybean) {
        if(allElectricitybean.getMeterList().size()>0){
            ACache.getInstance().put(Constants.CACHE_OPS_OBJID, allElectricitybean.getMeterList().get(0).getMeterId());
            ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE, DateUtil.getCurrentYear()+"-"+DateUtil.getCurrentMonth());
            mPresenter.getData_NoData();
            dataNoEleTv.setText(allElectricitybean.getMeterList().get(0).getMeterName());
            dialog = new BottomStyleDialogTwo(Data_NoActivity.this, allElectricitybean);
            dialog.setCliekAllElectricity(new BottomStyleDialogTwo.OnCliekAllElectricitytwo() {
                @Override
                public void OnCliekAllElectricityListener(int poaiton) {
                    long meterId = allElectricitybean.getMeterList().get(poaiton).getMeterId();
                    dataNoEleTv.setText(allElectricitybean.getMeterList().get(poaiton).getMeterName());
                    ACache.getInstance().put(Constants.CACHE_OPS_OBJID, meterId);
                    mPresenter.getData_NoData();
                }
            });
        }

    }
}
