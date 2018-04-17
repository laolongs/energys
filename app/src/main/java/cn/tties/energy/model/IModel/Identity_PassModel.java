package cn.tties.energy.model.IModel;

import cn.tties.energy.api.Api;
import cn.tties.energy.api.RetrofitApi;
import cn.tties.energy.api.RetrofitApiOps;

/**
 * Created by li on 2018/4/6
 * description：
 * author：guojlli
 */

public class Identity_PassModel implements IIdentity_PassModel {
    @Override
    public Api getIdentity_PassData() {
        return RetrofitApi.getServer();
    }
}
