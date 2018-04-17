package cn.tties.energy.view.iview;


import cn.tties.energy.base.BaseView;
import cn.tties.energy.model.result.OpsLoginbean;

/**
 * Created by li on 2018/3/30
 * description：
 * author：guojlli
 */

public interface IIdentityFragmentView extends BaseView {
    public void getOpsLoginData(OpsLoginbean opsLoginbean);
}
