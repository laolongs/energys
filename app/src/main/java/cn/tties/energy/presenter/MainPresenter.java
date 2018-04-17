package cn.tties.energy.presenter;

import android.content.Context;
import android.view.View;

import java.lang.ref.SoftReference;
import java.util.List;

import cn.tties.energy.base.BasePresenter;
import cn.tties.energy.model.IModel.IMainModel;
import cn.tties.energy.model.IModel.MainModel;
import cn.tties.energy.view.MainActivity;
import cn.tties.energy.view.iview.IMainView;


/**
 * mainpresenter
 */

public class MainPresenter extends BasePresenter<IMainView> {
    IMainView view;
    IMainModel mainModel;
    public MainPresenter(MainActivity view){
       this.view=view;
        mainModel=new MainModel();

    }
    public void showViewpageData(List<View> list){
        view.setViewPageData(list);
    }
}
