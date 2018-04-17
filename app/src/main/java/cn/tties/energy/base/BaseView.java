package cn.tties.energy.base;

/**
 *
 *
 * 所有的接口都继承这个接口
 */

public interface BaseView<T> {

    void onsuccess(T t);
    void onError(T t);
}
