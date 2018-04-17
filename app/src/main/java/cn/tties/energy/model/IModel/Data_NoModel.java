package cn.tties.energy.model.IModel;

import cn.tties.energy.api.Api;
import cn.tties.energy.api.RetrofitApi;
/**
 * Created by li on 2018/3/27
 * description：
 * author：guojlli
 */

public class Data_NoModel implements IData_NoModel {
    @Override
    public Api getData_NoData() {
        return RetrofitApi.getServer();
    }
    public Api getAllElectricity(){
        return RetrofitApi.getServer();
    }

}
