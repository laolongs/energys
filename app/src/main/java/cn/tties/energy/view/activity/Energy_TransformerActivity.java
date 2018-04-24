package cn.tties.energy.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tties.energy.R;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.chart.LineDataChart;
import cn.tties.energy.chart.LineDataTwoChart;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.MyAllTimeYear;
import cn.tties.energy.common.MyHint;
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

import cn.tties.energy.view.dialog.MyTimePickerWheelDialog;
import cn.tties.energy.view.iview.IEnergy_TransformerView;

/**
 * 变压器优化
 */
public class Energy_TransformerActivity extends BaseActivity<Energy_TransformerPresenter> implements IEnergy_TransformerView {
    private static final String TAG = "Energy_TransformerActiv";
    @BindView(R.id.toolbar_left)
    ImageView toolbarLeft;
    @BindView(R.id.toolbar_text)
    TextView toolbarText;
    @BindView(R.id.energy_transformer_tab)
    TabLayout energyTransformerTab;
    @BindView(R.id.energy_transformer_select1)
    ImageView energyTransformerSelect1;
    @BindView(R.id.energy_transformer_chart1)
    LineDataChart energyTransformerChart1;
    @BindView(R.id.energy_transformer_select2)
    ImageView energyTransformerSelect2;
    @BindView(R.id.energy_transformer_chart2)
    LineDataTwoChart energyTransformerChart2;
    @BindView(R.id.energy_transformer_damge)
    TextView energyTransformerDamge;
    @BindView(R.id.energy_transformer_kwh)
    TextView energyTransformerKwh;
    int transformerId = 0;
    MyTimePickerWheelDialog dialogtime;
    int year=0;
    @BindView(R.id.energy_transformer_year1)
    TextView energyTransformerYear1;
    @BindView(R.id.energy_transformer_year2)
    TextView energyTransformerYear2;
    DataAllbean dataAllbean=new DataAllbean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        mPresenter.getEnergy_TransformerList();
        toolbarText.setText("变压器优化");
        toolbarLeft.setOnClickListener(new View.OnClickListener() {
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
        });

        energyTransformerYear2.setText(DateUtil.getCurrentYear()+"年");
        energyTransformerYear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            energyTransformerChart1.clearData();
            ArrayList<Entry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            //判断数据是否全年，否则动态添加数据
            if(bean.getResult().size()!=12){
                int num = 12 - bean.getResult().size();
                allnum = bean.getResult().size() + num;
                for (int i = 0; i < allnum; i++) {
                    Entry entry = new Entry(i, 0f);
                    if(i>=bean.getResult().size()){
                        int monthNum=i+1;
                        int positionNum = DoubleUtils.getPositionNum(monthNum);
                        if(positionNum==1){
                            listDate.add("0"+monthNum);
                        }else{
                            listDate.add(monthNum+"");
                        }
                        entry.setY((float)0);

                    }else{
                        entry.setY((float) bean.getResult().get(i).getData());
                        String[] split = StringUtil.split(bean.getResult().get(i).getTime(), "-");
                        listDate.add(split[1]);
                    }
                    values.add(entry);
                }
            }else{
                for (int i = 0; i < bean.getResult().size(); i++) {
                    Entry entry = new Entry(i, 0f);
                    entry.setY((float) bean.getResult().get(i).getData());
                    values.add(entry);
                    if(bean.getResult().get(i).getTime()!=null||!bean.getResult().get(i).getTime().equals("")){
                        String[] split = StringUtil.split(bean.getResult().get(i).getTime(), "-");
                        listDate.add(split[1]);
                    }
                }
            }
            energyTransformerChart1.setDataSet(values, "");
            energyTransformerChart1.setDayXAxis(listDate);
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
            energyTransformerChart2.clearData();
            ArrayList<Entry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            //判断数据是否全年，否则动态添加数据
            if(bean.getResult().size()!=12){
                int num = 12 - bean.getResult().size();
                allnum = bean.getResult().size() + num;
                for (int i = 0; i < allnum; i++) {
                    Entry entry = new Entry(i, 0f);
                    if(i>=bean.getResult().size()){
                        int monthNum=i+1;
                        int positionNum = DoubleUtils.getPositionNum(monthNum);
                        if(positionNum==1){
                            listDate.add("0"+monthNum);
                        }else{
                            listDate.add(monthNum+"");
                        }
                        entry.setY((float)0);

                    }else{
                        entry.setY((float) bean.getResult().get(i).getData());
                        String[] split = StringUtil.split(bean.getResult().get(i).getBaseDate(), "-");
                        listDate.add(split[1]);
                    }
                    values.add(entry);
                }
            }else{
                for (int i = 0; i < bean.getResult().size(); i++) {
                    Entry entry = new Entry(i, 0f);
                    entry.setY((float) bean.getResult().get(i).getData());
                    values.add(entry);
                    String[] split = StringUtil.split(bean.getResult().get(i).getBaseDate(), "-");
                    listDate.add(split[1]);
                }
            }

            energyTransformerChart2.setDataSet(values, "");
            energyTransformerChart2.setDayXAxis(listDate);
            energyTransformerChart2.loadChart();
        }else{
            MyHint.myHintDialog(this);
        }
    }

}
