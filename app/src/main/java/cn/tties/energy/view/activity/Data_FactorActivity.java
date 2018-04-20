package cn.tties.energy.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;

import java.math.BigDecimal;
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
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Data_Factorbean;
import cn.tties.energy.presenter.Data_FactorPresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.utils.ToastUtil;
import cn.tties.energy.view.dialog.BottomStyleDialog;
import cn.tties.energy.view.dialog.MyTimePickerDialog;
import cn.tties.energy.view.dialog.MyTimePickerWheelTwoDialog;
import cn.tties.energy.view.iview.IData_FactorView;

/**
 * 功率因素
 */
public class Data_FactorActivity extends BaseActivity<Data_FactorPresenter> implements IData_FactorView {

    @BindView(R.id.toolbar_left)
    ImageView toolbarLeft;
    @BindView(R.id.toolbar_text)
    TextView toolbarText;
    @BindView(R.id.factor_num)
    TextView factorNum;
    @BindView(R.id.data_factor_chart)
    LineDataChart dataFactorChart;
    @BindView(R.id.data_factor_tv)
    TextView dataFactorTv;
    @BindView(R.id.data_factor_time)
    LinearLayout dataFactorTime;
    @BindView(R.id.data_factor_electrical)
    LinearLayout dataFactorElectrical;
    @BindView(R.id.data_factor_ele_tv)
    TextView dataFactorEleTv;
    private BottomStyleDialog dialog;
    double num = 0;
    MyTimePickerWheelTwoDialog dialogtime;
    MyAllTimeYear timeYear=new MyAllTimeYear();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        mPresenter.getAllElectricityData();
        dataFactorTv.setText(DateUtil.getCurrentYear()+"年"+DateUtil.getCurrentMonth()+"月");
        dialogtime = new MyTimePickerWheelTwoDialog(Data_FactorActivity.this);
        toolbarText.setText("功率因数");
        toolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dataFactorTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogtime.show();
                dialogtime.setOnCliekTime(new MyTimePickerWheelTwoDialog.OnCliekTime() {
                    @Override
                    public void OnCliekTimeListener(int poaiton) {
                        String tiemMonthBase = timeYear.getTiemMonthBase(poaiton);
                        dataFactorTv.setText(tiemMonthBase);
                        mPresenter.getData_Electric();
                    }
                });
            }
        });
        dataFactorElectrical.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.show();
                }

//                ToastUtil.showShort(Data_NoActivity.this,"");
            }
        });
    }

    @Override
    protected void createPresenter() {
        mPresenter = new Data_FactorPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data__factor;
    }

    @Override
    public void setData_FactorData(Data_Factorbean bean) {
        if(bean.getDataList().size()>0){
            dataFactorChart.clearData();
            ArrayList<Entry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            for (int i = 0; i < bean.getDataList().size(); i++) {
                num=num+Double.parseDouble(String.valueOf(bean.getDataList().get(i).getD()));
                Entry entry = new Entry(i, 0f);
                entry.setY((float) Float.parseFloat(String.valueOf(bean.getDataList().get(i).getD())));
                values.add(entry);
                if(bean.getDataList().get(i).getFreezeTime()!=null||bean.getDataList().get(i).getFreezeTime().equals("")){
                    String split = StringUtil.substring(bean.getDataList().get(i).getFreezeTime(),5,10);
                    listDate.add(split);
                }

            }
            //保留三位小数
//            BigDecimal b = new BigDecimal(num / bean.getDataList().size());
//            double df = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();

            double bigDecimal = StringUtil.getBigDecimal(num, bean.getDataList().size());
            factorNum.setText(bigDecimal + "");
            XAxis xAxis = dataFactorChart.getXAxis();
            xAxis.setLabelCount(bean.getDataList().size(),true);
            xAxis.setLabelRotationAngle(-50);
            dataFactorChart.setDataSet(values, "");
            dataFactorChart.setDayXAxis(listDate);
            dataFactorChart.loadChart();
        }

    }

    @Override
    public void setAllElectricity(final AllElectricitybean allElectricitybean) {
        if(allElectricitybean.getMeterList().size()>0){
            ACache.getInstance().put(Constants.CACHE_OPS_OBJID, allElectricitybean.getLedgerId());
            ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE, 1);
            ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE, DateUtil.getCurrentYear()+"-"+DateUtil.getCurrentMonth());
            mPresenter.getData_Electric();
            dataFactorEleTv.setText("总电量");
            dialog = new BottomStyleDialog(Data_FactorActivity.this, allElectricitybean);
            dialog.setCliekAllElectricity(new BottomStyleDialog.OnCliekAllElectricity() {
                @Override
                public void OnCliekAllElectricityListener(int poaiton) {
                    if (poaiton == 0) {
                        long ledgerId = allElectricitybean.getLedgerId();
                        dataFactorEleTv.setText("总电量");
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJID, ledgerId);
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE, 1);

                    }
                    if (poaiton > 0) {
                        long meterId = allElectricitybean.getMeterList().get(poaiton - 1).getMeterId();
                        dataFactorEleTv.setText(allElectricitybean.getMeterList().get(poaiton - 1).getMeterName());
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJID, meterId);
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE, 2);

                    }
                    mPresenter.getData_Electric();
                }
            });
        }

    }
}
