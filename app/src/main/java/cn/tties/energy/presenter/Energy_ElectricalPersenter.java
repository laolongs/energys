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
import cn.tties.energy.base.BasePresenter;
import cn.tties.energy.model.IModel.DataModel;
import cn.tties.energy.model.IModel.IDataModel;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Databean;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.view.iview.IDataView;
import cn.tties.energy.view.iview.IEnergy_BaseenergyView;

/**
 * Created by li on 2018/4/11
 * description：
 * author：guojlli
 */

public class Energy_ElectricalPersenter extends BasePresenter<IDataView> {
    private static final String TAG = "Energy_ElectricalPersen";
    CriProgressDialog dialogPgs;
    IDataView view;
    IDataModel model;
    DataAllbean dataAllbean=new DataAllbean();
    public Energy_ElectricalPersenter(IDataView view, Context context){
        this.view=view;
        model=new DataModel();
        dialogPgs=new CriProgressDialog(context);
    }
    public void getEnergy_Electrical(){
        dialogPgs.loadDialog("加载中...");
        String baseDate= DateUtil.getCurrentYear()+"-"+(DateUtil.getCurrentMonth()-1);
        Map<String,Object> map=new HashMap<>();
        map.put("userName",dataAllbean.getUserName());
        map.put("password",dataAllbean.getPassword());
        map.put("objId",dataAllbean.getEnergyledgerId());
        map.put("objType",1);//对象类型（1、分户；2、计量点）
        map.put("baseDate",baseDate);
        map.put("eleAccountId",dataAllbean.getEleAccountId());
        map.put("dateType",1);//日期类型（1、月   2年；）
        Log.i(TAG, "onErrordata: "+dataAllbean.getUserName());
        Log.i(TAG, "onErrordata: "+dataAllbean.getPassword());
        Log.i(TAG, "onErrordata: "+dataAllbean.getEnergyledgerId());
        Log.i(TAG, "onErrordata: "+baseDate);
        Log.i(TAG, "onErrordata: "+dataAllbean.getEleAccountId());
        model.getDataData().getData(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Databean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Databean value) {
                        dialogPgs.removeDialog();
                        if(value!=null){
                            view.setDataData(value);
                        }else{
                            Log.i(TAG, "onError: "+"数据有误");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialogPgs.removeDialog();
                        Log.i(TAG, "onErrordata: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getEnergy_ElectricalChart(){
        Map<String,Object> map=new HashMap<>();
        map.put("userName",dataAllbean.getUserName());
        map.put("password",dataAllbean.getPassword());
        map.put("objId",dataAllbean.getEnergyledgerId());
        map.put("objType",1);
        map.put("baseDate",dataAllbean.getBaseData());
        map.put("eleAccountId",dataAllbean.getEleAccountId());
        map.put("dateType",2);
        Log.i(TAG, "onErrordata: "+dataAllbean.getUserName());
        Log.i(TAG, "onErrordata: "+dataAllbean.getPassword());
        Log.i(TAG, "onErrordata: "+dataAllbean.getEnergyledgerId());
        Log.i(TAG, "onErrordata: "+dataAllbean.getBaseData());
        Log.i(TAG, "onErrordata: "+dataAllbean.getEleAccountId());
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
}
