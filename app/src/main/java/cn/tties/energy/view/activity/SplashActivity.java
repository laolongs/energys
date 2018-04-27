package cn.tties.energy.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.tties.energy.R;
import cn.tties.energy.application.MyApplication;
import cn.tties.energy.base.BaseActivity;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.EventKind;
import cn.tties.energy.model.bean.EventBusBean;
import cn.tties.energy.model.httputils.send.VersionSend;
import cn.tties.energy.presenter.MainPresenter;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.AnimationUtils;
import cn.tties.energy.view.MainActivity;
import cn.tties.energy.view.iview.IMainView;

public class SplashActivity extends BaseActivity<MainPresenter> implements IMainView{
    private static final int FAILURE = 0; // 失败
    private static final int SUCCESS = 1; // 成功
    private static final int SHOW_TIME_MIN = 2000; //最短显示时间
    LinearLayout splashLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFindView();
        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected Integer doInBackground(Void... params) {
                int result = loadingCache();
                long startTime = System.currentTimeMillis();
                long loadingTime = System.currentTimeMillis() - startTime;
                if (loadingTime < SHOW_TIME_MIN) {
                    try {
                        Thread.sleep(SHOW_TIME_MIN - loadingTime);


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return result;
            }

            @Override
            protected void onPostExecute(Integer result) {
                AnimationUtils.showAndHiddenAnimation(splashLogin, AnimationUtils.AnimationState.STATE_SHOW,1000);
                splashLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                });
                //进入登录画面
//                if (result == FAILURE) {
//                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                }
            }
        }.execute();
    }

    private void initFindView() {
        splashLogin=findViewById(R.id.splash_login);
    }

    @Override
    protected void createPresenter() {
        mPresenter=new MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    private int loadingCache() {
        Boolean loginStatus = ACache.getInstance().getAsObject(Constants.CACHE_LOGIN_STATUS);
        initVersion();
        if (!MyApplication.mNetWorkState || loginStatus == null || !loginStatus) {
            Log.d("logStatus", "用户非登录状态");
            return FAILURE;
        } else {
//            LoginSend send = new LoginSend();
//            send.send(new LoginParams());
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        return SUCCESS;
    }

    private void initVersion() {
        VersionSend.getVersionData(SplashActivity.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean bean) {
        if (bean != null && bean.getKind().equals(EventKind.EVENT_LOGIN)) {
            Boolean loginStatus = bean.getObj();
            if (!loginStatus) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
        if (bean != null && bean.getKind().equals(EventKind.EVENT_METER_SYCN)) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    @Override
    public void setViewPageData(List<View> list) {

    }

}
