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
import cn.tties.energy.api.RetrofitApiPub;
import cn.tties.energy.base.BasePresenter;
import cn.tties.energy.common.Constants;
import cn.tties.energy.model.IModel.Data_NoModel;
import cn.tties.energy.model.IModel.Data_PressModel;
import cn.tties.energy.model.IModel.IData_NoModel;
import cn.tties.energy.model.IModel.IData_PressModel;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Data_Nobean;
import cn.tties.energy.model.result.Data_Pressbean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.view.iview.IData_NoView;
import cn.tties.energy.view.iview.IData_PressView;

/**
 * mainpresenter
 */

public class Data_NoPresenter extends BasePresenter<IData_NoView>  {
    private static final String TAG = "Data_NoPresenter";
    CriProgressDialog dialogPgs;
    IData_NoView view;
    IData_NoModel model;
    DataAllbean dataAllbean=new DataAllbean();
    public Data_NoPresenter(IData_NoView view, Context context) {
        this.view = view;
        this.model = new Data_NoModel();
        dialogPgs=new CriProgressDialog(context);
    }
    public void getData_NoData(){
        dialogPgs.loadDialog("加载中...");
        Log.i(TAG, "onErrordata: "+dataAllbean.getUserName());
        Log.i(TAG, "onErrordata: "+dataAllbean.getPassword());
        Log.i(TAG, "onErrordata: "+dataAllbean.getObjId());
        Log.i(TAG, "onErrordata: "+dataAllbean.getBaseData());
        Map<String,Object> map=new HashMap<>();
        map.put("userName",dataAllbean.getUserName());
        map.put("password",dataAllbean.getPassword());
        map.put("objId",dataAllbean.getObjId());
        map.put("baseDate",dataAllbean.getBaseData());
        map.put("dataType",0);//数据类型  0-电流不平衡  1-电压不平衡
//        map.put("objId","1486536481282");
        model.getData_NoData().getData_No(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data_Nobean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Data_Nobean value) {
                        dialogPgs.removeDialog();
                        Log.i(TAG, "onNext: "+value.getLimitData().size());
                        if(value!=null){
                            view.setData_NoData(value);
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
