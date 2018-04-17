package cn.tties.energy.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class SycnService extends Service {

    private static final String TAG = "SycnService";

    public static final String SYCN_KIND = "SYCN_KIND";


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "后台数据同步服务启动");
        String kind = intent.getStringExtra(SYCN_KIND);
        switch (kind) {
            case SYCN_KIND:
//                sycnMeter();
                break;
        }
        return START_STICKY;
    }

//    private void sycnMeter() {
//        Log.d(TAG, "同步分户数据");
//        MeterParams params = new MeterParams();
//        params.setObjType(MeterParams.TYPE_METER);
//        final LoginResult userInfo = MyApplication.getUserInfo();
//        params.setObjId(userInfo.getLedgerId());
//        Callback.CommonCallback<String> callback = new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                try {
//                    Log.d(TAG, "返回数据: " + result);
//                    MeterResult ret = JsonUtils.deserialize(result, MeterResult.class);
//                    DbManager db = x.getDb(MyApplication.getDaoConfig());
//                    db.dropTable(MeterEntity.class);
//                    for (MeterResult.Meter meter : ret.getMeterList()) {
//                        MeterEntity entity = new MeterEntity();
//                        entity.setMeterId(meter.getMeterId());
//                        entity.setMeterName(meter.getMeterName());
//                        entity.setLedgerId(ret.getLedgerId());
//                        db.save(entity);
//                    }
//                    List<MeterEntity> list = db.findAll(MeterEntity.class);
//                    for (MeterEntity meter : list) {
//                        System.out.println(meter.getMeterName() + " " + meter.getMeterId());
//                    }
//
//                    Log.d(TAG, "同步分户数据成功");
//                    EventBusBean bean = new EventBusBean();
//                    bean.setKind(EventKind.EVENT_METER_SYCN);
//                    EventBus.getDefault().post(bean);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    stopSelf();//结束服务
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                Log.d(TAG, "异常错误");
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
//    }

}