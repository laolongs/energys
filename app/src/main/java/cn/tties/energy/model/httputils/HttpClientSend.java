package cn.tties.energy.model.httputils;



import okhttp3.OkHttpClient;

/**
 * 登录
 * author chensi
 */
public class HttpClientSend {

    public void send(ClinetRequestParams params,OkUiCallback callback) {
//        x.http().post(params.getParams(), callback);
        params.getParams().enqueue(callback);
    }
}
