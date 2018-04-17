package cn.tties.energy.view.iview;


import cn.tties.energy.base.BaseView;
import cn.tties.energy.model.result.OpsLoginbean;

/**
 * Created by li on 2018/3/22
 * description：
 * author：guojlli
 */

public interface ILoginView extends BaseView {
    public String getLoginName();
    public String getLoginPass();
    public void getOpsLoginData(OpsLoginbean opsLoginbean);
}
