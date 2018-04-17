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
import cn.tties.energy.model.IModel.DataModel;
import cn.tties.energy.model.IModel.Data_ElectricModel;
import cn.tties.energy.model.IModel.IDataModel;
import cn.tties.energy.model.IModel.IData_ElectricModel;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Data_Electricbean;
import cn.tties.energy.model.result.Databean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.view.iview.IDataView;
import cn.tties.energy.view.iview.IData_ElectricView;

/**
 * Created by li on 2018/4/3
 * description：
 * author：guojlli
 */

public class DataPresenter extends BasePresenter<IDataView> {
    private static final String TAG = "DataPresenter";
    IDataView view;
    IDataModel model;
    DataAllbean dataAllbean=new DataAllbean();
    public DataPresenter(IDataView view){
        this.view=view;
        model=new DataModel();
    }
    public void getData(){
        Log.i(TAG, "onErrordata: "+dataAllbean.getUserName());
        Log.i(TAG, "onErrordata: "+dataAllbean.getPassword());
        Log.i(TAG, "onErrordata: "+dataAllbean.getObjId());
        Log.i(TAG, "onErrordata: "+dataAllbean.getObjType());
        Log.i(TAG, "onErrordata: "+dataAllbean.getBaseData());
        Log.i(TAG, "onErrordata: "+dataAllbean.getEleAccountId());
        Map<String,Object> map=new HashMap<>();
        map.put("userName",dataAllbean.getUserName());
        map.put("password",dataAllbean.getPassword());
        map.put("objId",dataAllbean.getObjId());
        map.put("objType",dataAllbean.getObjType());
        map.put("baseDate",dataAllbean.getBaseData());
        map.put("eleAccountId",dataAllbean.getEleAccountId());
        map.put("dateType",2);
        model.getDataData().getData(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Databean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Databean value) {
                        if(value!=null){
                            view.setDataData(value);
                        }else{
                            Log.i(TAG, "onError: "+"数据有误");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onErrordata: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getchartData(){
        Map<String,Object> map=new HashMap<>();
        map.put("userName",dataAllbean.getUserName());
        map.put("password",dataAllbean.getPassword());
        map.put("objId",dataAllbean.getObjId());
        map.put("objType",dataAllbean.getObjType());
        map.put("baseDate",dataAllbean.getBaseData());
        map.put("eleAccountId",dataAllbean.getEleAccountId());
        map.put("dateType",2);
        model.getDataData().getData(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Databean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Databean value) {
                        if(value!=null){
                            view.setDataChartData(value);
                        }else{
                            Log.i(TAG, "onError: "+"数据有误");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onErrorchart: "+e.getMessage());
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
