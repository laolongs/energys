package cn.tties.energy.base;

/**
 *
 *
 * 定义两个方法  一个绑定view  一个解除绑定
 */

public class BasePresenter<T extends BaseView> {

    private T mT;

    public void attachView(T t){
        this.mT=t;
    }
    public T getView(){
        return mT;
    }

    public void detachView() {
        if (mT != null) {
            mT = null;
        }
    }

}
