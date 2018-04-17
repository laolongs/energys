package cn.tties.energy.view.iview;


import cn.tties.energy.base.BaseView;
import cn.tties.energy.model.result.Discussbean;
import cn.tties.energy.model.result.Opsbean;

/**
 * Created by li on 2018/3/23
 * description：
 * author：guojlli
 */

public interface IQuestionsView extends BaseView {
    public void setQuestionData(Opsbean bean);
    public void setQuestionDiscussData(Discussbean discussbean);
}
