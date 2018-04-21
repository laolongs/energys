package cn.tties.energy.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tties.energy.R;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.chart.BarDataChart;
import cn.tties.energy.chart.BarDataCharttwo;
import cn.tties.energy.chart.LineDataChart;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.MyAllTimeYear;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.DataFragmentbean;
import cn.tties.energy.model.result.Databean;
import cn.tties.energy.presenter.Energy_ForcePresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.DoubleUtils;
import cn.tties.energy.utils.StringUtil;

import cn.tties.energy.view.dialog.MyTimePickerWheelDialog;
import cn.tties.energy.view.iview.IEnergy_ForceView;

/**
 * 力调电量优化
 */
public class Energy_ForceActivity extends BaseActivity<Energy_ForcePresenter> implements IEnergy_ForceView {
    private static final String TAG = "Energy_ForceActivity";
    @BindView(R.id.toolbar_left)
    ImageView toolbarLeft;
    @BindView(R.id.toolbar_text)
    TextView toolbarText;
    @BindView(R.id.energy_force_havekw)
    TextView energyForceHavekw;
    @BindView(R.id.energy_force_nokvar)
    TextView energyForceNokvar;
    @BindView(R.id.energy_force_num)
    TextView energyForceNum;
    @BindView(R.id.energy_force_select)
    ImageView energyForceSelect;
    @BindView(R.id.energy_force_chart1)
    LineDataChart energyForceChart1;
    @BindView(R.id.energy_force_chart2)
    BarDataCharttwo energyForceChart2;
    @BindView(R.id.energy_force_year)
    TextView energyForceYear;
    MyTimePickerWheelDialog dialogtime;
    int year=0;
    int currentYear;
    int currentMonth;
    DataAllbean dataAllbean=new DataAllbean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        dialogtime=new MyTimePickerWheelDialog(Energy_ForceActivity.this);
        //当月
        mPresenter.getEnergy_Force();

        mPresenter.getEnergy_Forcecharge();
        toolbarText.setText("力调电量优化");
        toolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        energyForceYear.setText(DateUtil.getCurrentYear()+"年");
        //选择年
        energyForceYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogtime.show();
                dialogtime.setOnCliekTime(new MyTimePickerWheelDialog.OnCliekTime() {
                    @Override
                    public void OnCliekTimeListener(int poaiton) {
                        int tiemBase = MyAllTimeYear.getTiemBase(poaiton);
                        energyForceYear.setText(tiemBase+"年");
                        mPresenter.getEnergy_ForcechartData();
                    }
                });
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
            ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE, DateUtil.getCurrentYear()+"-01");
            mPresenter.getEnergy_ForcechartData();
            //有功功率
            energyForceHavekw.setText( DoubleUtils.getNum(bean.getDataList().get(0).getAp()) + "kW");
            //无功
            energyForceNokvar.setText(DoubleUtils.getNegative(bean.getDataList().get(0).getRp()) + "kVar");
            //因数
            energyForceNum.setText(DoubleUtils.getRate(bean.getDataList().get(0).getRate()) + "");
        }

    }

    @Override
    public void setEnergy_ForceChartData(Databean bean) {
        if(bean.getDataList().size()>0){
            energyForceChart1.clearData();
            ArrayList<Entry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            for (int i = 0; i < bean.getDataList().size(); i++) {
                Entry entry = new Entry(i, 0f);
                entry.setY((float) bean.getDataList().get(i).getRate());
                values.add(entry);
                if(bean.getDataList().get(i).getBaseDate()!=null||!bean.getDataList().get(i).getBaseDate().equals("")){
                    String[] split = StringUtil.split(bean.getDataList().get(i).getBaseDate(), "-");
                    listDate.add(split[1]);
                }

            }
            getChartXCount(energyForceChart1);
            energyForceChart1.setDataSet(values, "");
            energyForceChart1.setDayXAxis(listDate);
            energyForceChart1.loadChart();
        }

    }
    //每月节约电费
    @Override
    public void setEnergy_ForceCharge(DataFragmentbean bean) {
        if (bean.getDataList().size() > 0) {
            energyForceChart2.clearData();
//            double num;
            ArrayList<BarEntry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            for (int i = 0; i < bean.getDataList().size(); i++) {
//                Log.i(TAG, "setEnergy_ForceCharge111: "+bean.getDataList().get(i).getFouceSum());
//                Log.i(TAG, "setEnergy_ForceCharge222: "+bean.getDataList().get(i+1).getFouceSum());
//                num = bean.getDataList().get(i).getFouceSum() - bean.getDataList().get(i++).getFouceSum();
//                i--;
                BarEntry entry = new BarEntry(i, 0f);
//                Log.i(TAG, "setEnergy_ForceCharge: "+num);
                entry.setY((float) bean.getDataList().get(i).getFouceSum()*-1);
                values.add(entry);
                if(bean.getDataList().get(i).getBaseDate()!=null||!bean.getDataList().get(i).getBaseDate().equals("")){
                    String[] split = StringUtil.split(bean.getDataList().get(i).getBaseDate(), "-");
                    listDate.add(split[1]+"月");
                }

            }
            energyForceChart2.setDataSet(values, "");
            energyForceChart2.setDayXAxis(listDate);
            energyForceChart2.loadChart();
        }

    }
    //计算x数量
    public void getChartXCount(LineDataChart lineDataChart){
        //得到当前年月 确定chart表x轴加载的数量
        currentYear = DateUtil.getCurrentYear();
        currentMonth= DateUtil.getCurrentMonth();
        XAxis xAxis = lineDataChart.getXAxis();
//        xAxis.setLabelRotationAngle(0);
        String baseData = dataAllbean.getBaseData();
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
