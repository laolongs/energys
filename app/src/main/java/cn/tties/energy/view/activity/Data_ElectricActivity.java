package cn.tties.energy.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import cn.tties.energy.R;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.chart.LineDataChart;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.MyAllTimeYear;
import cn.tties.energy.common.MyHint;
import cn.tties.energy.common.MyNoDoubleClickListener;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Data_Electricbean;
import cn.tties.energy.presenter.Data_ElectricPresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.DoubleUtils;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.view.dialog.BottomStyleDialog;
import cn.tties.energy.view.dialog.MyPopupWindow;
import cn.tties.energy.view.dialog.MyTimePickerWheelTwoDialog;
import cn.tties.energy.view.iview.IData_ElectricView;

/**
 * 电量数据
 */
public class Data_ElectricActivity extends BaseActivity<Data_ElectricPresenter> implements IData_ElectricView, View.OnClickListener {
    private static final String TAG = "Data_ElectricActivity";
    LinearLayout toolbarLl;
    ImageView toolbarLeft;
    TextView electricalNum;
    LineDataChart electricalChart;
    TextView toolbarText;
    TextView dataElectricalTv;
    LinearLayout dataElectricalTime;
    LinearLayout dataAllelectricElectrical;
    TextView dataElectricalTimeTv;
    ImageView electricalVerify;
    TextView electricalTv;
    private BottomStyleDialog dialog;
    double num = 0;
    DataAllbean allbean = new DataAllbean();
    MyTimePickerWheelTwoDialog dialogtime;
    MyAllTimeYear timeYear = new MyAllTimeYear();
    private MyPopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFindView();
        initView();
    }

    private void initFindView() {
        toolbarLl= findViewById(R.id.toolbar_ll);
        toolbarLeft= findViewById(R.id.toolbar_left);
        electricalNum= findViewById(R.id.electrical_num);
        electricalChart= findViewById(R.id.electrical_chart);
        toolbarText= findViewById(R.id.toolbar_text);
        dataElectricalTv= findViewById(R.id.data_electrical_tv);
        dataElectricalTime= findViewById(R.id.data_electrical_time);
        dataAllelectricElectrical= findViewById(R.id.data_allelectric_electrical);
        dataElectricalTimeTv= findViewById(R.id.data_electrical_time_tv);
        electricalVerify= findViewById(R.id.electrical_verify);
        electricalTv= findViewById(R.id.electrical_tv);
    }

    private void initView() {
        popupWindow = new MyPopupWindow();
        electricalVerify.setOnClickListener(this);
        electricalTv.setOnClickListener(this);
        mPresenter.getAllElectricityData();
        dataElectricalTv.setText(DateUtil.getCurrentYear() + "年" + DateUtil.getCurrentMonth() + "月");
        dialogtime = new MyTimePickerWheelTwoDialog(Data_ElectricActivity.this);
        toolbarText.setText("电量数据");
        toolbarLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dataElectricalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogtime.show();
                dialogtime.setOnCliekTime(new MyTimePickerWheelTwoDialog.OnCliekTime() {
                    @Override
                    public void OnCliekTimeListener(int poaiton) {
                        if (MyNoDoubleClickListener.isFastClick()) {
                            String tiemMonthBase = timeYear.getTiemMonthBase(poaiton);
                            dataElectricalTv.setText(tiemMonthBase);
                            mPresenter.getData_Electric();
                        }
                    }
                });
            }
        });
        dataAllelectricElectrical.setOnClickListener(new View.OnClickListener() {
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
            case R.id.electrical_verify:
                popupWindow.showTipPopupWindow(electricalVerify, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.electrical_tv:
                popupWindow.showTipPopupWindow(electricalTv, new View.OnClickListener() {
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
        mPresenter = new Data_ElectricPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data__electric;
    }


    @Override
    public void setData_ElectricData(Data_Electricbean bean) {

        if (bean.getDataList().size() > 0) {
            electricalChart.clearData();
            Log.i(TAG, "setData_ElectricData: " + "zzzzzzzzz");
            ArrayList<Entry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            for (int i = 0; i < bean.getDataList().size(); i++) {
                num = num + bean.getDataList().get(i).getA();
                Entry entry = new Entry(i, 0f);
                entry.setY((float) bean.getDataList().get(i).getA());
                values.add(entry);
                if (bean.getDataList().get(i).getFreezeTime() != null || bean.getDataList().get(i).getFreezeTime().equals("")) {
                    String split = StringUtil.substring(bean.getDataList().get(i).getFreezeTime(), 5, 10);
                    listDate.add(split);
                }

            }
            XAxis xAxis = electricalChart.getXAxis();
            xAxis.setLabelCount(bean.getDataList().size(), true);
            xAxis.setLabelRotationAngle(-50);
            electricalNum.setText("￥" + DoubleUtils.getNum(num));
            electricalChart.setDataSet(values, "");
            electricalChart.setDayXAxis(listDate);
            electricalChart.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    String num = DoubleUtils.getNum((double) value);
                    return num;
                }
            });
            electricalChart.loadChart();
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
            dataElectricalTimeTv.setText("总电量");
            dialog = new BottomStyleDialog(Data_ElectricActivity.this, allElectricitybean);
            dialog.setCliekAllElectricity(new BottomStyleDialog.OnCliekAllElectricity() {
                @Override
                public void OnCliekAllElectricityListener(int poaiton) {
                    if (poaiton == 0) {
                        long ledgerId = allElectricitybean.getLedgerId();
                        dataElectricalTimeTv.setText("总电量");
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJID, ledgerId);
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE, 1);

                    }
                    if (poaiton > 0) {
                        long meterId = allElectricitybean.getMeterList().get(poaiton - 1).getMeterId();
                        dataElectricalTimeTv.setText(allElectricitybean.getMeterList().get(poaiton - 1).getMeterName());
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJID, meterId);
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE, 2);

                    }
                    mPresenter.getData_Electric();
                }
            });
        }

    }
}
