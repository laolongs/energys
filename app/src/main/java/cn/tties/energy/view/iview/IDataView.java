package cn.tties.energy.view.iview;


import cn.tties.energy.base.BaseView;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.Databean;

/**
 * Created by li on 2018/3/23
 * description：
 * author：guojlli
 */

public interface IDataView extends BaseView {
    public void setDataData(Databean bean);
    public void setDataChartData(Databean bean);
    public void setAllElectricity(AllElectricitybean allElectricitybean);
}
