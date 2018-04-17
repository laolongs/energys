package cn.tties.energy.view.iview;


import cn.tties.energy.base.BaseView;
import cn.tties.energy.model.result.Energy_Monthlybean;

/**
 * Created by li on 2018/4/5
 * description：
 * author：guojlli
 */

public interface IEnergy_MonthlyView extends BaseView {
    public void setEnergy_MonthlyData(Energy_Monthlybean bean);
}
