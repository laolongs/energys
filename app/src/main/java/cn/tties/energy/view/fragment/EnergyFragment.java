package cn.tties.energy.view.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import cn.tties.energy.R;
import cn.tties.energy.base.BaseFragment;
import cn.tties.energy.common.CircleProgressBar;
import cn.tties.energy.common.MyEnergyProgressRound;
import cn.tties.energy.common.MyNoDoubleClickListener;
import cn.tties.energy.model.result.EnergyFragmentbean;
import cn.tties.energy.presenter.EnergyFragmentPresenter;
import cn.tties.energy.view.activity.Energy_BaseenergyActivity;
import cn.tties.energy.view.activity.Energy_ElectricalActivity;
import cn.tties.energy.view.activity.Energy_EnergyActivity;
import cn.tties.energy.view.activity.Energy_ForceActivity;
import cn.tties.energy.view.activity.Energy_OpsActivity;
import cn.tties.energy.view.activity.Energy_TransformerActivity;
import cn.tties.energy.view.dialog.CriProgressDialog;
import cn.tties.energy.view.iview.IEnergyFragmentView;

/**
 * Created by li on 2018/3/21
 * description：
 * author：guojlli
 */

public class EnergyFragment extends BaseFragment<EnergyFragmentPresenter> implements View.OnClickListener, IEnergyFragmentView {
    private static final String TAG = "EnergyFragment";
    MyEnergyProgressRound tasksView;
    TextView energyUsermark;
    LinearLayout energyBaseenergy;
    LinearLayout energyElectrical;
    LinearLayout energyForce;
    LinearLayout energyOps;
    LinearLayout energyEnergy;
    LinearLayout energyTransformer;
    RatingBar energyBar;
    TextView energyToolbarText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View inflate = inflater.inflate(R.layout.fragment_energy, null);
        initFindView(inflate);
        initView();
        return inflate;
    }

    private void initFindView(View inflate) {
        energyBaseenergy = inflate.findViewById(R.id.energy_baseenergy);
        energyElectrical = inflate.findViewById(R.id.energy_electrical);
        energyForce = inflate.findViewById(R.id.energy_force);
        energyOps = inflate.findViewById(R.id.energy_ops);
        energyEnergy = inflate.findViewById(R.id.energy_energy);
        energyTransformer = inflate.findViewById(R.id.energy_transformer);
        energyToolbarText = inflate.findViewById(R.id.energy_toolbar_text);
        energyBar = inflate.findViewById(R.id.energy_bar);
        tasksView = inflate.findViewById(R.id.tasks_view);
        energyUsermark = inflate.findViewById(R.id.energy_usermark);
        energyBaseenergy.setOnClickListener(this);
        energyElectrical.setOnClickListener(this);
        energyForce.setOnClickListener(this);
        energyOps.setOnClickListener(this);
        energyEnergy.setOnClickListener(this);
        energyTransformer.setOnClickListener(this);
    }

    private void initView() {
        mPresenter.getEnergyFragment();
        energyToolbarText.setText("电力能效");
    }

    @Override
    protected void createPresenter() {
        mPresenter = new EnergyFragmentPresenter(this,getActivity());
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
//            基本电量优化
            case R.id.energy_baseenergy:
                if(MyNoDoubleClickListener.isFastClick()){
                    intent = new Intent(getActivity(), Energy_BaseenergyActivity.class);
                    startActivity(intent);
                }

                break;
//            电度电量优化
            case R.id.energy_electrical:
                if(MyNoDoubleClickListener.isFastClick()){
                    intent = new Intent(getActivity(), Energy_ElectricalActivity.class);
                    startActivity(intent);
                }

                break;
//            力调电费优化
            case R.id.energy_force:
                if(MyNoDoubleClickListener.isFastClick()){
                    intent = new Intent(getActivity(), Energy_ForceActivity.class);
                    startActivity(intent);
                }

                break;
            //变压器优化
            case R.id.energy_transformer:
                if(MyNoDoubleClickListener.isFastClick()){
                    intent = new Intent(getActivity(), Energy_TransformerActivity.class);
                    startActivity(intent);
                }

                break;
            //运维月报
            case R.id.energy_ops:
                if(MyNoDoubleClickListener.isFastClick()){
                    intent = new Intent(getActivity(), Energy_OpsActivity.class);
                    startActivity(intent);
                }

                break;
            //能效月报
            case R.id.energy_energy:
                if(MyNoDoubleClickListener.isFastClick()){
                    intent = new Intent(getActivity(), Energy_EnergyActivity.class);
                    startActivity(intent);
                }

                break;
        }
    }

    @Override
    public void setEnergyFragmentData(EnergyFragmentbean bean) {
            tasksView.setProgressMax(bean.getTotalScore());
            energyBar.setRating((float) bean.getStartScore() / 20);
            energyUsermark.setText("您目前击败了"+bean.getRank() + "%的同行用户");
    }
}
