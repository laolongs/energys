package cn.tties.energy.presenter;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import cn.tties.energy.base.BasePresenter;
import cn.tties.energy.common.Constants;
import cn.tties.energy.model.IModel.OpsDataModel;
import cn.tties.energy.model.result.DataAllbean;
import cn.tties.energy.model.result.OpsLoginbean;
import cn.tties.energy.model.result.Opsbean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.view.iview.IOpsView;

/**
 * Created by li on 2018/3/23
 * description：
 * author：guojlli
 */

public class OpsPresenter extends BasePresenter<IOpsView> {
    private static final String TAG = "OpsPresenter";
    IOpsView view;
    OpsDataModel model;
    int pagenum;
    int patrolType;
    DataAllbean dataAllbean=new DataAllbean();
    public int getPatrolType() {
        return patrolType;
    }

    public void setPatrolType(int patrolType) {
        this.patrolType = patrolType;
    }

    public OpsPresenter(IOpsView view) {
        this.view = view;
        this.model = new OpsDataModel();
    }
    public void setPageNum(int pagenum){
        this.pagenum=pagenum;
    }
    public int getPageNum(){
        return pagenum;
    }
    public void getOpsRightData(){
        String companyid = ACache.getInstance().getAsString(Constants.CACHE_OPS_COMPANDID);
        HashMap<String,Object> map=new HashMap<>();
        map.put("companyId",companyid);
        map.put("patrolType",getPatrolType());
        map.put("pagesize",10);
        map.put("pagenum",getPageNum());
        model.getOpsData().getOps(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Opsbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Opsbean value) {
                        if(value!=null&&value.getErrorCode()==0){
                            Log.i(TAG, "onNext: ");
                            view.setOpsRightData(value);
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
