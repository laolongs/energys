package cn.tties.energy.model.httputils;

import android.os.Handler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by li on 2018/3/13
 * description：
 * author：guojlli
 */

public abstract class OkUiCallback implements Callback {
    //给handler赋一个主线程的handler,然後这个handler的信息就可以拼接到主线程的handler里了
    private Handler handler=OkHttpUtils.getInstance().getHandler();
    //建两个方法，一个成功，一个失败，用于被子线程调用赋值
    public abstract void onFailed(Call call, IOException e);
    public abstract void onSuccess(String str)throws IOException;
    //数据请求失败
    @Override
    public void onFailure(final Call call,final IOException e) {
        //该方法就是把 线程post到handler所在的线程；
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailed(call,e);
            }
        });

    }
    //数据请求成功
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String str=response.body().string();
        //该方法就是把 线程post到handler所在的线程；
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    onSuccess(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
