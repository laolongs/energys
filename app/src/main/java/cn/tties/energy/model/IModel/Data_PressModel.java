package cn.tties.energy.model.IModel;

import cn.tties.energy.api.Api;
import cn.tties.energy.api.RetrofitApi;

/**
 * Created by li on 2018/3/27
 * description：
 * author：guojlli
 */

public class Data_PressModel implements IData_PressModel {
    @Override
    public Api getData_PressData() {
        return RetrofitApi.getServer();
    }

    @Override
    public Api getAllElectricity() {
        return RetrofitApi.getServer();
    }


}
