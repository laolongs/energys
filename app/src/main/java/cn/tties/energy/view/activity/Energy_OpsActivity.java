package cn.tties.energy.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.tties.energy.R;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.model.result.Energy_Monthlybean;
import cn.tties.energy.presenter.Energy_MonthlyPresenter;
import cn.tties.energy.utils.ShareUtils;
import cn.tties.energy.utils.ToastUtil;
import cn.tties.energy.view.adapter.MyMonthlyAdapter;
import cn.tties.energy.view.adapter.MyOpsMonthlyAdapter;
import cn.tties.energy.view.iview.IEnergy_MonthlyView;
import cn.tties.energy.wxapi.Defaultcontent;

/**
 * 运维月报
 * 现在获取报告接口 参数变成 1运维报告 2能效word报告 3能效pdf报告
 */
public class Energy_OpsActivity extends BaseActivity<Energy_MonthlyPresenter> implements IEnergy_MonthlyView {
    private static final String TAG = "Energy_OpsActivity";
    @BindView(R.id.toolbar_ll)
    LinearLayout toolbarLl;
    @BindView(R.id.toolbar_left)
    ImageView toolbarLeft;
    @BindView(R.id.toolbar_text)
    TextView toolbarText;
    @BindView(R.id.enerey_ops_rec)
    RecyclerView enereyOpsRec;
    @BindView(R.id.enerey_ops_ll)
    LinearLayout enereyOpsLl;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mPresenter.getEnergy_Monthly(1);
        toolbarText.setText("运维月报");
        toolbarLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        enereyOpsRec.setLayoutManager(new LinearLayoutManager(Energy_OpsActivity.this));
    }

    @Override
    protected void createPresenter() {
        mPresenter = new Energy_MonthlyPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_energy__ops;
    }

    @Override
    public void setEnergy_MonthlyData(Energy_Monthlybean bean) {
        if(bean.getResult().size()==0){
            enereyOpsLl.setVisibility(View.VISIBLE);
        }else{
            enereyOpsLl.setVisibility(View.GONE);
        }
        MyOpsMonthlyAdapter adapter = new MyOpsMonthlyAdapter(this, bean);
        enereyOpsRec.setAdapter(adapter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
