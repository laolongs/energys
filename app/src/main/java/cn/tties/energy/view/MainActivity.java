package cn.tties.energy.view;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import cn.tties.energy.R;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.common.Constants;
import cn.tties.energy.model.result.Versionbean;
import cn.tties.energy.presenter.MainPresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.view.activity.UpdateActivity;
import cn.tties.energy.view.fragment.DataFragment;
import cn.tties.energy.view.fragment.EnergyFragment;
import cn.tties.energy.view.fragment.IdentityFragment;
import cn.tties.energy.view.fragment.OpsFragment;
import cn.tties.energy.view.iview.IMainView;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView, View.OnClickListener {
    private static final String TAG = "MainActivity";
    ImageView ivOps;
    TextView tvOps;
    LinearLayout llOps;
    ImageView ivEnergy;
    TextView tvEnergy;
    LinearLayout llEnergy;
    ImageView ivData;
    TextView tvData;
    LinearLayout llData;
    ImageView ivIden;
    TextView tvIden;
    LinearLayout llIden;
    private ImageView ivCurrent;
    private TextView tvCurrent;
    private List<View> mViews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFindView();
        initView();
        checkVersion();
    }

    private void initFindView() {
        ivOps=findViewById(R.id.ivOps);
        tvOps=findViewById(R.id.tvOps);
        llOps=findViewById(R.id.llOps);
        ivEnergy=findViewById(R.id.ivEnergy);
        tvEnergy=findViewById(R.id.tvEnergy);
        llEnergy=findViewById(R.id.llEnergy);
        ivData=findViewById(R.id.ivData);
        tvData=findViewById(R.id.tvData);
        llData=findViewById(R.id.llData);
        ivIden=findViewById(R.id.ivIden);
        tvIden=findViewById(R.id.tvIden);
        llIden=findViewById(R.id.llIden);
    }

    private void initView() {
        llOps.setOnClickListener(this);
        llData.setOnClickListener(this);
        llEnergy.setOnClickListener(this);
        llIden.setOnClickListener(this);
        ivOps.setSelected(true);
        tvOps.setSelected(true);
        ivCurrent=ivOps;
        tvCurrent=tvOps;
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fl, new OpsFragment()).commit();
    }

    @Override
    protected void createPresenter() {
        mPresenter = new MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setViewPageData(List<View> list) {
    }

    private void checkVersion() {
        try {
            Versionbean ret = ACache.getInstance().getAsObject(Constants.CACHEE_VERSION);
            if (ret == null) {
                return;
            }
            // 获取packagemanager的实例
            PackageManager packageManager = getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            int versionCode = packInfo.versionCode;
            if (ret.getVersionCode() > versionCode) {
                UpdateActivity.show(MainActivity.this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View view) {
        ivCurrent.setSelected(false);
        tvCurrent.setSelected(false);
        switch (view.getId()){
            case R.id.llOps:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fl, new OpsFragment()).commit();
                ivOps.setSelected(true);
                tvOps.setSelected(true);
                ivCurrent=ivOps;
                tvCurrent=tvOps;
                break;
            case R.id.llEnergy:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fl, new EnergyFragment()).commit();
                ivEnergy.setSelected(true);
                tvEnergy.setSelected(true);
                ivCurrent=ivEnergy;
                tvCurrent=tvEnergy;
                break;
            case R.id.llData:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fl, new DataFragment()).commit();
                ivData.setSelected(true);
                tvData.setSelected(true);
                ivCurrent=ivData;
                tvCurrent=tvData;
                break;
            case R.id.llIden:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fl, new IdentityFragment()).commit();
                ivIden.setSelected(true);
                tvIden.setSelected(true);
                ivCurrent=ivIden;
                tvCurrent=tvIden;
                break;
            default:
                break;
        }
    }
}
