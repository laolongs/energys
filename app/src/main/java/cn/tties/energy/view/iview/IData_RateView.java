package cn.tties.energy.view.iview;


import cn.tties.energy.base.BaseView;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.Data_HaveKwbean;
import cn.tties.energy.model.result.Data_NoKvarbean;

/**
 * Created by li on 2018/4/4
 * description：
 * author：guojlli
 */

public interface IData_RateView extends BaseView {
    public void setData_HaveKWData(Data_HaveKwbean data_haveKwbean);
    public void setData_NoKvarData(Data_NoKvarbean data_noKvarbean);
    public void setAllElectricity(AllElectricitybean allElectricitybean);
}
