package cn.tties.energy.view.iview;


import cn.tties.energy.base.BaseView;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.Data_Electricbean;

/**
 * Created by li on 2018/3/23
 * description：
 * author：guojlli
 */

public interface IData_ElectricView extends BaseView {
    public void setData_ElectricData(Data_Electricbean bean);
    public void setAllElectricity(AllElectricitybean allElectricitybean);
}
