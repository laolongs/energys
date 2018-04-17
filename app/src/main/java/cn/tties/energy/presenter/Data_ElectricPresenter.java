package cn.tties.energy.presenter;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import cn.tties.energy.api.RetrofitApi;
import cn.tties.energy.base.BasePresenter;
import cn.tties.energy.common.Constants;
import cn.tties.energy.model.IModel.Data_ElectricModel;
import cn.tties.energy.model.IModel.IData_ElectricModel;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Data_Electricbean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.view.iview.IData_ElectricView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by li on 2018/4/3
 * description：
 * author：guojlli
 */

public class Data_ElectricPresenter extends BasePresenter<IData_ElectricView> {
    private static final String TAG = "Data_ElectricPresenter";
    IData_ElectricView view;
    IData_ElectricModel model;
    DataAllbean dataAllbean=new DataAllbean();
    public Data_ElectricPresenter(IData_ElectricView view){
        this.view=view;
        model=new Data_ElectricModel();
    }
    public void getData_Electric(){
        Log.i(TAG, "onErrordata: "+dataAllbean.getUserName());
        Log.i(TAG, "onErrordata: "+dataAllbean.getPassword());
        Log.i(TAG, "onErrordata: "+dataAllbean.getObjId());
        Log.i(TAG, "onErrordata: "+dataAllbean.getObjType());
        Log.i(TAG, "onErrordata: "+dataAllbean.getBaseData());
        Map<String,Object> map=new HashMap<>();
        map.put("userName",dataAllbean.getUserName());
        map.put("password",dataAllbean.getPassword());
        map.put("objId",dataAllbean.getObjId());
        map.put("objType",dataAllbean.getObjType());
        map.put("baseDate",dataAllbean.getBaseData());
        map.put("dataType",6);//数据类型  6-电量  5-功率因数 3-有功功率 4-无功功率 1-电压 2-电流
        map.put("dateType",3);//月份
        model.getData_ElectricData().getData_Electric(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data_Electricbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Data_Electricbean value) {
                        if(value!=null){
                            view.setData_ElectricData(value);
                        }else{
                            Log.i(TAG, "onNext: "+"数据有误");
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
