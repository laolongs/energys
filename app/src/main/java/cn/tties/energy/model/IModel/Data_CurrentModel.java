package cn.tties.energy.model.IModel;


import cn.tties.energy.api.Api;
import cn.tties.energy.api.RetrofitApi;

/**
 * Created by li on 2018/4/4
 * description：
 * author：guojlli
 */

public class Data_CurrentModel implements IData_CurrentModel {
    @Override
    public Api getData_CurrentData() {
        return RetrofitApi.getServer();
    }

    @Override
    public Api getData_CurrentPressData() {
        return RetrofitApi.getServer();
    }

    @Override
    public Api getAllElectricity() {
        return RetrofitApi.getServer();
    }
}
