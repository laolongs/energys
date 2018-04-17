package cn.tties.energy.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import cn.tties.energy.base.BasePresenter;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.EventKind;
import cn.tties.energy.model.IModel.ILoginModel;
import cn.tties.energy.model.IModel.LoginModel;
import cn.tties.energy.model.bean.EventBusBean;
import cn.tties.energy.model.result.Loginbean;
import cn.tties.energy.model.result.OpsLoginbean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.EncryptUtil;
import cn.tties.energy.utils.StringUtil;
import cn.tties.energy.utils.ToastUtil;
import cn.tties.energy.view.MainActivity;
import cn.tties.energy.view.dialog.CriProgressDialog;
import cn.tties.energy.view.iview.ILoginView;

/**
 * mainpresenter
 */

public class LoginPresenter extends BasePresenter<ILoginView>  {
    private static final String TAG = "LoginSend";
    private CriProgressDialog dialog;
    ILoginView view;
    ILoginModel mainModel;
    String loginPass;
    String loginName;
    Context context;
    public LoginPresenter(ILoginView view,Context context){
       this.view=view;
        mainModel=new LoginModel();
        this.context=context;
        dialog = new CriProgressDialog(context);
    }
    public void showloginData(){
        dialog.loadDialog("登录中..");
        loginName = view.getLoginName();
        loginPass = view.getLoginPass();
        setPassword(loginPass);
        if(loginName.isEmpty()){
            ToastUtil.showShort(context,"用户名不能为空");
            return;
        }
        if(loginPass.isEmpty()){
            ToastUtil.showShort(context,"密码不能为空");
            return;
        }
        Map<String,Object> map=new HashMap<>();
        Log.i(TAG, "showloginData: "+getPassword());
        Log.i(TAG, "------------showloginData: "+loginPass);
        map.put("userName",loginName);
        map.put("password",loginPass);
        map.put("type",3);
        map.put("version",android.os.Build.VERSION.SDK_INT);
        mainModel.getloginData().getLogin(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Loginbean>(){

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Loginbean value) {
                        Log.d("返回数据 ", String.valueOf(value));
                        Log.d(TAG, "返回数据: " + value);
                        EventBusBean bean = new EventBusBean();
                        dialog.removeDialog();
                        if (value.isLoginFlag()) {
                            Log.d(TAG, "------"+value.isLoginFlag());
                            ACache.getInstance().put(Constants.CACHE_USERINFO, value);
                            ACache.getInstance().put(Constants.CACHE_LOGIN_STATUS, true);
                            ACache.getInstance().put(Constants.CACHE_LOGIN_USERNAME, loginName);
                            ACache.getInstance().put(Constants.CACHE_LOGIN_PASSWORD, view.getLoginPass());
                            ACache.getInstance().put(Constants.CACHE_LOGIN_PASSWORDMD5, getPassword());
                            getOpsloginData();
//                            Intent intent = new Intent(context, MainActivity.class);
//                            context.startActivity(intent);
//                            ((Activity)context).finish();
//                            MeterSend send = new MeterSend();
//                            send.send(null);
                        } else {
                            //0:登陆成功 1:用户名不存在或已注销 2:密码不正确 3:版本不正确,
                            if (value.getErrorCode() == 0) {
                                bean.setObj(true);
                                Log.d(TAG, "后台登录成功");
//                                MeterSend meterSend = new MeterSend();
//                                meterSend.send(null);
                                ACache.getInstance().put(Constants.CACHE_USERINFO, value);
                            } else {
                                bean.setObj(false);
                                Log.d(TAG, "后台登录失败");
                            }
                            bean.setKind(EventKind.EVENT_LOGIN);
                            EventBus.getDefault().post(bean);
                            if (value.getErrorCode() == 1) {

                                ToastUtil.showLong(context,"用户名不存在或已注销");
                            }
                            if (value.getErrorCode() == 2) {
                                ToastUtil.showLong(context,"密码不正确");
                            }
                            if (value.getErrorCode() == 3) {
                                ToastUtil.showLong(context,"版本不正确");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.removeDialog();
                        ToastUtil.showLong(context,"服务器异常");
                        Log.d(TAG, "后台登录失败"+e.getMessage());
                        EventBusBean bean = new EventBusBean();
                        bean.setKind(EventKind.EVENT_LOGIN);
                        bean.setObj(false);
                        EventBus.getDefault().post(bean);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    public String getPassword() {
        if (StringUtil.isEmpty(loginPass)) {
            loginPass = ACache.getInstance().getAsString(Constants.CACHE_LOGIN_PASSWORD);
            loginPass = EncryptUtil.MD5Encrypt(loginPass).toUpperCase();
        }
        return loginPass;
    }
    public void setPassword(String password) {
        this.loginPass = EncryptUtil.MD5Encrypt(password).toUpperCase();
    }
    public void getOpsloginData(){
        //这个回来得数据暂时有误
        Loginbean bean = ACache.getInstance().getAsObject(Constants.CACHE_USERINFO);//"1502183891109"
//        long accountId = bean.getAccountId();
        mainModel.getOpsLoginData().getOpsLogin(bean.getAccountId()+"").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OpsLoginbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OpsLoginbean value) {
                        if(value != null&&value.getErrorCode()==0){

                            Log.i(TAG, "onNext: "+value.getResult().getMaintUser().getStaffName());
                            view.getOpsLoginData(value);
                        }else{
                            Log.i(TAG, "getOpsLoginData: "+"数据有误");
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
