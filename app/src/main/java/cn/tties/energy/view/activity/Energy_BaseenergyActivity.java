package cn.tties.energy.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tties.energy.R;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.chart.LineDataChart;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.MyAllTimeYear;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Databean;
import cn.tties.energy.model.result.Energy_BasePlanbean;
import cn.tties.energy.presenter.Energy_BaseenergyPresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.view.dialog.MyTimePickerDialog;
import cn.tties.energy.view.dialog.MyTimePickerWheelDialog;
import cn.tties.energy.view.iview.IEnergy_BaseenergyView;

/**
 * 基本电量优化
 */
public class Energy_BaseenergyActivity extends BaseActivity<Energy_BaseenergyPresenter> implements IEnergy_BaseenergyView {
    private static final String TAG = "Energy_BaseenergyActivi";
    @BindView(R.id.toolbar_left)
    ImageView toolbarLeft;
    @BindView(R.id.toolbar_text)
    TextView toolbarText;
    @BindView(R.id.energy_base_kva)
    TextView energyBaseKva;
    @BindView(R.id.energy_base_price)
    TextView energyBasePrice;
    @BindView(R.id.energy_base_select)
    ImageView energyBaseSelect;
    @BindView(R.id.energy_base_monthmax)
    TextView energyBaseMonthmax;
    @BindView(R.id.energy_base_time)
    TextView energyBaseTime;
    @BindView(R.id.energy_base_chart)
    LineDataChart energyBaseChart;
    @BindView(R.id.energy_base_plan_kw1)
    TextView energyBasePlanKw1;
    @BindView(R.id.energy_base_plan_allprice1)
    TextView energyBasePlanAllprice1;
    @BindView(R.id.energy_base_plan_kw2)
    TextView energyBasePlanKw2;
    @BindView(R.id.energy_base_plan_allprice2)
    TextView energyBasePlanAllprice2;
    @BindView(R.id.energy_base_plan_kw3)
    TextView energyBasePlanKw3;
    @BindView(R.id.energy_base_plan_allprice3)
    TextView energyBasePlanAllprice3;
    @BindView(R.id.energy_base_type)
    TextView energyBaseType;
    @BindView(R.id.en_tv3)
    TextView enTv3;
    @BindView(R.id.energy_base_year)
    TextView energyBaseYear;
    MyTimePickerWheelDialog dialogtime;
    DataAllbean allbean=new DataAllbean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        dialogtime=new MyTimePickerWheelDialog(Energy_BaseenergyActivity.this);
        mPresenter.getEnergy_Baseenergy();
        mPresenter.getEnergy_BasePlan();
        toolbarText.setText("基本电费优化");
        toolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        energyBaseYear.setText(DateUtil.getCurrentYear()+"年");
        energyBaseYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

            energyBasePrice.setText(bean.getDataList().get(0).getBaseSum() + "元");
            energyBaseMonthmax.setText(bean.getDataList().get(0).getMaxMD() + "kW");
            energyBaseTime.setText(bean.getDataList().get(0).getMaxMDDate() + "");
        }

    }

    @Override
    public void setEnergy_BaseenergyYearData(Databean bean) {
        if(bean.getDataList().size()>0){
            energyBaseChart.clearData();
            ArrayList<Entry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            for (int i = 0; i < bean.getDataList().size(); i++) {
                Entry entry = new Entry(i, 0f);
                entry.setY((float) bean.getDataList().get(i).getBaseSum());
                values.add(entry);
                if(bean.getDataList().get(i).getBaseDate()!=null||!bean.getDataList().get(i).getBaseDate().equals("")){
                    Log.i(TAG, "setEnergy_BaseenergyYearData: "+bean.getDataList().get(i).getBaseDate());
                    String[] split = StringUtil.split(bean.getDataList().get(i).getBaseDate(), "-");
                    listDate.add(split[1]);
                }

            }
            getChartXCount(energyBaseChart);
            energyBaseChart.setDataSet(values, "");
            energyBaseChart.setDayXAxis(listDate);
            energyBaseChart.loadChart();
        }

    }

    @Override
    public void setEnergy_BasePlanData(Energy_BasePlanbean bean) {
        if (bean.getBestType() == 1) {
            enTv3.setText("报装容量");
        } else {
            enTv3.setText("报装需量");
        }
        energyBasePlanKw1.setText(bean.getBestValue() + "kW");
        energyBasePlanKw2.setText(bean.getVolumeValue() + "kW");
        energyBasePlanKw3.setText(bean.getDemandValue() + "kW");
        energyBasePlanAllprice1.setText(bean.getBestFee() + "元");
        energyBasePlanAllprice2.setText(bean.getVolumeFee() + "元");
        energyBasePlanAllprice3.setText(bean.getDemandFee() + "元");
    }
    //计算x数量
    public void getChartXCount(LineDataChart lineDataChart){
        //得到当前年月 确定chart表x轴加载的数量
        int currentYear = DateUtil.getCurrentYear();
        int currentMonth= DateUtil.getCurrentMonth();
        XAxis xAxis = lineDataChart.getXAxis();
//        xAxis.setLabelRotationAngle(0);
        String baseData = allbean.getBaseData();
        String[] split = StringUtil.split(baseData, "-");
        if(split[0].equals(currentYear+"")){
//            if(currentMonth>8){
//                xAxis.setLabelRotationAngle(-50);
//            }
            xAxis.setLabelCount(currentMonth,true);
        }else{
            xAxis.setLabelCount(12,true);
//            xAxis.setLabelRotationAngle(-50);
        }
    }
}
