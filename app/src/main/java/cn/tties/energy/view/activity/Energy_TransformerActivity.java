package cn.tties.energy.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import cn.tties.energy.R;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.chart.LineDataChart;
import cn.tties.energy.chart.LineDataTwoChart;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.MyAllTimeYear;
import cn.tties.energy.common.MyChartXList;
import cn.tties.energy.common.MyHint;
import cn.tties.energy.common.MyNoDoubleClickListener;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Energy_TransformerDamgebean;
import cn.tties.energy.model.result.Energy_TransformerListbean;
import cn.tties.energy.model.result.Energy_TransformerTemperaturebean;
import cn.tties.energy.model.result.Energy_TransformerVolumebean;
import cn.tties.energy.presenter.Energy_TransformerPresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.DoubleUtils;
import cn.tties.energy.utils.StringUtil;

import cn.tties.energy.view.dialog.CriProgressDialog;
import cn.tties.energy.view.dialog.MyTimePickerWheelDialog;
import cn.tties.energy.view.iview.IEnergy_TransformerView;

/**
 * 变压器优化
 */
public class Energy_TransformerActivity extends BaseActivity<Energy_TransformerPresenter> implements IEnergy_TransformerView {
    private static final String TAG = "Energy_TransformerActiv";
    LinearLayout toolbarLl;
    ImageView toolbarLeft;
    TextView toolbarText;
    TabLayout energyTransformerTab;
    ImageView energyTransformerSelect1;
    LineDataChart energyTransformerChart1;
    ImageView energyTransformerSelect2;
    LineDataTwoChart energyTransformerChart2;
    TextView energyTransformerDamge;
    TextView energyTransformerKwh;
    TextView energyTransformerYear1;
    TextView energyTransformerYear2;
    int transformerId = 0;
    MyTimePickerWheelDialog dialogtime;
    int year=0;
    DataAllbean dataAllbean=new DataAllbean();
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
        energyTransformerTab = findViewById(R.id.energy_transformer_tab);
        energyTransformerSelect1 = findViewById(R.id.energy_transformer_select1);
        energyTransformerChart1 = findViewById(R.id.energy_transformer_chart1);
        energyTransformerSelect2 = findViewById(R.id.energy_transformer_select2);
        energyTransformerChart2 = findViewById(R.id.energy_transformer_chart2);
        energyTransformerDamge = findViewById(R.id.energy_transformer_damge);
        energyTransformerKwh = findViewById(R.id.energy_transformer_kwh);
        energyTransformerYear1 = findViewById(R.id.energy_transformer_year1);
        energyTransformerYear2 = findViewById(R.id.energy_transformer_year2);
    }

    private void initView() {
        dialogPgs=new CriProgressDialog(this);
        dialogPgs.loadDialog("加载中...");
        mPresenter.getEnergy_TransformerList();
        toolbarText.setText("变压器优化");
        toolbarLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dialogtime=new MyTimePickerWheelDialog(Energy_TransformerActivity.this);
        energyTransformerYear1.setText(DateUtil.getCurrentYear()+"年");
        energyTransformerYear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyNoDoubleClickListener.isFastClick()){
                    dialogtime.show();
                    dialogtime.setOnCliekTime(new MyTimePickerWheelDialog.OnCliekTime() {
                        @Override
                        public void OnCliekTimeListener(int poaiton) {
                            int tiemBase = MyAllTimeYear.getTiemBase(poaiton);
                            energyTransformerYear1.setText(tiemBase + "年");
                            ACache.getInstance().put(Constants.CACHE_OPS_TRANSFORMERTEMPERATUREBASEDATE, tiemBase+"");
                            mPresenter.getEnergy_TransformerTemperature(transformerId);
                        }
                    });
                }

            }
        });

        energyTransformerYear2.setText(DateUtil.getCurrentYear()+"年");
        energyTransformerYear2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(MyNoDoubleClickListener.isFastClick()){
                    dialogtime.show();
                    dialogtime.setOnCliekTime(new MyTimePickerWheelDialog.OnCliekTime() {
                        @Override
                        public void OnCliekTimeListener(int poaiton) {
                            int tiemBase = MyAllTimeYear.getTiemBase(poaiton);
                            energyTransformerYear2.setText(tiemBase + "年");
                            ACache.getInstance().put(Constants.CACHE_OPS_TRANSFORMERVOLUMEBASEDATE, tiemBase+"");
                            mPresenter.getEnergy_TransformerVolume(transformerId);
                        }
                    });
                }

            }
        });
    }
    @Override
    protected void createPresenter() {
        mPresenter = new Energy_TransformerPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_energy__transformer;
    }


    @Override
    public void setEnergy_TransformerListbeanData(final Energy_TransformerListbean bean) {
        //设置可以滑动
        energyTransformerTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        if (bean.getResult().size() > 0) {
            dialogPgs.removeDialog();
            for (int i = 0; i < bean.getResult().size(); i++) {
                energyTransformerTab.addTab(energyTransformerTab.newTab().setText(bean.getResult().get(i).getName()));
            }
            transformerId = bean.getResult().get(0).getCompanyEquipmentId();
            ACache.getInstance().put(Constants.CACHE_OPS_TRANSFORMERTEMPERATUREBASEDATE, DateUtil.getCurrentYear()+"");
            ACache.getInstance().put(Constants.CACHE_OPS_TRANSFORMERVOLUMEBASEDATE, DateUtil.getCurrentYear()+"");
            mPresenter.getEnergy_TransformerDamge(transformerId);
            mPresenter.getEnergy_TransformerTemperature(transformerId);
            mPresenter.getEnergy_TransformerVolume(transformerId);
            energyTransformerTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
//                ToastUtil.showShort(Energy_TransformerActivity.this, tab.getText());
                    int position = tab.getPosition();
                    transformerId = bean.getResult().get(position).getCompanyEquipmentId();
                    mPresenter.getEnergy_TransformerDamge(transformerId);
                    mPresenter.getEnergy_TransformerTemperature(transformerId);
                    mPresenter.getEnergy_TransformerVolume(transformerId);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

    }
    @Override
    public void setEnergy_TransformerDamgebeanData(Energy_TransformerDamgebean bean) {
        if (bean.getResult().getConsume() == 0) {
            energyTransformerKwh.setText("NaN");
        }else{
            energyTransformerKwh.setText(bean.getResult().getConsume() + "");
        }
        if (bean.getResult().getDamge() == 0) {
            energyTransformerDamge.setText("NaN");
        }else{
            energyTransformerDamge.setText(bean.getResult().getDamge() + "%");
        }


    }
    @Override
    public void setEnergy_TransformerTemperaturebeanData(Energy_TransformerTemperaturebean bean) {
        int allnum;
        if(bean.getResult().size()>0){
            int color = Color.parseColor("#38A6FE");
            int color2 = Color.parseColor("#00000000");
            ArrayList<Integer> listcolor=new ArrayList<>();
            energyTransformerChart1.clearData();
            ArrayList<Entry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            //判断数据是否全年，否则动态添加数据
//            if(bean.getResult().size()!=12){
//                int num = 12 - bean.getResult().size();
//                allnum = bean.getResult().size() + num;
//                for (int i = 0; i < allnum; i++) {
//                    Entry entry = new Entry(i, 0f);
//                    if(i>=bean.getResult().size()){
//                        int monthNum=i+1;
//                        int positionNum = DoubleUtils.getPositionNum(monthNum);
//                        if(positionNum==1){
//                            listDate.add("0"+monthNum);
//                        }else{
//                            listDate.add(monthNum+"");
//                        }
//                        entry.setY((float)0);
//                        listcolor.add(color2);
//                    }else{
//                        entry.setY((float) bean.getResult().get(i).getData());
//                        String[] split = StringUtil.split(bean.getResult().get(i).getTime(), "-");
//                        listDate.add(split[1]);
//                        listcolor.add(color);
//                    }
//                    values.add(entry);
//                }
//            }else{
                for (int i = 0; i < bean.getResult().size(); i++) {
//                    Entry entry = new Entry(i, 0f);
//                    entry.setY((float) bean.getResult().get(i).getData());
//                    values.add(entry);
                    values.add(new Entry(i,(float) bean.getResult().get(i).getData()));
//                    if(bean.getResult().get(i).getTime()!=null||!bean.getResult().get(i).getTime().equals("")){
//                        String[] split = StringUtil.split(bean.getResult().get(i).getTime(), "-");
//                        listDate.add(split[1]);
//                        listcolor.add(color);
//                    }
                }
//            }
            energyTransformerChart1.setDataSet(values, "");
//            dataSet.setColors(listcolor);
            energyTransformerChart1.getXAxis().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    MyChartXList myChartXList = new MyChartXList();
                    String s = myChartXList.getlist().get((int) value);
                    return s;
                }
            });
//            energyTransformerChart1.setDayXAxis(listDate);
            energyTransformerChart1.loadChart();
        }else{
            MyHint.myHintDialog(this);
        }

    }

//变压器容量
    @Override
    public void setEnergy_TransformerVolumebeanData(Energy_TransformerVolumebean bean) {
        int allnum;
        //实体bean暂无数据
        if(bean.getResult().size()>0){
            int color = Color.parseColor("#FF7B10");
            int color2 = Color.parseColor("#00000000");
            ArrayList<Integer> listcolor=new ArrayList<>();
            energyTransformerChart2.clearData();
            ArrayList<Entry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            //判断数据是否全年，否则动态添加数据
//            if(bean.getResult().size()!=12){
//                int num = 12 - bean.getResult().size();
//                allnum = bean.getResult().size() + num;
//                for (int i = 0; i < allnum; i++) {
//                    Entry entry = new Entry(i, 0f);
//                    if(i>=bean.getResult().size()){
//                        int monthNum=i+1;
//                        int positionNum = DoubleUtils.getPositionNum(monthNum);
//                        if(positionNum==1){
//                            listDate.add("0"+monthNum);
//                        }else{
//                            listDate.add(monthNum+"");
//                        }
//                        entry.setY((float)0);
//                        listcolor.add(color2);
//                    }else{
//                        entry.setY((float) bean.getResult().get(i).getData());
//                        String[] split = StringUtil.split(bean.getResult().get(i).getBaseDate(), "-");
//                        listDate.add(split[1]);
//                        listcolor.add(color);
//                    }
//                    values.add(entry);
//                }
//            }else{
                for (int i = 0; i < bean.getResult().size(); i++) {
//                    Entry entry = new Entry(i, 0f);
//                    entry.setY((float) bean.getResult().get(i).getData());
//                    values.add(entry);
                    values.add(new Entry(i,(float) bean.getResult().get(i).getData()));
//                    String[] split = StringUtil.split(bean.getResult().get(i).getBaseDate(), "-");
//                    listDate.add(split[1]);
//                    listcolor.add(color);
                }
//            }
            energyTransformerChart2.setDataSet(values, "");
            energyTransformerChart2.getXAxis().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    MyChartXList myChartXList = new MyChartXList();
                    String s = myChartXList.getlist().get((int) value);
                    return s;
                }
            });
//            dataSet.setColors(listcolor);
//            energyTransformerChart2.setDayXAxis(listDate);
            energyTransformerChart2.loadChart();
        }else{
            MyHint.myHintDialog(this);
        }
    }
}
