package cn.tties.energy.model.httputils;

import android.os.Handler;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by li on 2018/3/13
 * description：
 * author：guojlli
 */

public class OkHttpUtils {
    public HashMap<String,Object> map=new HashMap<>();
    //实例一个在ui的handler
    private Handler handler=new Handler();
    public Handler getHandler(){
        return  handler;
    }
    //单例
    private static OkHttpUtils okHttpUtils=new OkHttpUtils();
    private OkHttpUtils(){};
    public static OkHttpUtils getInstance(){
        return okHttpUtils;
    }
    private OkHttpClient client;
    private void initOkhttpClient(){
        if(client==null){
            client=new OkHttpClient.Builder().build();
        }
    }
    //公用的get请求方法，完成的各功能不确定
    public void doGet(String url, Callback callback){
        initOkhttpClient();
        Request request=new Request.Builder().url(url).build();
        Call call=client.newCall(request);
        call.enqueue(callback);
    }
    //公用的post请求方法，完成的各功能不确定
    public void doPost(String url,HashMap<String,String> map,Callback callback){
        initOkhttpClient();
        FormBody.Builder body=new FormBody.Builder();
        for (String key:map.keySet()) {
            body.add(key,map.get(key));
        }
        Request request=new Request.Builder().url(url).post(body.build()).build();
        Call call=client.newCall(request);
        call.enqueue(callback);
    }
    //公用的post请求方法，完成的各功能不确定
    public Call getCall(String url,HashMap<String,Object> map){
        initOkhttpClient();
        FormBody.Builder body=new FormBody.Builder();
        for (String key:map.keySet()) {
            body.add(key,map.get(key)+"");
        }
        Request request=new Request.Builder().url(url).post(body.build()).build();
        Call call=client.newCall(request);
        return call;
    }
}
