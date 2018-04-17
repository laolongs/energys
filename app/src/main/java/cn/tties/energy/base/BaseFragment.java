package cn.tties.energy.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {

    protected View mView;
    protected Context mContext;
    protected T mPresenter;
    protected Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void init() {
        mActivity = getActivity();
        mContext = getActivity();
        createPresenter();
        mPresenter.attachView(this);
    }

    protected abstract void createPresenter();
//    protected abstract int getLayoutId();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.detachView();
        }
    }

    @Override
    public void onsuccess(Object o) {

    }

    @Override
    public void onError(Object o) {

    }
}
