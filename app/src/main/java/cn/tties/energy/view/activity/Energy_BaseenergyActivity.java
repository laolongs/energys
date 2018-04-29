package cn.tties.energy.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;

import java.util.ArrayList;
import java.util.List;

import cn.tties.energy.R;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.chart.LineDataChart;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.MyAllTimeYear;
import cn.tties.energy.common.MyChartXList;
import cn.tties.energy.common.MyHint;
import cn.tties.energy.common.MyNoDoubleClickListener;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Databean;
import cn.tties.energy.model.result.Energy_BasePlanbean;
import cn.tties.energy.presenter.Energy_BaseenergyPresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.DoubleUtils;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.view.dialog.CriHintDialog;
import cn.tties.energy.view.dialog.CriProgressDialog;
import cn.tties.energy.view.dialog.MyTimePickerWheelDialog;
import cn.tties.energy.view.iview.IEnergy_BaseenergyView;

/**
 * 基本电量优化
 */
public class Energy_BaseenergyActivity extends BaseActivity<Energy_BaseenergyPresenter> implements IEnergy_BaseenergyView {
    private static final String TAG = "Energy_BaseenergyActivi";
    LinearLayout toolbarLl;
    ImageView toolbarLeft;
    TextView toolbarText;
    TextView energyBaseKva;
    TextView energyBasePrice;
    ImageView energyBaseSelect;
    TextView energyBaseMonthmax;
    TextView energyBaseTime;
    LineDataChart energyBaseChart;
    TextView energyBasePlanKw1;
    TextView energyBasePlanAllprice1;
    TextView energyBasePlanKw2;
    TextView energyBasePlanAllprice2;
    TextView energyBasePlanKw3;
    TextView energyBasePlanAllprice3;
    TextView energyBaseType;
    TextView enTv3;
    TextView energyBaseYear;
    MyTimePickerWheelDialog dialogtime;
    DataAllbean allbean=new DataAllbean();
    CriProgressDialog dialogPgs;
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
        energyBaseKva= findViewById(R.id.energy_base_kva);
        energyBasePrice= findViewById(R.id.energy_base_price);
        energyBaseSelect= findViewById(R.id.energy_base_select);
        energyBaseMonthmax= findViewById(R.id.energy_base_monthmax);
        energyBaseTime= findViewById(R.id.energy_base_time);
        energyBaseChart= findViewById(R.id.energy_base_chart);
        energyBasePlanKw1= findViewById(R.id.energy_base_plan_kw1);
        energyBasePlanAllprice1= findViewById(R.id.energy_base_plan_allprice1);
        energyBasePlanKw2= findViewById(R.id.energy_base_plan_kw2);
        energyBasePlanAllprice2= findViewById(R.id.energy_base_plan_allprice2);
        energyBasePlanKw3= findViewById(R.id.energy_base_plan_kw3);
        energyBasePlanAllprice3= findViewById(R.id.energy_base_plan_allprice3);
        energyBaseType= findViewById(R.id.energy_base_type);
        enTv3= findViewById(R.id.en_tv3);
        energyBaseYear= findViewById(R.id.energy_base_year);
    }

    private void initView() {
        dialogPgs=new CriProgressDialog(this);
        dialogPgs.loadDialog("加载中...");
        dialogtime=new MyTimePickerWheelDialog(Energy_BaseenergyActivity.this);
        mPresenter.getEnergy_Baseenergy();
        mPresenter.getEnergy_BasePlan();
        toolbarText.setText("基本电费优化");
        toolbarLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        energyBaseYear.setText(DateUtil.getCurrentYear()+"年");
        energyBaseYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyNoDoubleClickListener.isFastClick()){
                    dialogtime.show();
                    dialogtime.setOnCliekTime(new MyTimePickerWheelDialog.OnCliekTime() {
                        @Override
                        public void OnCliekTimeListener(int poaiton) {
                            int tiemBase = MyAllTimeYear.getTiemBase(poaiton);
                            energyBaseYear.setText(tiemBase+"年");

                            mPresenter.getEnergy_BaseenergyYear();
                        }

                    });
                }

            }
        });
    }

    @Override
    protected void createPresenter() {
        mPresenter = new Energy_BaseenergyPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_energy__baseenergy;
    }


    @Override
    public void setEnergy_BaseenergyData(Databean bean) {
        if(bean.getDataList().size()>0){
            ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE, DateUtil.getCurrentYear() + "-" + DateUtil.getCurrentMonth());
            mPresenter.getEnergy_BaseenergyYear();
            energyBaseKva.setText(bean.getDataList().get(0).getVolume() + "kVA");
            if (bean.getDataList().get(0).getDeclareType() == 1) {
                energyBaseType.setText("容量");
            } else {
                energyBaseType.setText("需量");
            }

            energyBasePrice.setText(DoubleUtils.getNum(bean.getDataList().get(0).getBaseSum())+"元");
            energyBaseMonthmax.setText(DoubleUtils.getNum(bean.getDataList().get(0).getMaxMD()) + "kW");
            energyBaseTime.setText(bean.getDataList().get(0).getMaxMDDate() + "");
        }

    }

    @Override
    public void setEnergy_BaseenergyYearData(Databean bean) {

        if(bean.getDataList().size()>0){
            energyBaseChart.clearData();
            ArrayList<Entry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
                for (int i = 0; i <bean.getDataList().size(); i++) {
                    values.add(new Entry(i,(float) bean.getDataList().get(i).getBaseSum()));

                }
             energyBaseChart.setDataSet(values, "");

            energyBaseChart.setDayXAxis(listDate);
            energyBaseChart.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    int i = (int) value;
                    String str = DoubleUtils.getThousandNum(i)+ "KW";
                    return str;
                }
            });
            energyBaseChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    MyChartXList myChartXList = new MyChartXList();
                    String s = myChartXList.getlist().get((int) value);
                    return s;
                }
            });
            energyBaseChart.loadChart();

        }else{
            MyHint.myHintDialog(this);
        }

    }
    @Override
    public void setEnergy_BasePlanData(Energy_BasePlanbean bean) {
        dialogPgs.removeDialog();
        if (bean.getBestType() == 1) {
            enTv3.setText("报装容量");
        } else {
            enTv3.setText("报装需量");
        }
        energyBasePlanKw1.setText(DoubleUtils.getNum(bean.getBestValue()) + "kW");
        energyBasePlanKw2.setText(DoubleUtils.getNum(bean.getVolumeValue()) + "kW");
        energyBasePlanKw3.setText(DoubleUtils.getNum(bean.getDemandValue()) + "kW");
        energyBasePlanAllprice1.setText( DoubleUtils.getNum(bean.getBestFee())+"元");
        energyBasePlanAllprice2.setText(DoubleUtils.getNum(bean.getVolumeFee())+"元");
        energyBasePlanAllprice3.setText(DoubleUtils.getNum(bean.getDemandFee())+"元");

    }
}
