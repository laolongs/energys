package cn.tties.energy.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tties.energy.R;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.chart.LineDataChart;
import cn.tties.energy.chart.LineDataFourChart;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.MyAllTimeYear;
import cn.tties.energy.common.MyHint;
import cn.tties.energy.common.MyProgressRound;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Databean;
import cn.tties.energy.presenter.Energy_ElectricalPersenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.utils.DoubleUtils;
import cn.tties.energy.utils.StringUtil;

import cn.tties.energy.view.dialog.MyTimePickerWheelDialog;
import cn.tties.energy.view.iview.IDataView;

/**
 * 电度电量优化
 */
public class Energy_ElectricalActivity extends BaseActivity<Energy_ElectricalPersenter> implements IDataView {
    private static final String TAG = "Energy_ElectricalActivi";
    @BindView(R.id.electrical_myview)
    MyProgressRound electricalMyview;
    @BindView(R.id.toolbar_left)
    ImageView toolbarLeft;
    @BindView(R.id.toolbar_text)
    TextView toolbarText;
    @BindView(R.id.enerey_electrical_month)
    TextView enereyElectricalMonth;
    @BindView(R.id.enerey_electrical_cusp)
    TextView enereyElectricalCusp;
    @BindView(R.id.enerey_electrical_hight)
    TextView enereyElectricalHight;
    @BindView(R.id.enerey_electrical_low)
    TextView enereyElectricalLow;
    @BindView(R.id.enerey_electrical_kva)
    TextView enereyElectricalKva;
    @BindView(R.id.enerey_electrical_price)
    TextView enereyElectricalPrice;
    @BindView(R.id.enerey_electrical_select)
    ImageView enereyElectricalSelect;
    @BindView(R.id.enerey_electrical_chart)
    LineDataFourChart enereyElectricalChart;
    @BindView(R.id.enerey_electrical_year)
    TextView enereyElectricalYear;
    MyTimePickerWheelDialog dialogtime;
    DataAllbean dataAllbean=new DataAllbean();
    private float cusp2;
    private float hight2;
    private float low2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        dialogtime=new MyTimePickerWheelDialog(Energy_ElectricalActivity.this);
        mPresenter.getEnergy_Electrical();
        toolbarText.setText("电度电费优化");
        toolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        enereyElectricalYear.setText(DateUtil.getCurrentYear() + "年");
        enereyElectricalYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogtime.show();
                dialogtime.setOnCliekTime(new MyTimePickerWheelDialog.OnCliekTime() {
                    @Override
                    public void OnCliekTimeListener(int poaiton) {
                        int tiemBase = MyAllTimeYear.getTiemBase(poaiton);
                        enereyElectricalYear.setText(tiemBase+"年");
                        mPresenter.getEnergy_ElectricalChart();
                    }


                });
            }
        });
    }

    @Override
    protected void createPresenter() {
        mPresenter = new Energy_ElectricalPersenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_energy__electrical;
    }

    @Override
    public void setDataData(Databean bean) {
        if (bean.getDataList().size() > 0) {
            ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE, DateUtil.getCurrentYear() + "-" + DateUtil.getCurrentMonth());
            mPresenter.getEnergy_ElectricalChart();
            int percentage = 0;
            double num = 0;
            int low = bean.getDataList().get(0).getSectorGuValue();
            int cusp = bean.getDataList().get(0).getSectorJianValue();
            int hight = bean.getDataList().get(0).getSectorFengValue();
            num = low + hight + cusp;
            enereyElectricalMonth.setText(bean.getDataList().get(0).getBaseDate() + "");
            electricalMyview.setAllPregerssData(cusp,hight,low,num);
//            //尖峰
//            electricalMyview.setProgressMax(cusp, num);
//            //高峰
//            electricalMyview.setProgressCenter(hight, num);
//            //低谷
//            electricalMyview.setProgressMin(low, num);

            enereyElectricalCusp.setText(DoubleUtils.getNum(bean.getDataList().get(0).getSectorJianValue()) + "度");
            enereyElectricalHight.setText(DoubleUtils.getNum(bean.getDataList().get(0).getSectorFengValue()) + "度");
            enereyElectricalLow.setText(DoubleUtils.getNum(bean.getDataList().get(0).getSectorGuValue()) + "度");
            enereyElectricalKva.setText( DoubleUtils.getNum(bean.getDataList().get(0).getTotalEnergy()) + "kVA");
            enereyElectricalPrice.setText(DoubleUtils.getNum(bean.getDataList().get(0).getTotalSum()) + "元");
        }else{
            MyHint.myHintDialog(this);
        }

    }

    @Override
    public void setDataChartData(Databean bean) {
        int allnum=0;
        double number = 0;
        if(bean.getDataList().size()>0){
            enereyElectricalChart.clearData();
            ArrayList<Entry> values1 = new ArrayList<>();
            ArrayList<Entry> values2 = new ArrayList<>();
            ArrayList<Entry> values3 = new ArrayList<>();
            List<String> listDate = new ArrayList<String>();
            //判断数据是否全年，否则动态添加数据
            if(bean.getDataList().size()!=12){
                int num = 12 - bean.getDataList().size();
                allnum = bean.getDataList().size() + num;
                for (int i = 0; i < allnum; i++) {
                    Entry entry1 = new Entry(i, 0f);
                    Entry entry2 = new Entry(i, 0f);
                    Entry entry3 = new Entry(i, 0f);
                    if(i>=bean.getDataList().size()){
                        int monthNum=i+1;
                        int positionNum = DoubleUtils.getPositionNum(monthNum);
                        if(positionNum==1){
                            listDate.add("0"+monthNum);
                        }else{
                            listDate.add(monthNum+"");
                        }
                        entry1.setY(0);
                        entry2.setY(0);
                        entry3.setY(0);
                    }else{
                        int low = bean.getDataList().get(i).getSectorGuValue();
                        int cusp = bean.getDataList().get(i).getSectorJianValue();
                        int hight = bean.getDataList().get(i).getSectorFengValue();
                        number = low + hight + cusp;
                        if(number==0){
                            cusp2 = (float) 0;
                            hight2 = (float) 0;
                            low2 = (float) 0;
                        }else{
                            cusp2 = (float) (bean.getDataList().get(i).getSectorJianValue()/number*100);
                            hight2 = (float) (bean.getDataList().get(i).getSectorFengValue()/number*100)+ cusp2;
                            low2 = (float) (bean.getDataList().get(i).getSectorGuValue()/number*100)+ cusp2 + hight2;
                        }
                        entry1.setY(cusp2);
                        entry2.setY(hight2);
                        entry3.setY(low2);
                        String[] split = StringUtil.split(bean.getDataList().get(i).getBaseDate(), "-");
                        listDate.add(split[1]);
                    }
                    values1.add(entry1);
                    values2.add(entry2);
                    values3.add(entry3);
                }
            }else{
                for (int i = 0; i < bean.getDataList().size(); i++) {
                    double num = 0;
                    int low = bean.getDataList().get(i).getSectorGuValue();
                    int cusp = bean.getDataList().get(i).getSectorJianValue();
                    int hight = bean.getDataList().get(i).getSectorFengValue();
                    num = low + hight + cusp;
                    Entry entry1 = new Entry(i, 0f);
                    Entry entry2 = new Entry(i, 0f);
                    Entry entry3 = new Entry(i, 0f);
                    if(num==0){
                        cusp2 = (float) 0;
                        hight2 = (float) 0;
                        low2 = (float) 0;
                    }else{
                        cusp2 = (float) (bean.getDataList().get(i).getSectorJianValue()/num*100);
                        hight2 = (float) (bean.getDataList().get(i).getSectorFengValue()/num*100)+ cusp2;
                        low2 = (float) (bean.getDataList().get(i).getSectorGuValue()/num*100)+ cusp2 + hight2;
                    }
                    entry1.setY(cusp2);
                    entry2.setY(hight2);
                    entry3.setY(low2);
                    values1.add(entry1);
                    values2.add(entry2);
                    values3.add(entry3);
                    String[] split = StringUtil.split(bean.getDataList().get(i).getBaseDate(), "-");
                    listDate.add(split[1]);
                }
            }
            enereyElectricalChart.setDataSet3(values1, "尖峰");
            enereyElectricalChart.setDataSet2(values2, "高峰");
            enereyElectricalChart.setDataSet1(values3, "低谷");
            enereyElectricalChart.setDayXAxis(listDate);
            enereyElectricalChart.loadChart();
        }else{
            MyHint.myHintDialog(this);
        }

    }

    @Override
    public void setAllElectricity(AllElectricitybean allElectricitybean) {

    }

}
