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

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import cn.tties.energy.R;
import cn.tties.energy.base.BaseFragment;
import cn.tties.energy.chart.BarDataChart;
import cn.tties.energy.chart.BarDataCharttwo;
import cn.tties.energy.chart.LineDataChart;
import cn.tties.energy.common.MyNoDoubleClickListener;
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
import cn.tties.energy.view.dialog.CriProgressDialog;
import cn.tties.energy.view.iview.IDataFragmentView;

import static cn.tties.energy.R.drawable.month_load_bg;

/**
 * Created by li on 2018/3/21
 * description：
 * author：guojlli
 */

public class DataFragment extends BaseFragment<DataFragmentPresenter> implements View.OnClickListener, IDataFragmentView {
    private static final String TAG = "DataFragment";
    TextView toolbarText;
    Toolbar dataToolbar;
    LinearLayout dataCharge;
    LinearLayout dataAmount;
    LinearLayout dataRate;
    LinearLayout dataFactor;
    LinearLayout dataFlow;
    LinearLayout dataNo;
    LinearLayout dataPress;
    TextView datafragmentTimeTv;
    TextView datafragmentPrice;
    BarDataChart datafragmentChart;
    private List<Integer> listColor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View inflate = inflater.inflate(R.layout.fragment_data, null);
        initFindView(inflate);
        initView();
        return inflate;
    }

    private void initFindView(View inflate) {
        toolbarText = inflate.findViewById(R.id.data_toolbar_text);
        dataToolbar = inflate.findViewById(R.id.data_toolbar);
        dataCharge = inflate.findViewById(R.id.data_charge);
        dataAmount = inflate.findViewById(R.id.data_amount);
        dataRate = inflate.findViewById(R.id.data_rate);
        dataFactor = inflate.findViewById(R.id.data_factor);
        dataFlow = inflate.findViewById(R.id.data_flow);
        dataNo = inflate.findViewById(R.id.data_no);
        dataPress = inflate.findViewById(R.id.data_press);
        datafragmentTimeTv = inflate.findViewById(R.id.datafragment_time_tv);
        datafragmentChart = inflate.findViewById(R.id.datafragment_chart);
        datafragmentPrice = inflate.findViewById(R.id.datafragment_price);
        dataCharge.setOnClickListener(this);
        dataAmount.setOnClickListener(this);
        dataRate.setOnClickListener(this);
        dataFactor.setOnClickListener(this);
        dataFlow.setOnClickListener(this);
        dataNo.setOnClickListener(this);
        dataPress.setOnClickListener(this);
    }

    private void initView() {
        datafragmentTimeTv.setText((DateUtil.getCurrentMonth()-1)+"月");
        toolbarText.setText("电力数据");
        mPresenter.getDataFragment();
    }

    @Override
    protected void createPresenter() {
        mPresenter = new DataFragmentPresenter(this,getActivity());
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            //电费数据
            case R.id.data_charge:
              if(MyNoDoubleClickListener.isFastClick()){
                  intent = new Intent(getActivity(), DataActivity.class);
                  startActivity(intent);
              }

                break;
            //电量数据
            case R.id.data_amount:
                if(MyNoDoubleClickListener.isFastClick()){
                    intent = new Intent(getActivity(), Data_ElectricActivity.class);
                    startActivity(intent);
                }

                break;
            //功率数据
            case R.id.data_rate:
                if(MyNoDoubleClickListener.isFastClick()){
                    intent = new Intent(getActivity(), Data_RateActivity.class);
                    startActivity(intent);
                }

                break;
            //功率因素
            case R.id.data_factor:
                if(MyNoDoubleClickListener.isFastClick()){
                    intent = new Intent(getActivity(), Data_FactorActivity.class);
                    startActivity(intent);
                }

                break;
            //电流电压
            case R.id.data_flow:
                if(MyNoDoubleClickListener.isFastClick()){
                    intent = new Intent(getActivity(), Data_CurrentActivity.class);
                    startActivity(intent);
                }

                break;
            //电流不平衡
            case R.id.data_no:
                if(MyNoDoubleClickListener.isFastClick()){
                    intent = new Intent(getActivity(), Data_NoActivity.class);
                    startActivity(intent);
                }

                break;
            //电压不平衡
            case R.id.data_press:
                if(MyNoDoubleClickListener.isFastClick()){
                    intent = new Intent(getActivity(), Data_PressActivity.class);
                    startActivity(intent);
                }

                break;


        }
    }

    @Override
    public void setDataFragmentData(DataFragmentbean bean) {
        int color = Color.parseColor("#FF8D6A");
        int color2 = Color.parseColor("#FF7247");
        int color3 = Color.parseColor("#FFD5C8");
        if(bean.getDataList().size()>0){
            float max = getMax(bean);
            datafragmentChart.clearData();
            ArrayList<BarEntry> values = new ArrayList<>();
            listColor = new ArrayList<>();
            for (int i =1 ; i <=bean.getDataList().size(); i++) {
                double j=bean.getDataList().get(bean.getDataList().size()-i).getCost();
                if(i==bean.getDataList().size()){
                    listColor.add(color3);
                }else if(bean.getDataList().get(bean.getDataList().size()-i).getCost()==-1){
                    j=max+1;
                    listColor.add(color2);
                }else{
                    listColor.add(color);
                }
                BarEntry entry = new BarEntry(i, 0f);
                Log.i(TAG, "setDataFragmentData: "+j);
                entry.setY((float) j);
                values.add(entry);
            }
            BarDataSet barDataSet = datafragmentChart.setDataSet(values, "");
            barDataSet.setColors(listColor);
            YAxis axisLeft = datafragmentChart.getAxisLeft();
            axisLeft.setMaxWidth(max+1);
            axisLeft.setMinWidth(0f);
            datafragmentChart.loadChart();
            datafragmentPrice.setText(bean.getDataList().get(0).getCost()+"");
        }

    }
    //取最大值
    public float getMax(DataFragmentbean bean){
        List<Float> listMax=new ArrayList<>();
        for (int i = 0; i < bean.getDataList().size(); i++) {
            listMax.add((float)bean.getDataList().get(i).getCost());
        }
        return Collections.max(listMax);
    }
}
