package cn.tties.energy.model.IModel;

import cn.tties.energy.api.Api;

/**
 * Created by li on 2018/4/4
 * description：
 * author：guojlli
 */

public interface IData_RateModel {
    public Api getData_HaveKWData();
    public Api getData_NoKvarData();
    public Api getAllElectricity();
}