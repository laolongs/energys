package cn.tties.energy.presenter;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import cn.tties.energy.view.dialog.CriProgressDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import cn.tties.energy.api.RetrofitApi;
import cn.tties.energy.base.BasePresenter;
import cn.tties.energy.common.Constants;
import cn.tties.energy.model.IModel.Data_ElectricModel;
import cn.tties.energy.model.IModel.EnergyFragmentModel;
import cn.tties.energy.model.IModel.IData_ElectricModel;
import cn.tties.energy.model.IModel.IEnergyFragmentModel;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Data_Electricbean;
import cn.tties.energy.model.result.EnergyFragmentbean;
import cn.tties.energy.model.result.Loginbean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.view.iview.IData_ElectricView;
import cn.tties.energy.view.iview.IEnergyFragmentView;

/**
 * Created by li on 2018/4/3
 * description：
 * author：guojlli
 */

public class EnergyFragmentPresenter extends BasePresenter<IEnergyFragmentView> {
    private static final String TAG = "EnergyFragmentPresenter";
    CriProgressDialog dialogPgs;
    IEnergyFragmentView view;
    IEnergyFragmentModel model;
    DataAllbean dataAllbean=new DataAllbean();
    public EnergyFragmentPresenter(IEnergyFragmentView view, Context context){
        this.view=view;
        model=new EnergyFragmentModel();
        dialogPgs=new CriProgressDialog(context);
    }
    public void getEnergyFragment(){
        dialogPgs.loadDialog("加载中...");
        Map<String,Object> map=new HashMap<>();
        map.put("userName",dataAllbean.getUserName());
        map.put("password",dataAllbean.getPassword());
        map.put("accountId",dataAllbean.getEleAccountId());
        model.getEnergyFragmentData().getEnergyFragment(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EnergyFragmentbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(EnergyFragmentbean value) {
                        dialogPgs.removeDialog();
                        if(value!=null){
                            view.setEnergyFragmentData(value);
                        }else{
                            Log.i(TAG, "onError: "+"数据有误");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialogPgs.removeDialog();
                        Log.i(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
