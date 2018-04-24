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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tties.energy.R;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.chart.LineDataChart;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.MyAllTimeYear;
import cn.tties.energy.common.MyHint;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.Data_Pressbean;
import cn.tties.energy.presenter.Data_PressPresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.utils.ToastUtil;
import cn.tties.energy.view.dialog.BottomStyleDialog;
import cn.tties.energy.view.dialog.BottomStyleDialogTwo;

import cn.tties.energy.view.dialog.MyTimePickerWheelTwoDialog;
import cn.tties.energy.view.iview.IData_PressView;

/**
 * 电压不平衡
 */
public class Data_PressActivity extends BaseActivity<Data_PressPresenter> implements IData_PressView {

    @BindView(R.id.toolbar_left)
    ImageView toolbarLeft;
    @BindView(R.id.toolbar_text)
    TextView toolbarText;
    @BindView(R.id.data_press_chart1)
    LineDataChart dataPressChart1;
    @BindView(R.id.data_press_chart2)
    LineDataChart dataPressChart2;
    @BindView(R.id.data_press_chart3)
    LineDataChart dataPressChart3;
    @BindView(R.id.data_press_time_tv)
    TextView dataTimeTv;
    @BindView(R.id.data_press_time)
    LinearLayout dataPressTime;
    @BindView(R.id.data_press_allelectric)
    LinearLayout dataPressAllelectric;
    @BindView(R.id.data_press_ele_tv)
    TextView dataPressEleTv;
    private BottomStyleDialogTwo dialog;
    MyTimePickerWheelTwoDialog dialogtime;
    MyAllTimeYear timeYear=new MyAllTimeYear();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mPresenter.getAllElectricityData();
        initView();
    }

    private void initView() {
        dataTimeTv.setText(DateUtil.getCurrentYear()+"年"+DateUtil.getCurrentMonth()+"月");
        dialogtime = new MyTimePickerWheelTwoDialog(Data_PressActivity.this);
        toolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbarText.setText("电压不平衡");
        dataPressTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogtime.show();
                dialogtime.setOnCliekTime(new MyTimePickerWheelTwoDialog.OnCliekTime() {
                    @Override
                    public void OnCliekTimeListener(int poaiton) {
                        String tiemMonthBase = timeYear.getTiemMonthBase(poaiton);
                        dataTimeTv.setText(tiemMonthBase);
                        mPresenter.getData_PressData();
                    }
                });
            }
        });
        dataPressAllelectric.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.show();
                }
            }
        });
    }

    @Override
    protected void createPresenter() {
        mPresenter = new Data_PressPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data__press;
    }


    @Override
    public void setData_PressData(Data_Pressbean bean) {
        if (bean.getMaxTimeData().size()>0) {
            dataPressChart2.clearData();
            //不平衡最大值
            ArrayList<Entry> values1 = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
//            List<String> listYDate = new ArrayList<String>();
            for (int i = 0; i < bean.getMaxTimeData().size(); i++) {
                Entry entry = new Entry(i, 0f);
                String[] split1 = StringUtil.split(bean.getMaxTimeData().get(i).getVUMAXTIME(), ":");
                entry.setY((float)Float.parseFloat(split1[0])/24*100);
                values1.add(entry);
                if(bean.getMaxTimeData().get(i).getFREEZETIME()!=null||bean.getMaxTimeData().get(i).getFREEZETIME().equals("")){
                    String split = StringUtil.substring(bean.getMaxTimeData().get(i).getFREEZETIME(),5,10);
                    listDate.add(split);
                }

            }
            XAxis xAxis = dataPressChart2.getXAxis();
            xAxis.setLabelCount(bean.getMaxTimeData().size(),true);
            xAxis.setLabelRotationAngle(-50);
            YAxis axisLeft = dataPressChart2.getAxisLeft();
            axisLeft.setLabelCount(7,true);
            axisLeft.setStartAtZero(true);
            dataPressChart2.setDataSet(values1, "");
            dataPressChart2.setDayXAxis(listDate);
//            dataPressChart2.setDayYAxis(listYDate);
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
            for (int i = 0; i < bean.getMaxData().size(); i++) {
                Entry entry = new Entry(i, 0f);
                entry.setY((float) bean.getMaxData().get(i).getVUMAX());
                values2.add(entry);
                if(bean.getMaxData().get(i).getFREEZETIME()!=null||bean.getMaxData().get(i).getFREEZETIME().equals("")){
                    String split = StringUtil.substring(bean.getMaxData().get(i).getFREEZETIME(),5,10);
                    listDate2.add(split);
                }

            }
            XAxis xAxis = dataPressChart1.getXAxis();
            xAxis.setLabelCount(bean.getMaxData().size(),true);
            xAxis.setLabelRotationAngle(-50);
            dataPressChart1.setDataSet(values2, "");
            dataPressChart1.setDayXAxis(listDate2);
            dataPressChart1.loadChart();
        }else {
            MyHint.myHintDialog(this);
        }
        if(bean.getLimitData().size()>0){
            dataPressChart3.clearData();
            //不平衡度越限日累计时间
            ArrayList<Entry> values3 = new ArrayList<>();
            List<String> listDate3 = new ArrayList<String>();
            for (int i = 0; i < bean.getLimitData().size(); i++) {
                Entry entry = new Entry(i, 0f);
                entry.setY((float) bean.getLimitData().get(i).getVULIMIT());
                values3.add(entry);
                if(bean.getLimitData().get(i).getFREEZETIME()!=null||bean.getLimitData().get(i).getFREEZETIME().equals("")){
                    String split = StringUtil.substring(bean.getLimitData().get(i).getFREEZETIME(),5,10);
                    listDate3.add(split);
                }

            }
            XAxis xAxis = dataPressChart3.getXAxis();
            xAxis.setLabelCount(bean.getLimitData().size(),true);
            xAxis.setLabelRotationAngle(-50);
            dataPressChart3.setDataSet(values3, "");
            dataPressChart3.setDayXAxis(listDate3);
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

    public int getTime(String str) {
//        String str="1640";
        int hour = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("hhmm");
        long time = 0;
        try {
            time = sdf.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date newTime = new Date(time); //就得到普通的时间了
        hour = newTime.getHours();//就得到了小时
        Log.i("-------ss----", "onCreate: " + time);
        Log.i("-------sxxxs----", "onCreate: " + hour);
        return hour;
    }


}
