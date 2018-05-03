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
import cn.tties.energy.model.IModel.DataFragmentModel;
import cn.tties.energy.model.IModel.DataModel;
import cn.tties.energy.model.IModel.IDataFragmentModel;
import cn.tties.energy.model.IModel.IDataModel;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.DataFragmentbean;
import cn.tties.energy.model.result.Data_Electricbean;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.view.iview.IDataFragmentView;
import cn.tties.energy.view.iview.IDataView;

/**
 * Created by li on 2018/4/3
 * description：
 * author：guojlli
 */

public class DataFragmentPresenter extends BasePresenter<IDataFragmentView> {
    private static final String TAG = "DataFragmentPresenter";
    CriProgressDialog dialogPgs;
    IDataFragmentView view;
    IDataFragmentModel model;
    DataAllbean dataAllbean=new DataAllbean();
    public DataFragmentPresenter(IDataFragmentView view, Context context){
        this.view=view;
        model=new DataFragmentModel();
        dialogPgs=new CriProgressDialog(context);
    }
    public void getDataFragment(){
        dialogPgs.loadDialog("加载中...");
        String baseDate=DateUtil.getCurrentYear()+"-"+(DateUtil.getCurrentMonth()-1);
        Map<String,Object> map=new HashMap<>();
        map.put("userName",dataAllbean.getUserName());
        map.put("password",dataAllbean.getPassword());
        map.put("objId",dataAllbean.getEnergyledgerId());
        map.put("objType",1);
        map.put("baseDate", baseDate);
        map.put("count","12");
        map.put("eleAccountId",dataAllbean.getEleAccountId());

        model.getDataFragmentData().getDataFragemet(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataFragmentbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataFragmentbean value) {
                        dialogPgs.removeDialog();
                        if(value!=null){
                            view.setDataFragmentData(value);
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

}
