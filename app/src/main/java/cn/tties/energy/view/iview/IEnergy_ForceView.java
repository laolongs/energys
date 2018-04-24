package cn.tties.energy.view.iview;


import cn.tties.energy.base.BaseView;
import cn.tties.energy.model.result.DataFragmentbean;
import cn.tties.energy.model.result.Databean;

/**
 * Created by li on 2018/4/5
 * description：
 * author：guojlli
 */

public interface IEnergy_ForceView extends BaseView {
    public void setEnergy_ForceData(Databean bean);
    public void setEnergy_ForceChartData(Databean bean);
    public void setEnergy_ForceCharge(Databean bean);
}
