package cn.tties.energy.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.tties.energy.R;
import cn.tties.energy.base.BaseFragment;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.MyNoDoubleClickListener;
import cn.tties.energy.model.result.Loginbean;
import cn.tties.energy.model.result.OpsLoginbean;
import cn.tties.energy.presenter.IdentityFragmentPresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.ToastUtil;
import cn.tties.energy.view.activity.AboutActivity;
import cn.tties.energy.view.activity.ChangeTableActivity;
import cn.tties.energy.view.activity.LoginActivity;
import cn.tties.energy.view.activity.PasswordActivity;
import cn.tties.energy.view.activity.UpdateActivity;
import cn.tties.energy.view.activity.VersionActivity;
import cn.tties.energy.view.dialog.ConfirmDialog;
import cn.tties.energy.view.dialog.CriProgressDialog;
import cn.tties.energy.view.iview.IIdentityFragmentView;

/**
 * Created by li on 2018/3/21
 * description：
 * author：guojlli
 */

public class IdentityFragment extends BaseFragment<IdentityFragmentPresenter> implements View.OnClickListener, IIdentityFragmentView {
    private static final String TAG = "IdentityFragment";
    Toolbar identityToolbar;
    TextView identityName;
    TextView identityCompany;
    TextView identityNumber;
    LinearLayout layoutPassword;
    LinearLayout layoutVersion;
    LinearLayout identityAbout;
    LinearLayout layoutLoginout;
    TextView identitySwitchElectricity;
    ImageView identityImg;
    ImageView identitySwitchSelsect;
    int num = 0;
    CriProgressDialog dialogPgs;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View inflate = inflater.inflate(R.layout.fragment_identity, null);
        initFindView(inflate);
        return inflate;
    }

    private void initFindView(View inflate) {
        identityToolbar = inflate.findViewById(R.id.identity_toolbar);
        layoutPassword = inflate.findViewById(R.id.layout_password);
        layoutVersion = inflate.findViewById(R.id.layout_version);
        identityAbout = inflate.findViewById(R.id.identity_about);
        layoutLoginout = inflate.findViewById(R.id.layout_loginout);
        identityImg = inflate.findViewById(R.id.identity_img);
        identityName = inflate.findViewById(R.id.identity_name);
        identityNumber = inflate.findViewById(R.id.identity_number);
        identityCompany = inflate.findViewById(R.id.identity_company);
        identitySwitchSelsect = inflate.findViewById(R.id.identity_switch_selsect);
        identitySwitchElectricity = inflate.findViewById(R.id.identity_switch_electricity);
        layoutPassword.setOnClickListener(this);
        layoutVersion.setOnClickListener(this);
        identityAbout.setOnClickListener(this);
        layoutLoginout.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        dialogPgs=new CriProgressDialog(getActivity());
        dialogPgs.loadDialog("加载中...");
        mPresenter.getOpsloginData();//1502183891109
    }

    @Override
    protected void createPresenter() {
        mPresenter = new IdentityFragmentPresenter(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            //切换表号
//            case R.id.layout_tablenumber:
//                intent = new Intent(getActivity(), TablenNumberActivity.class);
//                startActivity(intent);
//                break;
            //修改密码
            case R.id.layout_password:
                if(MyNoDoubleClickListener.isFastClick()){
                    intent = new Intent(getActivity(), PasswordActivity.class);
                    startActivity(intent);
                }

                break;
            //版本更新
            case R.id.layout_version:
                if(MyNoDoubleClickListener.isFastClick()){
                    intent = new Intent(getActivity(), UpdateActivity.class);
                    startActivity(intent);
                }

                break;
            //关于我们
            case R.id.identity_about:
                ToastUtil.showShort(getActivity(),"暂无关于我们！");
//                intent = new Intent(getActivity(), AboutActivity.class);
//                startActivity(intent);
                break;
            //设置
            case R.id.layout_loginout:
                ConfirmDialog dialog = new ConfirmDialog(getActivity());
                dialog.loadDialog("退出登录", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtil.showLong(getActivity(), "已退出登录");
                        ACache.getInstance().put(Constants.CACHE_LOGIN_STATUS, false);
                        final Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                break;
        }
    }
    @Override
    public void getOpsLoginData(final OpsLoginbean opsLoginbean) {
        dialogPgs.removeDialog();
        OpsLoginbean loginbean = ACache.getInstance().getAsObject(Constants.CACHE_OPSLOGIN_USERINFO);
        List<OpsLoginbean.ResultBean.EnergyLedgerListBean> energyLedgerList = opsLoginbean.getResult().getEnergyLedgerList();
        identityName.setText(opsLoginbean.getResult().getMaintUser().getStaffName());
        identityCompany.setText(opsLoginbean.getResult().getCompanyName() + "");
        if (opsLoginbean.getResult().getEnergyLedgerList().size() > 0) {
            String ischeck = ACache.getInstance().getAsString(Constants.CACHE_ISCHECK);
            int postion = Integer.parseInt(ischeck);
            for (int i = 0; i < energyLedgerList.size(); i++) {
                if (i == postion) {
                    long energyLedgerId = loginbean.getResult().getEnergyLedgerList().get(postion).getEnergyLedgerId();
                    identityNumber.setText(energyLedgerId + "");
                    Log.i(TAG, "getOpsLoginData: " + energyLedgerId);
                }
            }
            num = opsLoginbean.getResult().getEnergyLedgerList().size();
            if (num > 0 && num == 1) {
                identitySwitchSelsect.setVisibility(View.INVISIBLE);
                identitySwitchElectricity.setText("仅有1个电表");
            } else {
                identitySwitchSelsect.setVisibility(View.VISIBLE);
                identitySwitchElectricity.setText("共有" + num + "个电表 切换电表");
                identitySwitchElectricity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(MyNoDoubleClickListener.isFastClick()){
                            Intent intent1 = new Intent(getActivity(), ChangeTableActivity.class);
                            intent1.putExtra("bean", opsLoginbean);
                            startActivity(intent1);
                        }

                    }
                });

            }
        } else {
            Log.i(TAG, "getOpsLoginData: " + "当前运维无信息");
        }
    }
}
