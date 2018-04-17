package cn.tties.energy.model.httputils;



import cn.tties.energy.api.ObserverApi;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by wpz on 2017/5/17.
 */

public class HttpUtils {

    //请求网络
    public static<T> void getData(Observable<T>  observable,ObserverApi<T> observer){

        observable.observeOn(AndroidSchedulers.mainThread())    //在主线程处理数据
                .subscribeOn(Schedulers.io())                   //在子线程请求数据
                .subscribe(observer);
    }

}
