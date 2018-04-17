package cn.tties.energy.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
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
import cn.tties.energy.chart.LineDataThreeChart;
import cn.tties.energy.common.Constants;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.Data_CurrentPressbean;
import cn.tties.energy.model.result.Data_Currentbean;
import cn.tties.energy.presenter.Data_CurrentPresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.utils.ToastUtil;
import cn.tties.energy.view.dialog.BottomStyleDialog;
import cn.tties.energy.view.dialog.BottomStyleDialogTwo;
import cn.tties.energy.view.dialog.MyTimePickerDialog;
import cn.tties.energy.view.iview.IData_CurrentView;

/**
 * 电流电压
 */
public class Data_CurrentActivity extends BaseActivity<Data_CurrentPresenter> implements IData_CurrentView {

    @BindView(R.id.toolbar_left)
    ImageView toolbarLeft;
    @BindView(R.id.toolbar_text)
    TextView toolbarText;
    @BindView(R.id.data_current_chart)
    LineDataThreeChart dataCurrentChart;
    @BindView(R.id.data_currentpress_chart)
    LineDataThreeChart dataCurrentpressChart;
    @BindView(R.id.data_current_time_tv)
    TextView dataCurrentTimeTv;
    @BindView(R.id.data_current_time)
    LinearLayout dataCurrentTime;
    @BindView(R.id.data_current_allelectric)
    LinearLayout dataCurrentAllelectric;
    @BindView(R.id.data_current_ele_tv)
    TextView dataCurrentEleTv;
    private BottomStyleDialogTwo dialog;
    MyTimePickerDialog dialogtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        mPresenter.getAllElectricityData();
    }

    private void initView() {
        dataCurrentTimeTv.setText(DateUtil.getCurrentYear()+"年"+DateUtil.getCurrentMonth()+"月");
        dialogtime = new MyTimePickerDialog();
        toolbarText.setText("电流电压");
        toolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dataCurrentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogtime.getTimePickerDialog(Data_CurrentActivity.this);
                dialogtime.setOnTimeClick(new MyTimePickerDialog.OnTimeClick() {
                    @Override
                    public void OnTimeClickListener(String text) {
                        ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE, text);
                        dataCurrentTimeTv.setText(text);
                        mPresenter.getData_CurrentData();
                        mPresenter.getData_CurrentPressKwData();
                    }
                });
            }
        });
        dataCurrentAllelectric.setOnClickListener(new View.OnClickListener() {

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
        mPresenter = new Data_CurrentPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data__current;
    }

    @Override
    public void setData_CurrentData(Data_Currentbean bean) {
        if(bean.getDataList().size()>0){
            dataCurrentChart.clearData();
            ArrayList<Entry> values1 = new ArrayList<>();
            ArrayList<Entry> values2 = new ArrayList<>();
            ArrayList<Entry> values3 = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            for (int i = 0; i < bean.getDataList().size(); i++) {
                Entry entry1 = new Entry(i, 0f);
                Entry entry2 = new Entry(i, 0f);
                Entry entry3 = new Entry(i, 0f);
                entry1.setY((float) bean.getDataList().get(i).getA());
                entry2.setY((float) bean.getDataList().get(i).getB());
                entry3.setY((float) bean.getDataList().get(i).getC());
                values1.add(entry1);
                values2.add(entry2);
                values3.add(entry3);
                if(bean.getDataList().get(i).getFreezeTime()!=null||bean.getDataList().get(i).getFreezeTime().equals("")){
                    String split = StringUtil.substring(bean.getDataList().get(i).getFreezeTime(),5,10);
                    listDate.add(split);
                }

            }
            XAxis xAxis = dataCurrentChart.getXAxis();
            xAxis.setLabelCount(bean.getDataList().size(),true);
            xAxis.setLabelRotationAngle(-50);
            dataCurrentChart.setDataSet1(values1, "A相电流");
            dataCurrentChart.setDataSet2(values2, "B相电流");
            dataCurrentChart.setDataSet3(values3, "C相电流");
            dataCurrentChart.setDayXAxis(listDate);
            dataCurrentChart.loadChart();
        }

    }

    @Override
    public void setData_CurrentPressData(Data_CurrentPressbean bean) {
        if(bean.getDataList().size()>0){
            dataCurrentpressChart.clearData();
            ArrayList<Entry> values1 = new ArrayList<>();
            ArrayList<Entry> values2 = new ArrayList<>();
            ArrayList<Entry> values3 = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            for (int i = 0; i < bean.getDataList().size(); i++) {
                Entry entry1 = new Entry(i, 0f);
                Entry entry2 = new Entry(i, 0f);
                Entry entry3 = new Entry(i, 0f);
                entry1.setY((float) bean.getDataList().get(i).getA());
                entry2.setY((float) bean.getDataList().get(i).getB());
                entry3.setY((float) bean.getDataList().get(i).getC());
                values1.add(entry1);
                values2.add(entry2);
                values3.add(entry3);
                if(bean.getDataList().get(i).getFreezeTime()!=null||bean.getDataList().get(i).getFreezeTime().equals("")){
                    String split = StringUtil.substring(bean.getDataList().get(i).getFreezeTime(),5,10);
                    listDate.add(split);
                }

            }
            XAxis xAxis = dataCurrentpressChart.getXAxis();
            xAxis.setLabelCount(bean.getDataList().size(),true);
            xAxis.setLabelRotationAngle(-50);
            dataCurrentpressChart.setDataSet1(values1, "A相电流");
            dataCurrentpressChart.setDataSet2(values2, "B相电流");
            dataCurrentpressChart.setDataSet3(values3, "C相电流");
            dataCurrentpressChart.setDayXAxis(listDate);
            dataCurrentpressChart.loadChart();
        }

    }

    @Override
    public void setAllElectricity(final AllElectricitybean allElectricitybean) {
        if(allElectricitybean.getMeterList().size()>0){
            ACache.getInstance().put(Constants.CACHE_OPS_OBJID, allElectricitybean.getLedgerId());
            ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE, 1);
            ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE, DateUtil.getCurrentYear()+"-"+DateUtil.getCurrentMonth());
            mPresenter.getData_CurrentData();
            mPresenter.getData_CurrentPressKwData();
            dataCurrentEleTv.setText(allElectricitybean.getMeterList().get(0).getMeterName());
            dialog = new BottomStyleDialogTwo(Data_CurrentActivity.this, allElectricitybean);
            dialog.setCliekAllElectricity(new BottomStyleDialogTwo.OnCliekAllElectricitytwo() {
                @Override
                public void OnCliekAllElectricityListener(int poaiton) {
//                if (poaiton == 0) {
//                    long ledgerId = allElectricitybean.getLedgerId();
//                    dataCurrentEleTv.setText(allElectricitybean.getLedgerName());
//                    ACache.getInstance().put(Constants.CACHE_OPS_OBJID, ledgerId);
//                    ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE,1);
//
//                }
//                if (poaiton > 0) {
                    long meterId = allElectricitybean.getMeterList().get(poaiton).getMeterId();
                    dataCurrentEleTv.setText(allElectricitybean.getMeterList().get(poaiton).getMeterName());
                    ACache.getInstance().put(Constants.CACHE_OPS_OBJID, meterId);
                    ACache.getInstance().put(Constants.CACHE_OPS_OBJTYPE, 2);

//                }
                    mPresenter.getData_CurrentData();
                    mPresenter.getData_CurrentPressKwData();
                }
            });
        }

    }
}
