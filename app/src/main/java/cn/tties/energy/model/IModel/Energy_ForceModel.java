package cn.tties.energy.model.IModel;

import cn.tties.energy.api.Api;
import cn.tties.energy.api.RetrofitApi;

/**
 * Created by li on 2018/4/5
 * description：
 * author：guojlli
 */

public class Energy_ForceModel implements IEnergy_ForceModel {
    @Override
    public Api getEnergy_ForceData() {
        return RetrofitApi.getServer();
    }

    @Override
    public Api getEnergy_ForcechargeData() {
        return RetrofitApi.getServer();
    }
}
