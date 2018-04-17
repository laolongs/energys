package cn.tties.energy.model.IModel;


import cn.tties.energy.api.Api;
import cn.tties.energy.api.RetrofitApi;

/**
 * Created by li on 2018/4/3
 * description：
 * author：guojlli
 */

public class Data_ElectricModel implements IData_ElectricModel {
    @Override
    public Api getData_ElectricData() {
        return RetrofitApi.getServer();
    }

    @Override
    public Api getAllElectricity() {
        return RetrofitApi.getServer();
    }
}
