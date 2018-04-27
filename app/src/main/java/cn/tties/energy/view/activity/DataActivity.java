package cn.tties.energy.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import cn.tties.energy.R;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.chart.LineDataChart;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.MyAllTimeYear;
import cn.tties.energy.common.MyNoDoubleClickListener;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Databean;
import cn.tties.energy.presenter.DataPresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.DoubleUtils;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.view.dialog.BottomStyleDialog;
import cn.tties.energy.view.dialog.MyPopupWindow;
import cn.tties.energy.view.dialog.MyTimePickerWheelDialog;
import cn.tties.energy.view.iview.IDataView;

/**
 * 电费数据
 */
public class DataActivity extends BaseActivity<DataPresenter> implements View.OnClickListener, IDataView {
    private static final String TAG = "DataActivity";
    LinearLayout toolbarLl;
    ImageView toolbarLeft;
    TextView toolbarText;
    LinearLayout dataTime;
    LinearLayout dataAllelectric;
    TextView dataTimeTv;
    ImageView dataChargeImg1;
    ImageView dataChargeImg2;
    ImageView dataChargeImg3;
    ImageView dataChargeImg4;
    TextView dataAllCharge;
    TextView dataBaseCharge;
    TextView dataYearCharge;
    TextView dataForcesCharge;
    LineDataChart dataChart;
    TextView dataElectricityTv;
    TextView dataChargeTv1;
    TextView dataChargeTv2;
    TextView dataChargeTv3;
    TextView dataChargeTv4;
    private PopupWindow mCurPopupWindow;
    private MyPopupWindow popupWindow;
    private BottomStyleDialog dialog;
    //    MyTimePickerDialog dialogtime;
    MyTimePickerWheelDialog dialogtime;
    DataAllbean allbean = new DataAllbean();
    int year = 0;

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
        dataTime= findViewById(R.id.data_time);
        dataAllelectric= findViewById(R.id.data_allelectric);
        dataTimeTv= findViewById(R.id.data_time_tv);
        dataChargeImg1= findViewById(R.id.data_charge_img1);
        dataChargeImg2= findViewById(R.id.data_charge_img2);
        dataChargeImg3= findViewById(R.id.data_charge_img3);
        dataChargeImg4= findViewById(R.id.data_charge_img4);
        dataAllCharge= findViewById(R.id.data_all_charge);
        dataBaseCharge= findViewById(R.id.data_base_charge);
        dataYearCharge= findViewById(R.id.data_year_charge);
        dataForcesCharge= findViewById(R.id.data_forces_charge);
        dataChart= findViewById(R.id.data_chart);
        dataElectricityTv= findViewById(R.id.data_electricity_tv);
        dataChargeTv1= findViewById(R.id.data_charge_tv1);
        dataChargeTv2= findViewById(R.id.data_charge_tv2);
        dataChargeTv3= findViewById(R.id.data_charge_tv3);
        dataChargeTv4= findViewById(R.id.data_charge_tv4);
        toolbarText= findViewById(R.id.toolbar_text);
        dataChargeImg1.setOnClickListener(this);
        dataChargeImg2.setOnClickListener(this);
        dataChargeImg3.setOnClickListener(this);
        dataChargeImg4.setOnClickListener(this);
        dataChargeTv1.setOnClickListener(this);
        dataChargeTv2.setOnClickListener(this);
        dataChargeTv3.setOnClickListener(this);
        dataChargeTv4.setOnClickListener(this);
    }

    private void initView() {
        popupWindow = new MyPopupWindow();
        int currentYear = DateUtil.getCurrentYear();
        int currentMonth = DateUtil.getCurrentMonth();
        Log.i(TAG, "createArrays: " + currentYear);
        Log.i(TAG, "createArrays: " + currentMonth);
//        dialogtime=new MyTimePickerDialog();
        dialogtime = new MyTimePickerWheelDialog(DataActivity.this);
        toolbarText.setText("电费数据");
        toolbarLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dataTimeTv.setText(DateUtil.getCurrentYear() + "年");
        mPresenter.getAllElectricityData();
        dataTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyNoDoubleClickListener.isFastClick()) {
                    dialogtime.show();
                    dialogtime.setOnCliekTime(new MyTimePickerWheelDialog.OnCliekTime() {
                        @Override
                        public void OnCliekTimeListener(int poaiton) {
                            int tiemBase = MyAllTimeYear.getTiemBase(poaiton);
                            dataTimeTv.setText(tiemBase + "年");
                            mPresenter.getchartData();

                        }
                    });
                }

            }
        });
        //总电量
        dataAllelectric.setOnClickListener(new View.OnClickListener() {
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
    protected void createPresenter() {
        mPresenter = new DataPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data;
    }

    @Override
    public void onBackPressed() {
        if (mCurPopupWindow != null && mCurPopupWindow.isShowing()) {
            mCurPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.data_charge_img1:
                popupWindow.showTipPopupWindow(dataChargeImg1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.data_charge_img2:
                popupWindow.showTipPopupWindow(dataChargeImg2, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.data_charge_img3:
                popupWindow.showTipPopupWindow(dataChargeImg3, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.data_charge_img4:
                popupWindow.showTipPopupWindow(dataChargeImg4, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.data_charge_tv1:
                popupWindow.showTipPopupWindow(dataChargeTv1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.data_charge_tv2:
                popupWindow.showTipPopupWindow(dataChargeTv2, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.data_charge_tv3:
                popupWindow.showTipPopupWindow(dataChargeTv3, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.data_charge_tv4:
                popupWindow.showTipPopupWindow(dataChargeTv4, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    @Override
    public void setDataData(Databean bean) {
        if (bean.getDataList().size() > 0) {
            //总电费
            dataAllCharge.setText("￥" + DoubleUtils.getNum(bean.getDataList().get(0).getTotalSum()));
            //基本电费
            dataBaseCharge.setText("￥" + DoubleUtils.getNum(bean.getDataList().get(0).getBaseSum()));
            //年度电费
            dataYearCharge.setText("￥" + DoubleUtils.getNum(bean.getDataList().get(0).getFeeSum()));
            //力调电费
            dataForcesCharge.setText("￥" + DoubleUtils.getNum(bean.getDataList().get(0).getFouceSum()));
        }


    }

    @Override
    public void setDataChartData(Databean bean) {
        int allnum;
        if (bean.getDataList().size() > 0) {
            int color = Color.parseColor("#38A6FE");
            int color2 = Color.parseColor("#00000000");
            dataChart.clearData();
            ArrayList<Integer> listcolor=new ArrayList<>();
            ArrayList<Entry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            //判断数据是否全年，否则动态添加数据
            if (bean.getDataList().size() != 12) {
                int num = 12 - bean.getDataList().size();
                allnum = bean.getDataList().size() + num;
                for (int i = 0; i < allnum; i++) {
                    Entry entry = new Entry(i,0f);
                    if (i >= bean.getDataList().size()) {
                        int monthNum = i + 1;
                        int positionNum = DoubleUtils.getPositionNum(monthNum);
                        if (positionNum == 1) {
                            listDate.add("0" + monthNum);
                        } else {
                            listDate.add(monthNum + "");
                        }
                        entry.setY((float) 0);
                        listcolor.add(color2);
                    } else {
                        entry.setY((float) bean.getDataList().get(i).getTotalSum());
                        Log.i(TAG, "setEnergy_BaseenergyYearData: " + bean.getDataList().get(i).getBaseDate());
                        String[] split = StringUtil.split(bean.getDataList().get(i).getBaseDate(), "-");
                        listDate.add(split[1]);
                        listcolor.add(color);
                        if(i==bean.getDataList().size()-1){
                            listcolor.add(color2);
                            Log.i(TAG, "setDataChartData: "+i);
                        }
                    }
                    values.add(entry);

                }
            } else {
                for (int i = 0; i < bean.getDataList().size(); i++) {
                    bean.getDataList().get(i).getCost();
                    Entry entry = new Entry(i, 0f);
                    entry.setY((float) bean.getDataList().get(i).getTotalSum());
                    values.add(entry);
                    String[] split = StringUtil.split(bean.getDataList().get(i).getBaseDate(), "-");
                    listDate.add(split[1]);
                    listcolor.add(color);

                }
            }
            LineDataSet dataSet = dataChart.setDataSet(values, "");
            dataSet.setColors(listcolor);
            dataChart.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    String num = DoubleUtils.getNum((double) value);
                    return num;
                }
            });
            dataChart.setDayXAxis(listDate);
            dataChart.loadChart();
        }

    }

    @Override
    public void setAllElectricity(final AllElectricitybean allElectricitybean) {
        if (allElectricitybean.getMeterList().size() > 0) {
            ACache.getInstance().put(Constants.CACHE_OPS_OBJID, allElectricitybean.getLedgerId());
            ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE, 1);
            ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE, DateUtil.getCurrentYear() + "-" + (DateUtil.getCurrentMonth() - 1));
            mPresenter.getData();
            mPresenter.getchartData();
            dataElectricityTv.setText("总电量");
            dialog = new BottomStyleDialog(DataActivity.this, allElectricitybean);

            dialog.setCliekAllElectricity(new BottomStyleDialog.OnCliekAllElectricity() {
                @Override
                public void OnCliekAllElectricityListener(int poaiton) {
                    if (poaiton == 0) {
                        long ledgerId = allElectricitybean.getLedgerId();
                        dataElectricityTv.setText("总电量");
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJID, ledgerId);
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE, 1);

                    }
                    if (poaiton > 0) {
                        long meterId = allElectricitybean.getMeterList().get(poaiton - 1).getMeterId();
                        dataElectricityTv.setText(allElectricitybean.getMeterList().get(poaiton - 1).getMeterName());
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJID, meterId);
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE, 2);

                    }
                    mPresenter.getchartData();
                }
            });
        }
    }
}
