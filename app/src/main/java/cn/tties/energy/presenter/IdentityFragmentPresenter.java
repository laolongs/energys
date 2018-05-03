package cn.tties.energy.presenter;

import android.content.Context;
import android.util.Log;

import cn.tties.energy.view.dialog.CriProgressDialog;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import cn.tties.energy.base.BasePresenter;
import cn.tties.energy.common.Constants;
import cn.tties.energy.model.IModel.IIdentityFragmentModel;
import cn.tties.energy.model.IModel.IdentityFragmentModel;
import cn.tties.energy.model.result.Loginbean;
import cn.tties.energy.model.result.OpsLoginbean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.view.iview.IIdentityFragmentView;

/**
 * Created by li on 2018/3/30
 * description：
 * author：guojlli
 */

public class IdentityFragmentPresenter extends BasePresenter<IIdentityFragmentView> {
    private static final String TAG = "IdentityFragmentPresent";
    CriProgressDialog dialogPgs;
    IIdentityFragmentView view;
    IIdentityFragmentModel model;
    public IdentityFragmentPresenter(IIdentityFragmentView view, Context context){
        this.view=view;
        model= new IdentityFragmentModel();
        dialogPgs=new CriProgressDialog(context);
    }
    public void getOpsloginData(){
        dialogPgs.loadDialog("加载中...");
        //这个回来得数据暂时有误
        Loginbean bean = ACache.getInstance().getAsObject(Constants.CACHE_USERINFO);//"1502183891109"
//        bean.getAccountId();
        model.getOpsLoginData().getOpsLogin(bean.getAccountId()+"").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OpsLoginbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OpsLoginbean value) {
                        dialogPgs.removeDialog();
                        if(value != null&&value.getErrorCode()==0){
                            Log.i(TAG, "onNext: "+value.getResult().getMaintUser().getStaffName());
                            view.getOpsLoginData(value);
                        }else{
                            Log.i(TAG, "getOpsLoginData: "+"数据有误");
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
