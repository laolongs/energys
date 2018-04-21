package cn.tties.energy.view.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tties.energy.R;
import cn.tties.energy.application.MyApplication;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.chart.LineDataChart;
import cn.tties.energy.chart.LineDataTwoChart;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.MyAllTimeYear;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Data_HaveKwbean;
import cn.tties.energy.model.result.Data_NoKvarbean;
import cn.tties.energy.presenter.Data_RatePresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.view.dialog.BottomStyleDialog;
import cn.tties.energy.view.dialog.MyTimePickerWheelTwoDialog;
import cn.tties.energy.view.iview.IData_RateView;

/**
 * 功率数据
 */
public class Data_RateActivity extends BaseActivity<Data_RatePresenter> implements IData_RateView {

    @BindView(R.id.toolbar_left)
    ImageView toolbarLeft;
    @BindView(R.id.toolbar_text)
    TextView toolbarText;
//    @BindView(R.id.havekw_tv)
//    TextView havekwTv;
//    @BindView(R.id.havakw_time)
//    TextView havakwTime;
//    @BindView(R.id.havakw_year)
//    TextView havakwYear;
//    @BindView(R.id.nokvar_tv)
//    TextView nokvarTv;
//    @BindView(R.id.nokvar_time)
//    TextView nokvarTime;
//    @BindView(R.id.nokvar_year)
//    TextView nokvarYear;
    @BindView(R.id.havakw_chart)
    LineDataTwoChart havakwChart;
    @BindView(R.id.nokvar_chart)
    LineDataChart nokvarChart;
    @BindView(R.id.data_rate_tv)
    TextView dataRateTv;
    @BindView(R.id.data_rate_time)
    LinearLayout dataRateTime;
    @BindView(R.id.data_rate_electrical)
    LinearLayout dataRateElectrical;
    @BindView(R.id.data_rate_ele_tv)
    TextView dataRateEleTv;
    private BottomStyleDialog dialog;
    MyTimePickerWheelTwoDialog dialogtime;
    MyAllTimeYear timeYear=new MyAllTimeYear();
    DataAllbean allbean=new DataAllbean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        mPresenter.getAllElectricityData();

    }

    private void initView() {
        dialogtime = new MyTimePickerWheelTwoDialog(Data_RateActivity.this);
        dataRateTv.setText(DateUtil.getCurrentYear()+"年"+DateUtil.getCurrentMonth()+"月");
        toolbarText.setText("功率数据");
        toolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dataRateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogtime.show();
                dialogtime.setOnCliekTime(new MyTimePickerWheelTwoDialog.OnCliekTime() {
                    @Override
                    public void OnCliekTimeListener(int poaiton) {
                        String tiemMonthBase = timeYear.getTiemMonthBase(poaiton);
                        dataRateTv.setText(tiemMonthBase);
                        mPresenter.getData_HaveKwData();
                        mPresenter.getData_NoKvarKwData();
                    }
                });
            }
        });
        dataRateElectrical.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.show();
                }
//                ToastUtil.showShort(Data_PressActivity.this,""+dialog.getOnclickItem());
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
            for (int i = 0; i < data_haveKwbean.getDataList().size(); i++) {
                Entry entry = new Entry(i, 0f);
                entry.setY((float)Float.parseFloat(String.valueOf(data_haveKwbean.getDataList().get(i).getD())) );
                values.add(entry);
                if(data_haveKwbean.getDataList().get(i).getFreezeTime()!=null||data_haveKwbean.getDataList().get(i).getFreezeTime().equals("")){
                    String split = StringUtil.substring(data_haveKwbean.getDataList().get(i).getFreezeTime(),5,10);
                    listDate.add(split);
                }

            }
            XAxis xAxis = havakwChart.getXAxis();
            xAxis.setLabelCount(data_haveKwbean.getDataList().size(),true);
            xAxis.setLabelRotationAngle(-50);
            LineDataSet lineDataSet = havakwChart.setDataSet(values, "");
            havakwChart.setDayXAxis(listDate);
            havakwChart.loadChart();
        }

    }

    @Override
    public void setData_NoKvarData(Data_NoKvarbean data_noKvarbean) {
        if(data_noKvarbean.getDataList().size()>0){
            nokvarChart.clearData();
            ArrayList<Entry> values = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            for (int i = 0; i < data_noKvarbean.getDataList().size(); i++) {
                Entry entry = new Entry(i, 0f);
                entry.setY((float) Float.parseFloat(String.valueOf(data_noKvarbean.getDataList().get(i).getD())));
                values.add(entry);
                if(data_noKvarbean.getDataList().get(i).getFreezeTime()!=null||data_noKvarbean.getDataList().get(i).getFreezeTime().equals("")){
                    String split = StringUtil.substring(data_noKvarbean.getDataList().get(i).getFreezeTime(),5,10);
                    listDate.add(split);
                }

            }
            XAxis xAxis = nokvarChart.getXAxis();
            xAxis.setLabelCount(data_noKvarbean.getDataList().size(),true);
            xAxis.setLabelRotationAngle(-50);
            nokvarChart.setDataSet(values, "");
            nokvarChart.setDayXAxis(listDate);
            nokvarChart.loadChart();
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
