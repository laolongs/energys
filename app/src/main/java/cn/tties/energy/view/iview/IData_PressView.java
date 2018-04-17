package cn.tties.energy.view.iview;

import java.util.List;

import cn.tties.energy.base.BaseView;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.Data_Pressbean;

/**
 * Created by li on 2018/3/23
 * description：
 * author：guojlli
 */

public interface IData_PressView extends BaseView {
    public void setData_PressData(Data_Pressbean bean);
    public void setAllElectricity(AllElectricitybean allElectricitybean);
}
