package cn.tties.energy.model.IModel;

import cn.tties.energy.api.Api;
import cn.tties.energy.api.RetrofitApi;
import cn.tties.energy.api.RetrofitApiOps;

/**
 * Created by li on 2018/4/5
 * description：
 * author：guojlli
 */

public class EnergyFragmentModel implements IEnergyFragmentModel {
    @Override
    public Api getEnergyFragmentData() {
        return RetrofitApi.getServer();
    }
}
