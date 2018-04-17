package cn.tties.energy.view.iview;


import cn.tties.energy.base.BaseView;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.Data_Factorbean;

/**
 * Created by li on 2018/4/4
 * description：
 * author：guojlli
 */

public interface IData_FactorView extends BaseView {
    public void setData_FactorData(Data_Factorbean bean);
    public void setAllElectricity(AllElectricitybean allElectricitybean);
}
