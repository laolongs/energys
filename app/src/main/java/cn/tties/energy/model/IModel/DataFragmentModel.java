package cn.tties.energy.model.IModel;

import cn.tties.energy.api.Api;
import cn.tties.energy.api.RetrofitApi;

/**
 * Created by li on 2018/4/5
 * description：电力数据
 * author：guojlli
 */

public class DataFragmentModel implements IDataFragmentModel {
    @Override
    public Api getDataFragmentData() {
        return RetrofitApi.getServer();
    }
}
