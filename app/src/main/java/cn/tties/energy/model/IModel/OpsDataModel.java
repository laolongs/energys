package cn.tties.energy.model.IModel;

import cn.tties.energy.api.Api;
import cn.tties.energy.api.RetrofitApi;
import cn.tties.energy.api.RetrofitApiOps;

/**
 * Created by li on 2018/3/23
 * description：
 * author：guojlli
 */

public class OpsDataModel implements IOpsModel {
    @Override
    public Api getOpsData() {
        return RetrofitApiOps.getServer();
    }

//    CallBack callBack;
//    List<String> list=new ArrayList<>();
//    public void getOpsitemRight(CallBack callBack){
//        for (int i = 0; i <20; i++) {
//            list.add("落红不是无情物");
//        }
//        callBack.getrightArray(list);
//    }
//
//    @Override
//    public Api getOpsData() {
//        return null;
//    }
//
//    public interface CallBack{
//        void getrightArray(List<String> list);
//
//    }
}
