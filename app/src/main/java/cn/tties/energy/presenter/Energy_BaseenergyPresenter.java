package cn.tties.energy.presenter;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import cn.tties.energy.base.BasePresenter;
import cn.tties.energy.model.IModel.DataFragmentModel;
import cn.tties.energy.model.IModel.Energy_BaseenergyModel;
import cn.tties.energy.model.IModel.IDataFragmentModel;
import cn.tties.energy.model.IModel.IEnergy_BaseenergyModel;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.DataFragmentbean;
import cn.tties.energy.model.result.Databean;
import cn.tties.energy.model.result.Energy_BasePlanbean;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.view.iview.IDataFragmentView;
import cn.tties.energy.view.iview.IEnergy_BaseenergyView;

/**
 * Created by li on 2018/4/3
 * description：
 * author：guojlli
 */

public class Energy_BaseenergyPresenter extends BasePresenter<IEnergy_BaseenergyView> {
    private static final String TAG = "Energy_BaseenergyPresen";
    IEnergy_BaseenergyView view;
    IEnergy_BaseenergyModel model;
    DataAllbean dataAllbean=new DataAllbean();
    public Energy_BaseenergyPresenter(IEnergy_BaseenergyView view){
        this.view=view;
        model=new Energy_BaseenergyModel();
    }
    public void getEnergy_Baseenergy(){
        String baseDate= DateUtil.getCurrentYear()+"-"+(DateUtil.getCurrentMonth()-1);
        Map<String,Object> map=new HashMap<>();
        map.put("userName",dataAllbean.getUserName());
        map.put("password",dataAllbean.getPassword());
        map.put("objId",dataAllbean.getEnergyledgerId());
        map.put("objType",1);
        map.put("baseDate", baseDate);
        map.put("eleAccountId",dataAllbean.getEleAccountId());
        map.put("dateType","1");
        model.getEnergy_BaseenergyData().getData(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Databean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Databean value) {
                        if(value!=null){
                            view.setEnergy_BaseenergyData(value);
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
    public void getEnergy_BaseenergyYear(){
        Map<String,Object> map=new HashMap<>();
        map.put("userName",dataAllbean.getUserName());
        map.put("password",dataAllbean.getPassword());
        map.put("objId",dataAllbean.getEnergyledgerId());
        map.put("objType",1);
        map.put("baseDate", dataAllbean.getBaseData());
        map.put("eleAccountId",dataAllbean.getEleAccountId());
        map.put("dateType",2);
        Log.i(TAG, "onErrordata: "+dataAllbean.getUserName());
        Log.i(TAG, "onErrordata: "+dataAllbean.getPassword());
        Log.i(TAG, "onErrordata: "+dataAllbean.getEnergyledgerId());
        Log.i(TAG, "onErrordata: "+dataAllbean.getBaseData());
        Log.i(TAG, "onErrordata: "+dataAllbean.getEleAccountId());
        model.getEnergy_BaseenergyData().getData(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Databean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Databean value) {
                        if(value!=null){
                            view.setEnergy_BaseenergyYearData(value);
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
    public void getEnergy_BasePlan(){
        String baseDate= DateUtil.getCurrentYear()+"-"+(DateUtil.getCurrentMonth()-1);
        Map<String,Object> map=new HashMap<>();
        map.put("userName",dataAllbean.getUserName());
        map.put("password",dataAllbean.getPassword());
        map.put("objId",dataAllbean.getEnergyledgerId());
        map.put("baseDate", baseDate);
        map.put("eleAccountId",dataAllbean.getEleAccountId());
        model.getEnergy_BasePlanData().getEnergy_BasePlan(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Energy_BasePlanbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Energy_BasePlanbean value) {
                        if(value!=null){
                            view.setEnergy_BasePlanData(value);
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
}
