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
import cn.tties.energy.model.IModel.DataModel;
import cn.tties.energy.model.IModel.Energy_TransformerModel;
import cn.tties.energy.model.IModel.IDataModel;
import cn.tties.energy.model.IModel.IEnergy_TransformerModel;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Databean;
import cn.tties.energy.model.result.Energy_TransformerDamgebean;
import cn.tties.energy.model.result.Energy_TransformerListbean;
import cn.tties.energy.model.result.Energy_TransformerTemperaturebean;
import cn.tties.energy.model.result.Energy_TransformerVolumebean;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.view.iview.IDataView;
import cn.tties.energy.view.iview.IEnergy_TransformerView;

/**
 * Created by li on 2018/4/3
 * description：
 * author：guojlli
 */

public class Energy_TransformerPresenter extends BasePresenter<IEnergy_TransformerView> {
    private static final String TAG = "Energy_TransformerPrese";
    IEnergy_TransformerView view;
    IEnergy_TransformerModel model;
    DataAllbean dataAllbean=new DataAllbean();
    public Energy_TransformerPresenter(IEnergy_TransformerView view){
        this.view=view;
        model=new Energy_TransformerModel();
    }
    //变压器列表
    public void getEnergy_TransformerList(){
        Map<String,Object> map=new HashMap<>();
        map.put("eleAccountId",dataAllbean.getEleAccountId());
        model.getEnergy_TransformerListData().getEnergy_TransformerList(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Energy_TransformerListbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Energy_TransformerListbean value) {
                        if(value!=null&&value.getErrorCode()==0){
                            view.setEnergy_TransformerListbeanData(value);
                        }else{
                            Log.i(TAG, "onError000: "+"数据有误");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError000: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //变压器变损
    public void getEnergy_TransformerDamge(int transformerId){
        String baseDate= DateUtil.getCurrentYear()+"-"+(DateUtil.getCurrentMonth()-1);
        Map<String,Object> map=new HashMap<>();
        map.put("transformerId",transformerId);
        map.put("eleAccountId",dataAllbean.getEleAccountId());
        map.put("baseDate",baseDate);

        Log.i(TAG, "onErrordata: "+transformerId);
        Log.i(TAG, "onErrordata: "+dataAllbean.getEleAccountId());
        model.getEnergy_TransformerDamgeData().getEnergy_TransformerDamge(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Energy_TransformerDamgebean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Energy_TransformerDamgebean value) {
                        if(value!=null&&value.getErrorCode()==0){
                            view.setEnergy_TransformerDamgebeanData(value);
                        }else{
                            Log.i(TAG, "onError111: "+"数据有误");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError111: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //变压器温度
    public void getEnergy_TransformerTemperature(int transformerId){
        Map<String,Object> map=new HashMap<>();
        map.put("transformerId",transformerId);
        map.put("baseDate",dataAllbean.getBaseData());
        Log.i(TAG, "onErrordata: "+transformerId);
        Log.i(TAG, "onErrordata: "+dataAllbean.getBaseData());
        model.getEnergy_TransformerTemperatureData().getEnergy_TransformerTemperature(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Energy_TransformerTemperaturebean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Energy_TransformerTemperaturebean value) {
                        if(value!=null&&value.getErrorCode()==0){
                            view.setEnergy_TransformerTemperaturebeanData(value);
                        }else{
                            Log.i(TAG, "onError2222: "+"数据有误");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError222: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //变压器容量
    public void getEnergy_TransformerVolume(int transformerId){
        Map<String,Object> map=new HashMap<>();
        map.put("userName",dataAllbean.getUserName());
        map.put("password",dataAllbean.getPassword());
        map.put("transformerId",transformerId);
        map.put("eleAccountId",dataAllbean.getEleAccountId());
        map.put("baseDate",dataAllbean.getTransformerVolumeBaseData());
        Log.i(TAG, "onErrordata: "+dataAllbean.getUserName());
        Log.i(TAG, "onErrordata: "+dataAllbean.getPassword());
        Log.i(TAG, "onErrordata: "+transformerId);
        Log.i(TAG, "onErrordata: "+dataAllbean.getEleAccountId());
        Log.i(TAG, "onErrordata: "+dataAllbean.getTransformerVolumeBaseData());
        model.getEnergy_TransformerVolumeData().getEnergy_TransformerVolume(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Energy_TransformerVolumebean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Energy_TransformerVolumebean value) {
                        if(value!=null&&value.getErrorCode()==0){
                            view.setEnergy_TransformerVolumebeanData(value);
                        }else{
                            Log.i(TAG, "onError333: "+"数据有误");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError333: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
