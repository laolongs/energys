package cn.tties.energy.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.tties.energy.R;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.model.result.Energy_Monthlybean;
import cn.tties.energy.presenter.Energy_MonthlyPresenter;
import cn.tties.energy.view.adapter.MyMonthlyAdapter;
import cn.tties.energy.view.iview.IEnergy_MonthlyView;

/**
 * 能效月报
 */
public class Energy_EnergyActivity extends BaseActivity<Energy_MonthlyPresenter> implements IEnergy_MonthlyView {
    private static final String TAG = "Energy_EnergyActivity";
    LinearLayout toolbarLl;
    ImageView toolbarLeft;
    TextView toolbarText;
    Toolbar dataToolbar;
    RecyclerView energyEnergyRecy;
    LinearLayout enereyEnergyLl;
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
        dataToolbar= findViewById(R.id.data_toolbar);
        energyEnergyRecy= findViewById(R.id.energy_energy_Recy);
        enereyEnergyLl= findViewById(R.id.enerey_energy_ll);
    }

    private void initView() {
        mPresenter.getEnergy_Monthly(3);//2能效doc格式  3能效pdf格式
        toolbarText.setText("能效月报");
        toolbarLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        energyEnergyRecy.setLayoutManager(new LinearLayoutManager(Energy_EnergyActivity.this));
    }

    @Override
    protected void createPresenter() {
        mPresenter=new Energy_MonthlyPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_energy__energy;
    }

    @Override
    public void setEnergy_MonthlyData(Energy_Monthlybean bean) {
        if(bean.getResult().size()==0){
            enereyEnergyLl.setVisibility(View.VISIBLE);
        }else{
            enereyEnergyLl.setVisibility(View.GONE);
        }

        MyMonthlyAdapter adapter = new MyMonthlyAdapter(Energy_EnergyActivity.this,bean);
        energyEnergyRecy.setAdapter(adapter);
    }
}
