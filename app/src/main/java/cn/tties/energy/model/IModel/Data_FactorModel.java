package cn.tties.energy.model.IModel;

import cn.tties.energy.api.Api;
import cn.tties.energy.api.RetrofitApi;
/**
 * Created by li on 2018/4/4
 * description：
 * author：guojlli
 */

public class Data_FactorModel implements IData_FactorModel {
    @Override
    public Api getData_FactorData() {
        return RetrofitApi.getServer();
    }

    @Override
    public Api getAllElectricity() {
        return RetrofitApi.getServer();
    }
}
