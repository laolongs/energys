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
import cn.tties.energy.model.IModel.Data_FactorModel;
import cn.tties.energy.model.IModel.IData_ElectricModel;
import cn.tties.energy.model.IModel.IData_FactorModel;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Data_Electricbean;
import cn.tties.energy.model.result.Data_Factorbean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.view.iview.IData_ElectricView;
import cn.tties.energy.view.iview.IData_FactorView;

/**
 * Created by li on 2018/4/3
 * description：
 * author：guojlli
 */

public class Data_FactorPresenter extends BasePresenter<IData_FactorView> {
    private static final String TAG = "Data_FactorPresenter";
    CriProgressDialog dialogPgs;
    IData_FactorView view;
    IData_FactorModel model;
    DataAllbean dataAllbean=new DataAllbean();
    public Data_FactorPresenter(IData_FactorView view, Context context){
        this.view=view;
        model=new Data_FactorModel();
        dialogPgs=new CriProgressDialog(context);
    }
    public void getData_Electric(){
        dialogPgs.loadDialog("加载中...");
        Map<String,Object> map=new HashMap<>();
        map.put("userName",dataAllbean.getUserName());
        map.put("password",dataAllbean.getPassword());
        map.put("objId",dataAllbean.getObjId());
        map.put("objType",dataAllbean.getObjType());
        map.put("baseDate",dataAllbean.getBaseData());
        map.put("dataType",5);//数据类型  6-电量  5-功率因数 3-有功功率 4-无功功率 1-电压 2-电流
        map.put("dateType",3);//月份
        model.getData_FactorData().getData_Factor(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data_Factorbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Data_Factorbean value) {
                        dialogPgs.removeDialog();
                        if(value!=null){
                            view.setData_FactorData(value);
                        }else{
                            Log.i(TAG, "onNext: "+"数据有误");
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
    public void getAllElectricityData() {
        Map<String,Object> map=new HashMap<>();
        long asObject = ACache.getInstance().getAsObject(Constants.CACHE_OPS_ENERGYLEDGERID);
        map.put("userName",dataAllbean.getUserName());
        map.put("password",dataAllbean.getPassword());
        map.put("objId",asObject);
        map.put("objType",1);
        RetrofitApi.getServer().getAllElectricity(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer<AllElectricitybean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(AllElectricitybean value) {
                if(value!=null){
                   view.setAllElectricity(value);
                }else{
                    Log.i(TAG, "onError: "+"数据有误");
                }

            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
