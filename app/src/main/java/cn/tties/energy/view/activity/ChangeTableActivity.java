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

import cn.tties.energy.R;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.MyNoDoubleClickListener;
import cn.tties.energy.model.result.OpsLoginbean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.view.adapter.MyElectricityAdapter;
import cn.tties.energy.view.dialog.CriProgressDialog;

/**
 * 切换电表
 */
public class ChangeTableActivity extends AppCompatActivity {
    private static final String TAG = "ChangeTableActivity";
    ImageView toolbarLeft;
    TextView toolbarText;
    ListView lv;
    LinearLayout toolbarLl;
    LinearLayout electricalTableConfirm;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    int EleAccountId = 0;
    long energyLedgerId = 0;
    int companyId = 0;
    int staffid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_table);
        initFindView();
        initView();
    }

    private void initFindView() {
        toolbarLeft= findViewById(R.id.toolbar_left);
        toolbarText= findViewById(R.id.toolbar_text);
        lv= findViewById(R.id.identity_change_lv);
        electricalTableConfirm= findViewById(R.id.electrical_table_confirm);
        toolbarLl= findViewById(R.id.toolbar_ll);
    }

    private void initView() {

        sp = getSharedPreferences("check", MODE_PRIVATE);
        editor = sp.edit();
        toolbarText.setText("切换电表");
        toolbarLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        final OpsLoginbean bean = (OpsLoginbean) intent.getSerializableExtra("bean");
        final MyElectricityAdapter adapter = new MyElectricityAdapter(bean);
        lv.setAdapter(adapter);
        //确认使用此电表
        electricalTableConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyNoDoubleClickListener.isFastClick()) {
                    List<OpsLoginbean.ResultBean.EnergyLedgerListBean> energyLedgerList = bean.getResult().getEnergyLedgerList();
                    String ischeck = ACache.getInstance().getAsString(Constants.CACHE_ISCHECK);
                    int postion = Integer.parseInt(ischeck);
                    for (int i = 0; i < bean.getResult().getEnergyLedgerList().size(); i++) {
                        if (i == postion) {
                            staffid = bean.getResult().getMaintUser().getStaffId();
                            ACache.getInstance().put(Constants.CACHE_OPS_STAFFID, staffid);
                            energyLedgerId = energyLedgerList.get(postion).getEnergyLedgerId();
                            EleAccountId = energyLedgerList.get(postion).getEleAccountId();
                            companyId = energyLedgerList.get(postion).getCompanyId();
                            ACache.getInstance().put(Constants.CACHE_OPS_COMPANDID, companyId + "");
                            ACache.getInstance().put(Constants.CACHE_OPS_ENERGYLEDGERID, energyLedgerId);
                            ACache.getInstance().put(Constants.CACHE_OPS_ELEACCOUNTID, EleAccountId);
                            finish();
                        }
                    }
                }
            }

        });
    }
}
