package cn.tties.energy.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tties.energy.R;
import cn.tties.energy.common.Constants;
import cn.tties.energy.model.result.OpsLoginbean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.view.adapter.MyElectricityAdapter;

/**
 * 切换电表
 */
public class ChangeTableActivity extends AppCompatActivity {
    private static final String TAG = "ChangeTableActivity";
    @BindView(R.id.toolbar_left)
    ImageView toolbarLeft;
    @BindView(R.id.toolbar_text)
    TextView toolbarText;
    @BindView(R.id.identity_change_lv)
    ListView lv;
    @BindView(R.id.electrical_table_confirm)
    LinearLayout electricalTableConfirm;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    int EleAccountId=0;
    long energyLedgerId=0;
    int companyId=0;
    int staffid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_table);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        sp=getSharedPreferences("check",MODE_PRIVATE);
        editor=sp.edit();
        toolbarText.setText("切换电表");
        toolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        final OpsLoginbean bean = (OpsLoginbean) intent.getSerializableExtra("bean");
        final MyElectricityAdapter adapter = new MyElectricityAdapter(ChangeTableActivity.this, bean);
        lv.setAdapter(adapter);
        //确认使用此电表
        electricalTableConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<OpsLoginbean.ResultBean.EnergyLedgerListBean> energyLedgerList = bean.getResult().getEnergyLedgerList();
                String ischeck = ACache.getInstance().getAsString(Constants.CACHE_ISCHECK);
                int postion = Integer.parseInt(ischeck);
                for (int i = 0; i < bean.getResult().getEnergyLedgerList().size(); i++) {
                    if(i==postion){
                        staffid=bean.getResult().getMaintUser().getStaffId();
                        ACache.getInstance().put(Constants.CACHE_OPS_STAFFID,staffid);
                        energyLedgerId=energyLedgerList.get(postion).getEnergyLedgerId();
                        EleAccountId=energyLedgerList.get(postion).getEleAccountId();
                        companyId=energyLedgerList.get(postion).getCompanyId();
                        ACache.getInstance().put(Constants.CACHE_OPS_COMPANDID,companyId+"");
                        ACache.getInstance().put(Constants.CACHE_OPS_ENERGYLEDGERID,energyLedgerId);
                        ACache.getInstance().put(Constants.CACHE_OPS_ELEACCOUNTID,EleAccountId);
                        finish();
                    }
                }
            }
        });
    }

}
