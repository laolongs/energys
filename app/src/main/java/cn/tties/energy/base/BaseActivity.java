package cn.tties.energy.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.tties.energy.application.MyApplication;
import cn.tties.energy.model.bean.EventBusBean;


public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView{

    protected Context mContext;
    protected T mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置ap全屏展示没有标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        沉浸式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                 Window window = this.getWindow();
                 View decorView = window.getDecorView();
                 //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                 int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                 decorView.setSystemUiVisibility(option);
                 window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                 window.setStatusBarColor(Color.TRANSPARENT);
                 //导航栏颜色也可以正常设置 /
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = this.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
                 }
        }


                setContentView(getLayoutId());
                init();
        }
    //沉浸式相关方法
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void init(){
        mContext = this;
        MyApplication.getInstance().addActivity(this);//添加当前Activity
        createPresenter();
        mPresenter.attachView(this);
        //注册Eventbus
        EventBus.getDefault().register(this);
    }

    protected abstract void createPresenter();
    //初始化布局
    protected abstract int getLayoutId();



    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);//移除当前Activity
        //        Presenter解除绑定
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        EventBus.getDefault().unregister(this);
    }
    public void oneActivityDo() {
        onResume();
    }

    @Override
    public void onError(Object o) {

    }

    @Override
    public void onsuccess(Object o) {

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean bean) {
    }
}
