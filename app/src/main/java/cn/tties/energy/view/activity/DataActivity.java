package cn.tties.energy.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

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
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Databean;
import cn.tties.energy.presenter.DataPresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.utils.ToastUtil;
import cn.tties.energy.view.dialog.BottomStyleDialog;
import cn.tties.energy.view.dialog.MyPopupWindow;
import cn.tties.energy.view.dialog.MyTimePickerDialog;
import cn.tties.energy.view.iview.IDataView;

/**
 * 电费数据
 */
public class DataActivity extends BaseActivity<DataPresenter> implements View.OnClickListener, IDataView {

    @BindView(R.id.toolbar_left)
    ImageView toolbarLeft;
    @BindView(R.id.toolbar_text)
    TextView toolbarText;
    @BindView(R.id.data_time)
    LinearLayout dataTime;
    @BindView(R.id.data_allelectric)
    LinearLayout dataAllelectric;
    @BindView(R.id.data_time_tv)
    TextView dataTimeTv;
    @BindView(R.id.data_charge_img1)
    ImageView dataChargeImg1;
    @BindView(R.id.data_charge_img2)
    ImageView dataChargeImg2;
    @BindView(R.id.data_charge_img3)
    ImageView dataChargeImg3;
    @BindView(R.id.data_charge_img4)
    ImageView dataChargeImg4;
    @BindView(R.id.data_all_charge)
    TextView dataAllCharge;
    @BindView(R.id.data_base_charge)
    TextView dataBaseCharge;
    @BindView(R.id.data_year_charge)
    TextView dataYearCharge;
    @BindView(R.id.data_forces_charge)
    TextView dataForcesCharge;
    @BindView(R.id.data_chart)
    LineDataChart dataChart;
    @BindView(R.id.data_electricity_tv)
    TextView dataElectricityTv;
    private PopupWindow mCurPopupWindow;
    private MyPopupWindow popupWindow;
    private BottomStyleDialog dialog;
    MyTimePickerDialog dialogtime;
    DataAllbean allbean=new DataAllbean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        popupWindow = new MyPopupWindow();
        dataChargeImg1.setOnClickListener(this);
        dataChargeImg2.setOnClickListener(this);
        dataChargeImg3.setOnClickListener(this);
        dataChargeImg4.setOnClickListener(this);
        initView();

    }

    private void initView() {
        dialogtime=new MyTimePickerDialog();
        toolbarText.setText("电费数据");
        toolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dataTimeTv.setText(DateUtil.getCurrentYear()+"年"+DateUtil.getCurrentMonth()+"月");
        mPresenter.getAllElectricityData();
        dataTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogtime.getTimeYearPickerDialog(DataActivity.this);
                dialogtime.setOnTimeClick(new MyTimePickerDialog.OnTimeClick() {
                    @Override
                    public void OnTimeClickListener(String text) {
                        ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,text+"-01");
                        dataTimeTv.setText(text+"年");
                        mPresenter.getData();
                        mPresenter.getchartData();
                    }
                });

            }
        });
        //总电量
        dataAllelectric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.show();

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
                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.data_charge_img2:
                popupWindow.showTipPopupWindow(dataChargeImg2, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.data_charge_img3:
                popupWindow.showTipPopupWindow(dataChargeImg3, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.data_charge_img4:
                popupWindow.showTipPopupWindow(dataChargeImg4, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    @Override
    public void setDataData(Databean bean) {
        if(bean.getDataList().size()>0){
            //总电费
            dataAllCharge.setText(bean.getDataList().get(0).getTotalSum() + "");
            //基本电费
            dataBaseCharge.setText(bean.getDataList().get(0).getBaseSum() + "");
            //年度电费
            dataYearCharge.setText(bean.getDataList().get(0).getFeeSum() + "");
            //力调电费
            dataForcesCharge.setText(bean.getDataList().get(0).getFouceSum() + "");
        }


    }

    @Override
    public void setDataChartData(Databean bean) {
        if(bean.getDataList().size()>0){
            dataChart.clearData();
            ArrayList<Entry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            for (int i = 0; i < bean.getDataList().size(); i++) {
                Entry entry = new Entry(i, 0f);
                entry.setY((float) bean.getDataList().get(i).getCost());
                values.add(entry);
                if(bean.getDataList().get(i).getBaseDate()!=null||bean.getDataList().get(i).getBaseDate().equals("")){
                    String[] split = StringUtil.split(bean.getDataList().get(i).getBaseDate(), "-");
                    listDate.add(split[1]);
                }

            }
            getChartXCount(dataChart);
            dataChart.setDataSet(values, "");
            dataChart.setDayXAxis(listDate);
            dataChart.loadChart();
        }

    }

    @Override
    public void setAllElectricity(final AllElectricitybean allElectricitybean) {
        if(allElectricitybean.getMeterList().size()>0){
            ACache.getInstance().put(Constants.CACHE_OPS_OBJID,allElectricitybean.getLedgerId());
            ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE,1);
            ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE, DateUtil.getCurrentYear()+"-"+DateUtil.getCurrentMonth());
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
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJID,ledgerId);
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE,1);

                    }
                    if (poaiton > 0) {
                        long meterId = allElectricitybean.getMeterList().get(poaiton - 1).getMeterId();
                        dataElectricityTv.setText(allElectricitybean.getMeterList().get(poaiton - 1).getMeterName());
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJID,meterId);
                        ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE,2);

                    }
                    mPresenter.getData();
                    mPresenter.getchartData();
                }
            });
        }

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
