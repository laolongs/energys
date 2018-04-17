package cn.tties.energy.view.iview;


import cn.tties.energy.base.BaseView;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.Data_CurrentPressbean;
import cn.tties.energy.model.result.Data_Currentbean;

/**
 * Created by li on 2018/4/4
 * description：
 * author：guojlli
 */

public interface IData_CurrentView extends BaseView {
    public void setData_CurrentData(Data_Currentbean bean);
    public void setData_CurrentPressData(Data_CurrentPressbean bean);
    public void setAllElectricity(AllElectricitybean allElectricitybean);
}
