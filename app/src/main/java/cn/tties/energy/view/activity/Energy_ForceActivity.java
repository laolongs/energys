package cn.tties.energy.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import cn.tties.energy.R;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.chart.BarDataCharttwo;
import cn.tties.energy.chart.LineDataChart;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.MyAllTimeYear;
import cn.tties.energy.common.MyChartXList;
import cn.tties.energy.common.MyHint;
import cn.tties.energy.common.MyNoDoubleClickListener;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Databean;
import cn.tties.energy.presenter.Energy_ForcePresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.DoubleUtils;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.view.dialog.CriProgressDialog;
import cn.tties.energy.view.dialog.MyTimePickerWheelDialog;
import cn.tties.energy.view.iview.IEnergy_ForceView;

/**
 * 力调电量优化
 */
public class Energy_ForceActivity extends BaseActivity<Energy_ForcePresenter> implements IEnergy_ForceView {
    private static final String TAG = "Energy_ForceActivity";
    LinearLayout toolbarLl;
    ImageView toolbarLeft;
    TextView toolbarText;
    TextView energyForceHavekw;
    TextView energyForceNokvar;
    TextView energyForceNum;
    ImageView energyForceSelect;
    LineDataChart energyForceChart1;
    BarDataCharttwo energyForceChart2;
    TextView energyForceYear;
    MyTimePickerWheelDialog dialogtime;
    int year = 0;
    int currentYear;
    int currentMonth;
    DataAllbean dataAllbean = new DataAllbean();
    CriProgressDialog dialogPgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFindView();
        initView();

    }
    private void initFindView() {
        toolbarLl = findViewById(R.id.toolbar_ll);
        toolbarLeft = findViewById(R.id.toolbar_left);
        toolbarText = findViewById(R.id.toolbar_text);
        energyForceHavekw = findViewById(R.id.energy_force_havekw);
        energyForceNokvar = findViewById(R.id.energy_force_nokvar);
        energyForceNum = findViewById(R.id.energy_force_num);
        energyForceSelect = findViewById(R.id.energy_force_select);
        energyForceChart1 = findViewById(R.id.energy_force_chart1);
        energyForceChart2 = findViewById(R.id.energy_force_chart2);
        energyForceYear = findViewById(R.id.energy_force_year);
    }

    private void initView() {
        dialogPgs=new CriProgressDialog(this);
        dialogPgs.loadDialog("加载中...");
        dialogtime = new MyTimePickerWheelDialog(Energy_ForceActivity.this);
        //当月
        mPresenter.getEnergy_Force();
        toolbarText.setText("力调电量优化");
        toolbarLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        energyForceYear.setText(DateUtil.getCurrentYear() + "年");
        //选择年
        energyForceYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyNoDoubleClickListener.isFastClick()) {
                    dialogtime.show();
                    dialogtime.setOnCliekTime(new MyTimePickerWheelDialog.OnCliekTime() {
                        @Override
                        public void OnCliekTimeListener(int poaiton) {
                            int tiemBase = MyAllTimeYear.getTiemBase(poaiton);
                            energyForceYear.setText(tiemBase + "年");
                            mPresenter.getEnergy_ForcechartData();
                            mPresenter.getEnergy_Forcecharge();
                        }
                    });
                }

            }
        });
    }

    @Override
    protected void createPresenter() {
        mPresenter = new Energy_ForcePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_energy__force;
    }


    @Override
    public void setEnergy_ForceData(Databean bean) {
        if (bean.getDataList().size() > 0) {
            dialogPgs.removeDialog();
            ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE, DateUtil.getCurrentYear() + "-" + DateUtil.getCurrentMonth());
            mPresenter.getEnergy_ForcechartData();
            mPresenter.getEnergy_Forcecharge();
            //有功功率
            energyForceHavekw.setText(DoubleUtils.getNum(bean.getDataList().get(0).getAp()) + "kW");
            //无功
            energyForceNokvar.setText(DoubleUtils.getNum(bean.getDataList().get(0).getRp()) + "kVar");
            //因数
            energyForceNum.setText(DoubleUtils.getRate(bean.getDataList().get(0).getRate()) + "");
        }

    }

    @Override
    public void setEnergy_ForceChartData(Databean bean) {
        int allnum = 0;
        if (bean.getDataList().size() > 0) {
            int color = Color.parseColor("#38A6FE");
            int color2 = Color.parseColor("#00000000");
            ArrayList<Integer> listcolor=new ArrayList<>();
            energyForceChart1.clearData();
            ArrayList<Entry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            //判断数据是否全年，否则动态添加数据
//            if (bean.getDataList().size() != 12) {
//                int num = 12 - bean.getDataList().size();
//                allnum = bean.getDataList().size() + num;
//                for (int i = 0; i < allnum; i++) {
//                    Entry entry = new Entry(i, 0f);
//                    if (i >= bean.getDataList().size()) {
//                        int monthNum = i + 1;
//                        int positionNum = DoubleUtils.getPositionNum(monthNum);
//                        if (positionNum == 1) {
//                            listDate.add("0" + monthNum);
//                        } else {
//                            listDate.add(monthNum + "");
//                        }
//                        entry.setY((float) 0);
//                        listcolor.add(color2);
//                    } else {
//                        entry.setY((float) bean.getDataList().get(i).getRate());
//                        Log.i(TAG, "setEnergy_BaseenergyYearData: " + bean.getDataList().get(i).getBaseDate());
//                        String[] split = StringUtil.split(bean.getDataList().get(i).getBaseDate(), "-");
//                        listDate.add(split[1]);
//                        listcolor.add(color);
//                    }
//                    values.add(entry);
//                }
//            } else {
                for (int i = 0; i < bean.getDataList().size(); i++) {
                    Entry entry = new Entry(i, 0f);
                    entry.setY((float) bean.getDataList().get(i).getRate());
                    values.add(entry);
//                    String[] split = StringUtil.split(bean.getDataList().get(i).getBaseDate(), "-");
//                    listDate.add(split[1]);
//                    listcolor.add(color);
                }
//            }

            energyForceChart1.setDataSet(values, "");
            energyForceChart1.getXAxis().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    MyChartXList myChartXList = new MyChartXList();
                    String s = myChartXList.getlist().get((int) value);
                    return s;
                }
            });
//            dataSet.setColors(listcolor);

//            energyForceChart1.setDayXAxis(listDate);
            energyForceChart1.loadChart();
        } else {
            MyHint.myHintDialog(this);
        }

    }

    //每月节约电费
    @Override
    public void setEnergy_ForceCharge(Databean bean) {
        int allnum = 0;
        if (bean.getDataList().size() > 0) {
            energyForceChart2.clearData();
            ArrayList<BarEntry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            //判断数据是否全年，否则动态添加数据
//            if (bean.getDataList().size() != 12) {
//                int num = 12 - bean.getDataList().size();
//                allnum = bean.getDataList().size() + num;
//                for (int i = 0; i < allnum; i++) {
//                    BarEntry entry = new BarEntry(i, 0f);
//                    if (i >= bean.getDataList().size()) {
//                        int monthNum = i + 1;
//                        int positionNum = DoubleUtils.getPositionNum(monthNum);
//                        if (positionNum == 1) {
//                            listDate.add("0" + monthNum);
//                        } else {
//                            listDate.add(monthNum + "");
//                        }
//                        entry.setY((float) 0);
//
//                    } else {
//                        entry.setY((float) bean.getDataList().get(i).getFouceSum());
//                        String month = bean.getDataList().get(i).getBaseDate();
//                        String[] split = StringUtil.split(month, "-");
//                        listDate.add(split[1]);
//                    }
//                    values.add(entry);
//                }
//            } else {
                for (int i = 0; i < bean.getDataList().size(); i++) {
//                    BarEntry entry = new BarEntry(i, 0f);
//                    entry.setY((float) bean.getDataList().get(i).getFouceSum());
//                    values.add(entry);
                    values.add(new BarEntry(i,(float) bean.getDataList().get(i).getFouceSum()));
//                    String month = bean.getDataList().get(i).getBaseDate();
//                    String[] split = StringUtil.split(month, "-");
//                    listDate.add(split[1]);
                }
//            }

            energyForceChart2.setDataSet(values, "");
            energyForceChart2.getXAxis().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    MyChartXList myChartXList = new MyChartXList();
                    String s = myChartXList.getlist().get((int) value);
                    return s;
                }
            });
//            energyForceChart2.setDayXAxis(listDate);
            energyForceChart2.loadChart();
        } else {
            MyHint.myHintDialog(this);
        }
//        if (bean.getDataList().size() > 0) {
//            energyForceChart2.clearData();
//            ArrayList<BarEntry> values = new ArrayList<>();
//            List<String> listDate = new ArrayList<String>();
//            //判断数据是否全年，否则动态添加数据
//            if(bean.getDataList().size()!=12){
//                int num = 12 - bean.getDataList().size();
//                allnum = bean.getDataList().size() + num;
//                for (int i = 1; i <= allnum; i++) {
//                    BarEntry entry = new BarEntry(i, 0f);
//                    if(i>bean.getDataList().size()){
//                        int monthNum=i+1;
//                        int positionNum = DoubleUtils.getPositionNum(monthNum);
//                        if(positionNum==1){
//                            listDate.add("0"+monthNum);
//                        }else{
//                            listDate.add(monthNum+"");
//                        }
//                        entry.setY((float)0);
//
//                    }else{
//                        double j=bean.getDataList().get(bean.getDataList().size()-i).getFouceSum();
//                        entry.setY((float)j);
//                        String month=bean.getDataList().get(bean.getDataList().size()-i).getBaseDate();
//                        String[] split = StringUtil.split(month, "-");
//                        listDate.add(split[1]);
//                    }
//                    values.add(entry);
//                }
//            }else{
//                for (int i = 1; i <=bean.getDataList().size(); i++) {
//                    double j=bean.getDataList().get(bean.getDataList().size()-i).getFouceSum();
//
//                    BarEntry entry = new BarEntry(i, 0f);
////                Log.i(TAG, "setEnergy_ForceCharge: "+num);
//                    entry.setY((float)j);
//                    values.add(entry);
//                    String month=bean.getDataList().get(bean.getDataList().size()-i).getBaseDate();
//                    String[] split = StringUtil.split(month, "-");
//                    listDate.add(split[1]);
//                }
//            }
//
//            energyForceChart2.setDataSet(values, "");
//            energyForceChart2.setDayXAxis(listDate);
//            energyForceChart2.loadChart();
//            }
    }
}
