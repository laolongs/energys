package cn.tties.energy.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import cn.tties.energy.model.result.Data_Pressbean;
import cn.tties.energy.presenter.Data_PressPresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.utils.ToastUtil;
import cn.tties.energy.view.dialog.BottomStyleDialog;
import cn.tties.energy.view.dialog.BottomStyleDialogTwo;

import cn.tties.energy.view.dialog.CriProgressDialog;
import cn.tties.energy.view.dialog.MyTimePickerWheelTwoDialog;
import cn.tties.energy.view.iview.IData_PressView;

/**
 * 电压不平衡
 */
public class Data_PressActivity extends BaseActivity<Data_PressPresenter> implements IData_PressView {
    private static final String TAG = "Data_PressActivity";
    LinearLayout toolbarLl;
    ImageView toolbarLeft;
    TextView toolbarText;
    LineDataChart dataPressChart1;
    LineDataChart dataPressChart2;
    BarDataThreeChart dataPressChart3;
    TextView dataTimeTv;
    LinearLayout dataPressTime;
    LinearLayout dataPressAllelectric;
    TextView dataPressEleTv;
    LinearLayout dataPressLL;
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
        dataPressChart1= findViewById(R.id.data_press_chart1);
        dataPressChart2= findViewById(R.id.data_press_chart2);
        dataPressChart3= findViewById(R.id.data_press_chart3);
        dataTimeTv= findViewById(R.id.data_press_time_tv);
        dataPressTime= findViewById(R.id.data_press_time);
        dataPressAllelectric= findViewById(R.id.data_press_allelectric);
        dataPressEleTv= findViewById(R.id.data_press_ele_tv);
        dataPressLL= findViewById(R.id.data_press_LL);
    }

    private void initView() {
        mPresenter.getAllElectricityData();
        dataTimeTv.setText(DateUtil.getCurrentYear()+"年"+DateUtil.getCurrentMonth()+"月");
        months=DateUtil.getCurrentMonth();
        years=DateUtil.getCurrentYear();
        days = DateUtil.getDays(DateUtil.getCurrentYear(), DateUtil.getCurrentMonth());
        dialogtime = new MyTimePickerWheelTwoDialog(Data_PressActivity.this);
        toolbarLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbarText.setText("电压不平衡");
        dataPressTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyNoDoubleClickListener.isFastClick()){
                    dialogtime.show();
                    dialogtime.setOnCliekTime(new MyTimePickerWheelTwoDialog.OnCliekTime() {
                        @Override
                        public void OnCliekTimeListener(int poaiton) {
                            String tiemMonthBase = timeYear.getTiemMonthBase(poaiton);
                            dataTimeTv.setText(tiemMonthBase);
                            String month = StringUtil.getMonth(tiemMonthBase);
                            String year = StringUtil.getYear(tiemMonthBase);
                            months=(Integer.parseInt(month));
                            years=(Integer.parseInt(year));
                            days = DateUtil.getDays(Integer.parseInt(year), (Integer.parseInt(month)-1));
                            mPresenter.getData_PressData();
                        }
                    });
                }

            }
        });
        dataPressAllelectric.setOnClickListener(new View.OnClickListener() {

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
        mPresenter = new Data_PressPresenter(this,this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data__press;
    }


    @Override
    public void setData_PressData(Data_Pressbean bean) {
        dataPressLL.setVisibility(View.VISIBLE);
        if (bean.getMaxTimeData().size()>0) {
            dataPressChart2.clearData();
            //不平衡最大值
            ArrayList<Entry> values1 = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            MyChartXList myChartXList = new MyChartXList();
            Map map = myChartXList.get(years, months);
            Map XMap = (Map) map.get("XMap");
            for (int i = 0; i < bean.getMaxTimeData().size(); i++) {
                String split = StringUtil.substring(bean.getMaxTimeData().get(i).getFREEZETIME(),5,10);
                String[] split1 = StringUtil.split(bean.getMaxTimeData().get(i).getVUMAXTIME(), ":");
                Integer index = (int)XMap.get(split);
                values1.add(new Entry(index, (float)Float.parseFloat(split1[0])/24*100));

            }
            XAxis xAxis = dataPressChart2.getXAxis();
            xAxis.setLabelCount(15,true);
            xAxis.setLabelRotationAngle(-50);
            xAxis.setAxisMinimum(0f);
            xAxis.setAxisMaximum(days-1);
            YAxis axisLeft = dataPressChart2.getAxisLeft();
            axisLeft.setLabelCount(7,true);
            axisLeft.setStartAtZero(true);
            dataPressChart2.setDataSet(values1, "");
            dataPressChart2.setDayXAxis((List)map.get("X"));
            dataPressChart2.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    int i = (int) value;
                    String str = i*24/100 + ":00";
                    return str;
                }
            });
            dataPressChart2.loadChart();
        }else {
            MyHint.myHintDialog(this);
        }
        if(bean.getMaxData().size()>0) {
            dataPressChart1.clearData();
            //不平衡最大值发生时间
            ArrayList<Entry> values2 = new ArrayList<>();
            List<String> listDate2 = new ArrayList<String>();
            MyChartXList myChartXList = new MyChartXList();
            Map map = myChartXList.get(years, months);
            Map XMap = (Map) map.get("XMap");
            for (int i = 0; i < bean.getMaxData().size(); i++) {
                String split = StringUtil.substring(bean.getMaxData().get(i).getFREEZETIME(),5,10);
                listDate2.add(split);
                int index = (int) XMap.get(split);
                values2.add(new Entry(index, (float) bean.getMaxData().get(i).getVUMAX()));
            }
            XAxis xAxis = dataPressChart1.getXAxis();
            xAxis.setLabelCount(15,true);
            xAxis.setLabelRotationAngle(-50);
            xAxis.setAxisMinimum(0f);
            xAxis.setAxisMaximum(days-1);
            dataPressChart1.setDataSet(values2, "");
            dataPressChart1.setDayXAxis((List)map.get("X"));
            dataPressChart1.loadChart();
        }else {
            MyHint.myHintDialog(this);
        }
        if(bean.getLimitData().size()>0){
            dataPressChart3.clearData();
            //不平衡度越限日累计时间
            ArrayList<BarEntry> values3 = new ArrayList<>();
            List<String> listDate3 = new ArrayList<String>();
            MyChartXList myChartXList = new MyChartXList();
            Map map = myChartXList.get(years, months);
            Map XMap = (Map) map.get("XMap");
            for (int i = 0; i < bean.getLimitData().size(); i++) {
                String split = StringUtil.substring(bean.getLimitData().get(i).getFREEZETIME(),5,10);
                int index = (int) XMap.get(split);
                values3.add(new BarEntry(index, (float) bean.getLimitData().get(i).getVULIMIT()));
            }
            XAxis xAxis = dataPressChart3.getXAxis();
            xAxis.setLabelCount(15,true);
            xAxis.setLabelRotationAngle(-50);
            xAxis.setAxisMinimum(0f);
            xAxis.setAxisMaximum(days-1);
            dataPressChart3.setDataSet(values3, "");
            dataPressChart3.setDayXAxis((List)map.get("X"));
            dataPressChart3.loadChart();
        }else {
            MyHint.myHintDialog(this);
        }

    }

    @Override
    public void setAllElectricity(final AllElectricitybean allElectricitybean) {
        if(allElectricitybean.getMeterList().size()>0){
            ACache.getInstance().put(Constants.CACHE_OPS_OBJID, allElectricitybean.getMeterList().get(0).getMeterId());
            ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE, DateUtil.getCurrentYear()+"-"+DateUtil.getCurrentMonth());
            mPresenter.getData_PressData();
            dataPressEleTv.setText(allElectricitybean.getMeterList().get(0).getMeterName());
            dialog = new BottomStyleDialogTwo(Data_PressActivity.this, allElectricitybean);
            dialog.setCliekAllElectricity(new BottomStyleDialogTwo.OnCliekAllElectricitytwo() {
                @Override
                public void OnCliekAllElectricityListener(int poaiton) {
                    long meterId = allElectricitybean.getMeterList().get(poaiton).getMeterId();
                    dataPressEleTv.setText(allElectricitybean.getMeterList().get(poaiton).getMeterName());
                    ACache.getInstance().put(Constants.CACHE_OPS_OBJID, meterId);
                    mPresenter.getData_PressData();
                }
            });
        }

    }


    public static float minuteParse(String duration) {
        float allnumber = Float.parseFloat(duration);//1640
        float minute = allnumber * 60;
        return minute;
    }
}
