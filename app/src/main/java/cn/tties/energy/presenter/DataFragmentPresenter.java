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
    IDataFragmentView view;
    IDataFragmentModel model;
    DataAllbean dataAllbean=new DataAllbean();
    public DataFragmentPresenter(IDataFragmentView view){
        this.view=view;
        model=new DataFragmentModel();
    }
    public void getDataFragment(){
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
                        if(value!=null){
                            view.setDataFragmentData(value);
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
