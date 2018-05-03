package cn.tties.energy.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import cn.tties.energy.api.RetrofitApi;
import cn.tties.energy.api.RetrofitApiPub;
import cn.tties.energy.base.BasePresenter;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.EventKind;
import cn.tties.energy.model.IModel.Data_PressModel;
import cn.tties.energy.model.IModel.IData_PressModel;
import cn.tties.energy.model.IModel.ILoginModel;
import cn.tties.energy.model.IModel.LoginModel;
import cn.tties.energy.model.bean.EventBusBean;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.Data_Pressbean;
import cn.tties.energy.model.result.Loginbean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.EncryptUtil;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.utils.ToastUtil;
import cn.tties.energy.view.MainActivity;
import cn.tties.energy.view.dialog.CriProgressDialog;
import cn.tties.energy.view.iview.IData_PressView;
import cn.tties.energy.view.iview.ILoginView;

/**
 * mainpresenter
 */

public class Data_PressPresenter extends BasePresenter<IData_PressView>  {
    private static final String TAG = "Data_PressPresenter";
    CriProgressDialog dialogPgs;
    IData_PressView view;
    IData_PressModel model;
    DataAllbean dataAllbean=new DataAllbean();
    public Data_PressPresenter(IData_PressView view,Context context) {
        this.view = view;
        this.model = new Data_PressModel();
        dialogPgs=new CriProgressDialog(context);
    }
    public void getData_PressData(){
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
        map.put("dataType",1);//数据类型  0-电流不平衡  1-电压不平衡
        model.getData_PressData().getData_Press(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data_Pressbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Data_Pressbean value) {
                        dialogPgs.removeDialog();
                        if(value!=null){
                            view.setData_PressData(value);
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
