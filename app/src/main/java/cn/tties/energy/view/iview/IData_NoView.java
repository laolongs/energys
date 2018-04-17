package cn.tties.energy.view.iview;


import cn.tties.energy.base.BaseView;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.Data_Nobean;

/**
 * Created by li on 2018/3/23
 * description：
 * author：guojlli
 */

public interface IData_NoView extends BaseView {
    public void setData_NoData(Data_Nobean bean);
    public void setAllElectricity(AllElectricitybean allElectricitybean);
}
