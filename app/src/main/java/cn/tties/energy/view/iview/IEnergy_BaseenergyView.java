package cn.tties.energy.view.iview;


import cn.tties.energy.base.BaseView;
import cn.tties.energy.model.result.Databean;
import cn.tties.energy.model.result.Energy_BasePlanbean;

/**
 * Created by li on 2018/4/5
 * description：
 * author：guojlli
 */

public interface IEnergy_BaseenergyView extends BaseView {
    public void setEnergy_BaseenergyData(Databean bean);
    public void setEnergy_BaseenergyYearData(Databean bean);
    public void setEnergy_BasePlanData(Energy_BasePlanbean bean);
}
