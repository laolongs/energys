package cn.tties.energy.presenter;

import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import cn.tties.energy.base.BasePresenter;
import cn.tties.energy.common.Constants;
import cn.tties.energy.model.IModel.OpsDataModel;
import cn.tties.energy.model.IModel.QuestionsModel;
import cn.tties.energy.model.result.Discussbean;
import cn.tties.energy.model.result.OpsLoginbean;
import cn.tties.energy.model.result.Opsbean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.view.iview.IOpsView;
import cn.tties.energy.view.iview.IQuestionsView;

/**
 * Created by li on 2018/3/23
 * description：
 * author：guojlli
 */

public class QuestionsPresenter extends BasePresenter<IQuestionsView> {
    private static final String TAG = "QuestionsPresenter";
    IQuestionsView view;
    QuestionsModel model;

    public QuestionsPresenter(IQuestionsView view) {
        this.view = view;
        this.model = new QuestionsModel();
    }
    public void getQuestion(String questionId){
        String companyid = ACache.getInstance().getAsString(Constants.CACHE_OPS_COMPANDID);
        HashMap<String,Object> map=new HashMap<>();
        map.put("companyId",companyid);
        map.put("patrolType",0);//类型
        map.put("pagesize",10);
        map.put("pagenum",1);
        map.put("questionId",questionId);
        Log.i(TAG, "getQuestion: "+companyid);
        model.getQuestionsData().getOpsQuertion(map).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Opsbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Opsbean value) {
                        if(value!=null&&value.getErrorCode()==0){
                            Log.i(TAG, "onNext: "+"有数据");
                            view.setQuestionData(value);
                        }else{
                            Log.i(TAG, "onNext: "+"数据有误");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getDiscuss(String questionId,String content){
        OpsLoginbean opsLoginbean = ACache.getInstance().getAsObject(Constants.CACHE_OPSLOGIN_USERINFO);
        int staffId = opsLoginbean.getResult().getMaintUser().getStaffId();
        HashMap<String,Object> map=new HashMap<>();
        map.put("questionId",questionId);//对应的问题id
        map.put("staffId",staffId);//运维登录身份id
        map.put("content",content);
        model.getQuestionsData().getdiscuss(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Discussbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Discussbean value) {
                        if(value!=null&&value.getErrorCode()==0){
                            Log.i(TAG, "onNextDiscussbean: "+"有数据");
                            view.setQuestionDiscussData(value);
                        }else{
                            Log.i(TAG, "onNextDiscussbean: "+"数据有误");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
