package cn.tties.energy.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.tties.energy.R;
import cn.tties.energy.base.BaseFragment;
import cn.tties.energy.chart.BarDataChart;
import cn.tties.energy.chart.BarDataCharttwo;
import cn.tties.energy.chart.LineDataChart;
import cn.tties.energy.model.result.DataFragmentbean;
import cn.tties.energy.model.result.Databean;
import cn.tties.energy.presenter.DataFragmentPresenter;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.view.activity.DataActivity;
import cn.tties.energy.view.activity.Data_CurrentActivity;
import cn.tties.energy.view.activity.Data_ElectricActivity;
import cn.tties.energy.view.activity.Data_FactorActivity;
import cn.tties.energy.view.activity.Data_NoActivity;
import cn.tties.energy.view.activity.Data_PressActivity;
import cn.tties.energy.view.activity.Data_RateActivity;
import cn.tties.energy.view.iview.IDataFragmentView;

import static cn.tties.energy.R.drawable.month_load_bg;

/**
 * Created by li on 2018/3/21
 * description：
 * author：guojlli
 */

public class DataFragment extends BaseFragment<DataFragmentPresenter> implements View.OnClickListener, IDataFragmentView {
    private static final String TAG = "DataFragment";
    @BindView(R.id.data_toolbar_text)
    TextView toolbarText;
    @BindView(R.id.data_toolbar)
    Toolbar dataToolbar;
    @BindView(R.id.data_charge)
    LinearLayout dataCharge;
    @BindView(R.id.data_amount)
    LinearLayout dataAmount;
    @BindView(R.id.data_rate)
    LinearLayout dataRate;
    @BindView(R.id.data_factor)
    LinearLayout dataFactor;
    @BindView(R.id.data_flow)
    LinearLayout dataFlow;
    @BindView(R.id.data_no)
    LinearLayout dataNo;
    @BindView(R.id.data_press)
    LinearLayout dataPress;
    Unbinder unbinder;
    @BindView(R.id.datafragment_time_tv)
    TextView datafragmentTimeTv;
//    @BindView(R.id.datafragment_chart)
//    LineDataChart datafragmentChart;
//    @BindView(R.id.datafragment_price)
    TextView datafragmentPrice;
    BarDataChart datafragmentChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View inflate = inflater.inflate(R.layout.fragment_data, null);
        datafragmentChart = inflate.findViewById(R.id.datafragment_chart);
        datafragmentPrice = inflate.findViewById(R.id.datafragment_price);
        unbinder = ButterKnife.bind(this, inflate);
        initView();
        dataCharge.setOnClickListener(this);
        dataAmount.setOnClickListener(this);
        dataRate.setOnClickListener(this);
        dataFactor.setOnClickListener(this);
        dataFlow.setOnClickListener(this);
        dataNo.setOnClickListener(this);
        dataPress.setOnClickListener(this);
        return inflate;
    }

    private void initView() {
        datafragmentTimeTv.setText((DateUtil.getCurrentMonth()-1)+"月");
        toolbarText.setText("电力数据");
        mPresenter.getDataFragment();
    }

    @Override
    protected void createPresenter() {
        mPresenter = new DataFragmentPresenter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            //电费数据
            case R.id.data_charge:
                intent = new Intent(getActivity(), DataActivity.class);
                startActivity(intent);
                break;
            //电量数据
            case R.id.data_amount:
                intent = new Intent(getActivity(), Data_ElectricActivity.class);
                startActivity(intent);
                break;
            //功率数据
            case R.id.data_rate:
                intent = new Intent(getActivity(), Data_RateActivity.class);
                startActivity(intent);
                break;
            //功率因素
            case R.id.data_factor:
                intent = new Intent(getActivity(), Data_FactorActivity.class);
                startActivity(intent);
                break;
            //电流电压
            case R.id.data_flow:
                intent = new Intent(getActivity(), Data_CurrentActivity.class);
                startActivity(intent);
                break;
            //电流不平衡
            case R.id.data_no:
                intent = new Intent(getActivity(), Data_NoActivity.class);
                startActivity(intent);
                break;
            //电压不平衡
            case R.id.data_press:
                intent = new Intent(getActivity(), Data_PressActivity.class);
                startActivity(intent);
                break;


        }
    }

    @Override
    public void setDataFragmentData(DataFragmentbean bean) {
        if(bean.getDataList().size()>0){
            Double max = getMax(bean);
            datafragmentChart.clearData();
            ArrayList<BarEntry> values = new ArrayList<>();
//            for (int i =bean.getDataList().size()-1 ; i >=0; i--) {
            for (int i =1 ; i <=bean.getDataList().size(); i++) {
                double j=bean.getDataList().get(bean.getDataList().size()-i).getCost();
                if(bean.getDataList().get(bean.getDataList().size()-i).getCost()==-1){
                    j=max+1;
                }
                    BarEntry entry = new BarEntry(i, 0f);
                    Log.i(TAG, "setDataFragmentData: "+j);
                    entry.setY((float) j);
                    values.add(entry);
            }

            BarDataSet barDataSet = datafragmentChart.setDataSet(values, "");
            float yMax = barDataSet.getYMax();
            int entryCount = barDataSet.getEntryCount();

            int color = Color.parseColor("#FFD6C9");
            int color2 = Color.parseColor("#ffffff");
            double v = max + 1;
            for (int i = 0; i < barDataSet.getEntryCount(); i++) {

                if((barDataSet.getEntryForIndex(i).getY()+"").equals(v+"")){
                    Log.i(TAG, "setDataFragmentData11111111: "+barDataSet.getEntryForIndex(i).getY());
//                    barDataSet.setColor();
                }
                Log.i(TAG, "setDataFragmentData777777777: "+barDataSet.getEntryForIndex(i).getY());
            }

            datafragmentChart.loadChart();
                datafragmentPrice.setText(bean.getDataList().get(0).getCost()+"");
            }

    }
    //取最大值
    public Double getMax(DataFragmentbean bean){
        List<Databean.DataListBean> dataList = bean.getDataList();
        List<Double> listMax=new ArrayList<>();
        for (int i = 0; i < bean.getDataList().size(); i++) {
            listMax.add(bean.getDataList().get(i).getCost());
        }
        return Collections.max(listMax);
    }
}
