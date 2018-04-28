package cn.tties.energy.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
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
import cn.tties.energy.application.MyApplication;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.chart.LineDataChart;
import cn.tties.energy.chart.LineDataTwoChart;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.MyAllTimeYear;
import cn.tties.energy.common.MyChartXList;
import cn.tties.energy.common.MyHint;
import cn.tties.energy.common.MyNoDoubleClickListener;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Data_HaveKwbean;
import cn.tties.energy.model.result.Data_NoKvarbean;
import cn.tties.energy.presenter.Data_RatePresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.DoubleUtils;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.view.dialog.BottomStyleDialog;
import cn.tties.energy.view.dialog.MyTimePickerWheelTwoDialog;
import cn.tties.energy.view.iview.IData_RateView;

/**
 * 功率数据
 */
public class Data_RateActivity extends BaseActivity<Data_RatePresenter> implements IData_RateView {
    private static final String TAG = "Data_RateActivity";
    LinearLayout toolbarLl;
    ImageView toolbarLeft;
    TextView toolbarText;
//    TextView havekwTv;
//    TextView havakwTime;
//    TextView havakwYear;
//    TextView nokvarTv;
//    TextView nokvarTime;
//    TextView nokvarYear;
    LineDataTwoChart havakwChart;
    LineDataChart nokvarChart;
    TextView dataRateTv;
    LinearLayout dataRateTime;
    LinearLayout dataRateElectrical;
    TextView dataRateEleTv;
    private BottomStyleDialog dialog;
    MyTimePickerWheelTwoDialog dialogtime;
    MyAllTimeYear timeYear=new MyAllTimeYear();
    DataAllbean allbean=new DataAllbean();
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
        havakwChart= findViewById(R.id.havakw_chart);
        nokvarChart= findViewById(R.id.nokvar_chart);
        dataRateTv= findViewById(R.id.data_rate_tv);
        dataRateTime= findViewById(R.id.data_rate_time);
        dataRateElectrical= findViewById(R.id.data_rate_electrical);
        dataRateEleTv= findViewById(R.id.data_rate_ele_tv);
    }

    private void initView() {
        mPresenter.getAllElectricityData();
        dialogtime = new MyTimePickerWheelTwoDialog(Data_RateActivity.this);
        dataRateTv.setText(DateUtil.getCurrentYear()+"年"+DateUtil.getCurrentMonth()+"月");
        months=DateUtil.getCurrentMonth();
        years=DateUtil.getCurrentYear();
        days = DateUtil.getDays(DateUtil.getCurrentYear(), DateUtil.getCurrentMonth());
        toolbarText.setText("功率数据");
        toolbarLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dataRateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyNoDoubleClickListener.isFastClick()){
                    dialogtime.show();
                    dialogtime.setOnCliekTime(new MyTimePickerWheelTwoDialog.OnCliekTime() {
                        @Override
                        public void OnCliekTimeListener(int poaiton) {
                            String tiemMonthBase = timeYear.getTiemMonthBase(poaiton);
                            dataRateTv.setText(tiemMonthBase);
                            String month = StringUtil.getMonth(tiemMonthBase);
                            String year = StringUtil.getYear(tiemMonthBase);
                            months=(Integer.parseInt(month));
                            years=(Integer.parseInt(year));
                            days = DateUtil.getDays(Integer.parseInt(year), (Integer.parseInt(month)-1));
                            mPresenter.getData_HaveKwData();
                            mPresenter.getData_NoKvarKwData();
                        }
                    });
                }

            }
        });
        dataRateElectrical.setOnClickListener(new View.OnClickListener() {

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
        mPresenter = new Data_RatePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data__rate;
    }

    @Override
    public void setData_HaveKWData(Data_HaveKwbean data_haveKwbean) {
        if(data_haveKwbean.getDataList().size()>0){
            havakwChart.clearData();
            ArrayList<Entry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            MyChartXList myChartXList = new MyChartXList();
            Map map = myChartXList.get(years, months);
            Map XMap = (Map) map.get("XMap");
            for (int i = 0; i < data_haveKwbean.getDataList().size(); i++) {
                String split = StringUtil.substring(data_haveKwbean.getDataList().get(i).getFreezeTime(),5,10);
                int index = (int) XMap.get(split);
                values.add(new Entry(index, (float)(float)Float.parseFloat(String.valueOf(data_haveKwbean.getDataList().get(i).getD()))));
            }
            XAxis xAxis = havakwChart.getXAxis();
            xAxis.setLabelCount(data_haveKwbean.getDataList().size(),true);
            xAxis.setLabelRotationAngle(-50);
            xAxis.setAxisMinimum(0f);
            xAxis.setAxisMaximum(days-1);
            havakwChart.setDataSet(values, "");
            havakwChart.setDayXAxis((List)map.get("X"));
            havakwChart.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    String num = DoubleUtils.getNum((double) value);
                    return num;
                }
            });
            havakwChart.loadChart();
        }else {
            MyHint.myHintDialog(this);
        }

    }

    @Override
    public void setData_NoKvarData(Data_NoKvarbean data_noKvarbean) {
        if(data_noKvarbean.getDataList().size()>0){
            nokvarChart.clearData();
            ArrayList<Entry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            MyChartXList myChartXList = new MyChartXList();
            Map map = myChartXList.get(years, months);
            Map XMap = (Map) map.get("XMap");
            for (int i = 0; i < data_noKvarbean.getDataList().size(); i++) {
                String split = StringUtil.substring(data_noKvarbean.getDataList().get(i).getFreezeTime(),5,10);
                int index = (int) XMap.get(split);
                values.add(new Entry(index, (float)Float.parseFloat(String.valueOf(data_noKvarbean.getDataList().get(i).getD()))));

            }
            XAxis xAxis = nokvarChart.getXAxis();
            xAxis.setLabelCount(data_noKvarbean.getDataList().size(),true);
            xAxis.setLabelRotationAngle(-50);
            xAxis.setAxisMinimum(0f);
            xAxis.setAxisMaximum(days-1);
            nokvarChart.setDataSet(values, "");
            nokvarChart.setDayXAxis((List)map.get("X"));
            nokvarChart.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    String num = DoubleUtils.getNum((double) value);
                    return num;
                }
            });
            nokvarChart.loadChart();
        }else {
            MyHint.myHintDialog(this);
        }

    }

    @Override
    public void setAllElectricity(final AllElectricitybean allElectricitybean) {
        if(allElectricitybean.getMeterList().size()>0){
            ACache.getInstance().put(Constants.CACHE_OPS_OBJID, allElectricitybean.getLedgerId());
            ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE, 1);
            ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE, DateUtil.getCurrentYear() + "-" + DateUtil.getCurrentMonth());
            mPresenter.getData_HaveKwData();
            mPresenter.getData_NoKvarKwData();
            dataRateEleTv.setText("总电量");
            dialog = new BottomStyleDialog(Data_RateActivity.this, allElectricitybean);
            dialog.setCliekAllElectricity(new BottomStyleDialog.OnCliekAllElectricity() {
                @Override
                public void OnCliekAllElectricityListener(int poaiton) {
                    if (poaiton == 0) {
                        long ledgerId = allElectricitybean.getLedgerId();
                        dataRateEleTv.setText("总电量");
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJID, ledgerId);
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE, 1);

                    }
                    if (poaiton > 0) {
                        long meterId = allElectricitybean.getMeterList().get(poaiton - 1).getMeterId();
                        dataRateEleTv.setText(allElectricitybean.getMeterList().get(poaiton - 1).getMeterName());
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJID, meterId);
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE, 2);

                    }
                    mPresenter.getData_HaveKwData();
                    mPresenter.getData_NoKvarKwData();

                }
            });
        }

    }
}
