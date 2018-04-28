package cn.tties.energy.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.tties.energy.R;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.chart.LineDataChart;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.MyAllTimeYear;
import cn.tties.energy.common.MyChartXList;
import cn.tties.energy.common.MyHint;
import cn.tties.energy.common.MyNoDoubleClickListener;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.Data_Factorbean;
import cn.tties.energy.presenter.Data_FactorPresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.DoubleUtils;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.view.dialog.BottomStyleDialog;
import cn.tties.energy.view.dialog.MyPopupWindow;
import cn.tties.energy.view.dialog.MyTimePickerWheelTwoDialog;
import cn.tties.energy.view.iview.IData_FactorView;

/**
 * 功率因素
 */
public class Data_FactorActivity extends BaseActivity<Data_FactorPresenter> implements IData_FactorView, View.OnClickListener {
    private static final String TAG = "Data_FactorActivity";
    LinearLayout toolbarLl;
    ImageView toolbarLeft;
    TextView toolbarText;
    TextView factorNum;
    LineDataChart dataFactorChart;
    TextView dataFactorTv;
    LinearLayout dataFactorTime;
    LinearLayout dataFactorElectrical;
    TextView dataFactorEleTv;
    ImageView dataFactorVerify;
    TextView dataFactorText;
    private BottomStyleDialog dialog;
    double num = 0;
    MyTimePickerWheelTwoDialog dialogtime;
    MyAllTimeYear timeYear = new MyAllTimeYear();
    private MyPopupWindow popupWindow;
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
        factorNum= findViewById(R.id.factor_num);
        dataFactorChart= findViewById(R.id.data_factor_chart);
        dataFactorTv= findViewById(R.id.data_factor_tv);
        dataFactorTime= findViewById(R.id.data_factor_time);
        dataFactorElectrical= findViewById(R.id.data_factor_electrical);
        dataFactorEleTv= findViewById(R.id.data_factor_ele_tv);
        dataFactorVerify= findViewById(R.id.data_factor_verify);
        dataFactorText= findViewById(R.id.data_factor_text);
    }

    private void initView() {
        popupWindow = new MyPopupWindow();
        dataFactorVerify.setOnClickListener(this);
        dataFactorText.setOnClickListener(this);
        mPresenter.getAllElectricityData();
        dataFactorTv.setText(DateUtil.getCurrentYear() + "年" + DateUtil.getCurrentMonth() + "月");
        months=DateUtil.getCurrentMonth();
        years=DateUtil.getCurrentYear();
        days = DateUtil.getDays(DateUtil.getCurrentYear(), DateUtil.getCurrentMonth());
        dialogtime = new MyTimePickerWheelTwoDialog(Data_FactorActivity.this);
        toolbarText.setText("功率因数");
        toolbarLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dataFactorTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyNoDoubleClickListener.isFastClick()) {
                    dialogtime.show();
                    dialogtime.setOnCliekTime(new MyTimePickerWheelTwoDialog.OnCliekTime() {
                        @Override
                        public void OnCliekTimeListener(int poaiton) {
                            String tiemMonthBase = timeYear.getTiemMonthBase(poaiton);
                            dataFactorTv.setText(tiemMonthBase);
                            String month = StringUtil.getMonth(tiemMonthBase);
                            String year = StringUtil.getYear(tiemMonthBase);
                            months=(Integer.parseInt(month));
                            years=(Integer.parseInt(year));
                            days = DateUtil.getDays(Integer.parseInt(year), (Integer.parseInt(month)-1));
                            mPresenter.getData_Electric();
                        }
                    });
                }
            }
        });
        dataFactorElectrical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyNoDoubleClickListener.isFastClick()) {
                    if (dialog != null) {
                        dialog.show();
                    }
                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.data_factor_verify:
                popupWindow.showTipPopupWindow(dataFactorVerify, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.data_factor_text:
                popupWindow.showTipPopupWindow(dataFactorText, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
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
        if (bean.getDataList().size() > 0) {
            dataFactorChart.clearData();
            ArrayList<Entry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            MyChartXList myChartXList = new MyChartXList();
            Map map = myChartXList.get(years, months);
            Map XMap = (Map) map.get("XMap");
            for (int i = 0; i < bean.getDataList().size(); i++) {
                num = num + Double.parseDouble(String.valueOf(bean.getDataList().get(i).getD()));
                String split = StringUtil.substring(bean.getDataList().get(i).getFreezeTime(), 5, 10);
                int index = (int) XMap.get(split);
                values.add(new Entry(index, (float)Float.parseFloat(String.valueOf(bean.getDataList().get(i).getD()))));
            }
            //保留三位小数
//            BigDecimal b = new BigDecimal(num / bean.getDataList().size());
//            double df = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();

            double bigDecimal = DoubleUtils.getBigDecimal(num, bean.getDataList().size());
            factorNum.setText(bigDecimal + "");
            XAxis xAxis = dataFactorChart.getXAxis();
            xAxis.setLabelCount(bean.getDataList().size(), true);
            xAxis.setLabelRotationAngle(-50);
            xAxis.setAxisMinimum(0f);
            xAxis.setAxisMaximum(days-1);
            dataFactorChart.setDataSet(values, "");
            dataFactorChart.setDayXAxis((List)map.get("X"));
            dataFactorChart.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    String num = DoubleUtils.getRate((double) value);
                    return num;
                }
            });
            dataFactorChart.loadChart();
        } else {
            MyHint.myHintDialog(this);
        }

    }

    @Override
    public void setAllElectricity(final AllElectricitybean allElectricitybean) {
        if (allElectricitybean.getMeterList().size() > 0) {
            ACache.getInstance().put(Constants.CACHE_OPS_OBJID, allElectricitybean.getLedgerId());
            ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE, 1);
            ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE, DateUtil.getCurrentYear() + "-" + DateUtil.getCurrentMonth());
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
