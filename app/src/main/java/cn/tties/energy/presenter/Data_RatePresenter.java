package cn.tties.energy.presenter;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import cn.tties.energy.api.RetrofitApi;
import cn.tties.energy.base.BasePresenter;
import cn.tties.energy.common.Constants;
import cn.tties.energy.model.IModel.Data_NoModel;
import cn.tties.energy.model.IModel.Data_RateModel;
import cn.tties.energy.model.IModel.IData_NoModel;
import cn.tties.energy.model.IModel.IData_RateModel;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Data_HaveKwbean;
import cn.tties.energy.model.result.Data_NoKvarbean;
import cn.tties.energy.model.result.Data_Nobean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.view.iview.IData_NoView;
import cn.tties.energy.view.iview.IData_RateView;

/**
 * mainpresenter
 */

public class Data_RatePresenter extends BasePresenter<IData_RateView>  {
    private static final String TAG = "Data_RatePresenter";
    IData_RateView view;
    IData_RateModel model;
    DataAllbean dataAllbean=new DataAllbean();
    public Data_RatePresenter(IData_RateView view) {
        this.view = view;
        this.model = new Data_RateModel();
    }
    public void getData_HaveKwData(){
        Map<String,Object> map=new HashMap<>();
        map.put("userName",dataAllbean.getUserName());
        map.put("password",dataAllbean.getPassword());
        map.put("objId",dataAllbean.getObjId());
        map.put("objType",dataAllbean.getObjType());
        map.put("baseDate",dataAllbean.getBaseData());
        map.put("dataType",3);//数据类型  6-电量  5-功率因数 3-有功功率 4-无功功率 1-电压 2-电流
        map.put("dateType",3);//月份
        model.getData_HaveKWData().getData_HaveKw(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data_HaveKwbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Data_HaveKwbean value) {
                        if(value!=null){
                            Log.i(TAG, "onNext: ");
                            view.setData_HaveKWData(value);
                        }else{
                            Log.i(TAG, "onError: "+"数据有误");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: "+"数据有误");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    public void getData_NoKvarKwData(){
        Map<String,Object> map=new HashMap<>();
        map.put("userName",dataAllbean.getUserName());
        map.put("password",dataAllbean.getPassword());
        map.put("objId",dataAllbean.getObjId());
        map.put("objType",dataAllbean.getObjType());
        map.put("baseDate",dataAllbean.getBaseData());
        map.put("dataType",4);//数据类型  6-电量  5-功率因数 3-有功功率 4-无功功率 1-电压 2-电流
        map.put("dateType",3);//月份
        model.getData_NoKvarData().getData_NoKvar(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data_NoKvarbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Data_NoKvarbean value) {
                        if(value!=null){
                            Log.i(TAG, "onNext: ");
                            view.setData_NoKvarData(value);
                        }else{
                            Log.i(TAG, "onError: "+"数据有误");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: "+"数据有误");
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
                    Log.i(TAG, "onNext: "+value.getMeterList().size());
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
