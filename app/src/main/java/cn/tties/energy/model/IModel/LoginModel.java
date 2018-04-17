package cn.tties.energy.model.IModel;

import cn.tties.energy.api.Api;
import cn.tties.energy.api.RetrofitApi;
import cn.tties.energy.api.RetrofitApiOps;

/**
 * Created by li on 2018/3/22
 * description：
 * author：guojlli
 */

public class LoginModel implements ILoginModel {
    @Override
    public Api getloginData() {
        return RetrofitApi.getServer();
    }

    @Override
    public Api getOpsLoginData() {
        return RetrofitApiOps.getServer();
    }

}
