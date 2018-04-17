package cn.tties.energy.model.httputils.send;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import cn.tties.energy.api.ObserverApi;
import cn.tties.energy.api.RetrofitApi;
import cn.tties.energy.common.Constants;
import cn.tties.energy.common.EventKind;
import cn.tties.energy.model.bean.EventBusBean;
import cn.tties.energy.model.result.Versionbean;
import cn.tties.energy.utils.ACache;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by li on 2018/3/22
 * description：版本更新
 * author：guojlli
 */

public class VersionSend {
  public static void getVersionData(Context context){
      final String TAG = "VersionSend";
      RetrofitApi.getServer()
              .getVersion()
              .observeOn(AndroidSchedulers.mainThread())
              .subscribeOn(Schedulers.io())
              .subscribe(new ObserverApi<Versionbean>(context) {
                  @Override
                  public void onSuccess(Versionbean versionbean) {

                  }

                  @Override
                  public void onNext(Versionbean versionbean) {
                      if(versionbean!=null){
                          try {
                              Log.d(TAG, "检查新版本: " + versionbean);
                              ACache.getInstance().put(Constants.CACHEE_VERSION, versionbean);
                              EventBusBean bean = new EventBusBean();
                              bean.setKind(EventKind.EVENT_VERSION_SYCN);
                              EventBus.getDefault().post(bean);
                          } catch (Exception e) {
                              e.printStackTrace();
                          } finally {
                          }
                      }else{
                          Log.i(TAG, "onNext: "+"数据有误");
                      }

                  }

                  @Override
                  public void onError(Throwable e) {
                      Log.i(TAG, "onError: "+e.getMessage());
//                      Log.d(TAG, "异常错误");
//                      EventBusBean bean = new EventBusBean();
//                      bean.setKind(EventKind.EVENT_VERSION_SYCN);
//                      EventBus.getDefault().post(bean);
                  }
              });
  }
}
