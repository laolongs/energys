package cn.tties.energy.api;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import cn.tties.energy.common.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 *
 *
 * 将Retrofit封装起来，返回Api
 */

public class RetrofitApiPub {

    static class RetrofitInstance {
        private static OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new ReadCookiesInterceptor()).addInterceptor(new SaveCookiesInterceptor()).build();
        private static Api api = new Retrofit.Builder()
                .baseUrl(Constants.PUBBASE_RUL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api.class);
    }

    //得到Server对象
    public static Api getServer() {
        return RetrofitInstance.api;
    }

}
