package cn.tties.energy.model.IModel;

import cn.tties.energy.api.Api;
import cn.tties.energy.api.RetrofitApi;
import cn.tties.energy.api.RetrofitApiOps;

/**
 * Created by li on 2018/4/6
 * description：
 * author：guojlli
 */

public class Energy_MonthlyModel implements IEnergy_MonthlyModel {
    @Override
    public Api getEnergy_MonthlyData() {
        return RetrofitApiOps.getServer();
    }
}
