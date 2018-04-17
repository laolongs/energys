package cn.tties.energy.presenter;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import cn.tties.energy.base.BasePresenter;
import cn.tties.energy.common.Constants;
import cn.tties.energy.model.IModel.IIdentity_PassModel;
import cn.tties.energy.model.IModel.Identity_PassModel;
import cn.tties.energy.model.result.Identity_Passbean;
import cn.tties.energy.model.result.Loginbean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.EncryptUtil;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.utils.ToastUtil;
import cn.tties.energy.view.iview.IIdentity_PassView;

/**
 * Created by li on 2018/4/6
 * description：
 * author：guojlli
 */

public class Identity_PassPresenter extends BasePresenter<IIdentity_PassView> {
    private static final String TAG = "Identity_PassPresenter";
    IIdentity_PassView view;
    IIdentity_PassModel model;
    String loginPassold;
    String loginPassnew;
    String loginPassnew2;
    public Identity_PassPresenter(IIdentity_PassView view) {
        this.view = view;
        this.model=new Identity_PassModel();
    }
    public void getIdentity_Pass(Context context){
        String oldPass = view.getOldPass();
        setPasswordold(oldPass);
        String newPass = view.getNewPass();
        setPasswordnew(newPass);
        String new2Pass = view.getNew2Pass();
        setPasswordnew2(new2Pass);
        String password = ACache.getInstance().getAsString(Constants.CACHE_LOGIN_PASSWORD);
        if(oldPass.isEmpty()){
            ToastUtil.showShort(context,"密码不能为空");
            return;
        }
        if(!password.equals(view.getOldPass())){
            ToastUtil.showShort(context,"原密码不正确");
            return;
        }
        if(newPass.isEmpty()||new2Pass.isEmpty()){
            ToastUtil.showShort(context,"新密码或确认密码不能为空");
            return;
        }
        if(!newPass.equals(new2Pass)){
            ToastUtil.showShort(context,"新密码或确认密码不正确");
            return;
        }
        ToastUtil.showShort(context,"ok");
        Loginbean bean = ACache.getInstance().getAsObject(Constants.CACHE_USERINFO);
        Map<String,Object> map=new HashMap<>();
        map.put("accountId",bean.getAccountId());
        map.put("oldPassword",getPasswordold());
        map.put("password",getPasswordnew());
        model.getIdentity_PassData().gettIdentity_Pass(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Identity_Passbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Identity_Passbean value) {
                        if (value!=null){
                            view.setIdentity_PassData(value);
                        }else {
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
    public String getPasswordold() {
        if (StringUtil.isEmpty(loginPassold)) {
            loginPassold = ACache.getInstance().getAsString(Constants.CACHE_LOGIN_PASSWORD);
            loginPassold = EncryptUtil.MD5Encrypt(loginPassold).toUpperCase();
        }
        return loginPassold;
    }
    public void setPasswordold(String password) {
        this.loginPassold = EncryptUtil.MD5Encrypt(password).toUpperCase();
    }
    public String getPasswordnew() {
        if (StringUtil.isEmpty(loginPassnew)) {
            loginPassnew = ACache.getInstance().getAsString(Constants.CACHE_LOGIN_PASSWORD);
            loginPassnew = EncryptUtil.MD5Encrypt(loginPassnew).toUpperCase();
        }
        return loginPassnew;
    }
    public void setPasswordnew(String password) {
        this.loginPassnew = EncryptUtil.MD5Encrypt(password).toUpperCase();
    }
    public String getPasswordnew2() {
        if (StringUtil.isEmpty(loginPassnew2)) {
            loginPassnew2 = ACache.getInstance().getAsString(Constants.CACHE_LOGIN_PASSWORD);
            loginPassnew2 = EncryptUtil.MD5Encrypt(loginPassnew2).toUpperCase();
        }
        return loginPassnew2;
    }
    public void setPasswordnew2(String password) {
        this.loginPassnew2 = EncryptUtil.MD5Encrypt(password).toUpperCase();
    }
}
