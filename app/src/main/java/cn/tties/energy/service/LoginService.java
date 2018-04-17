package cn.tties.energy.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import cn.tties.energy.common.Constants;
import cn.tties.energy.model.httputils.OkHttpUtils;
import cn.tties.energy.model.httputils.OkUiCallback;
import cn.tties.energy.model.httputils.params.LoginParams;
import cn.tties.energy.utils.ACache;


public class LoginService extends Service {

    private static final String TAG = "LoginService";

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "后台登录服务启动");
        LoginParams params = new LoginParams();
//        params.setUserName("南洋印染");
//        params.setPassword("123456");
        params.setUserName(ACache.getInstance().getAsString(Constants.CACHE_LOGIN_USERNAME));
        params.setPassword(ACache.getInstance().getAsString(Constants.CACHE_LOGIN_PASSWORD));
        HashMap<String,Object> map=new HashMap<>();
//        map.put()
//        OkHttpUtils.getInstance().getCall(Constants.BASE_RUL,)
//        Callback.CommonCallback<String> callback = new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Log.d(TAG,"返回数据: " + result);
//                EventBusBean bean = new EventBusBean();
//                bean.setKind(EventKind.EVENT_LOGIN);
//                //登录返回
//                LoginResult ret = JsonUtils.deserialize(result, LoginResult.class);
//                //正常登录
//                if (ret.getErrorCode() == 0) {
//                    bean.setObj(true);
//                } else {
//                    bean.setObj(false);
//                }
//                ACache.getInstance().put(Constants.CACHE_USERINFO, ret);
//
//                Log.d(TAG, "后台登录成功");
//                EventBus.getDefault().post(bean);
//                stopSelf();//结束服务
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                Log.d(TAG, "后台登录失败");
//                EventBusBean bean = new EventBusBean();
//                bean.setKind(EventKind.EVENT_LOGIN);
//                bean.setObj(false);
//                EventBus.getDefault().post(bean);
//                stopSelf();//结束服务
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                stopSelf();//结束服务
//            }
//
//            @Override
//            public void onFinished() {
//                stopSelf();//结束服务
//            }
//        };
//        x.http().post(params.getParams(), callback);
        return START_STICKY;
    }
}