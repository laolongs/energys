package cn.tties.energy.model.IModel;

import cn.tties.energy.api.Api;
import cn.tties.energy.api.RetrofitApi;
import cn.tties.energy.api.RetrofitApiOps;

/**
 * Created by li on 2018/3/23
 * description：
 * author：guojlli
 */

public class QuestionsModel implements IQuestionsModel {
    @Override
    public Api getQuestionsData() {
        return RetrofitApiOps.getServer();
    }

    public interface CallBack{
        void getTabArray(String[] array);

    }
}
