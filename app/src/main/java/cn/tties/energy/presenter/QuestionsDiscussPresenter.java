package cn.tties.energy.presenter;

import android.util.Log;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import cn.tties.energy.base.BasePresenter;
import cn.tties.energy.common.Constants;
import cn.tties.energy.model.IModel.QuestionsModel;
import cn.tties.energy.model.result.Discussbean;
import cn.tties.energy.model.result.OpsLoginbean;
import cn.tties.energy.model.result.Opsbean;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.view.iview.IQuestionDiscussVIew;
import cn.tties.energy.view.iview.IQuestionsView;

/**
 * Created by li on 2018/3/23
 * description：
 * author：guojlli
 */

public class QuestionsDiscussPresenter extends BasePresenter<IQuestionDiscussVIew> {
    private static final String TAG = "QuestionsPresenter";
    IQuestionDiscussVIew view;
    QuestionsModel model;

    public QuestionsDiscussPresenter(IQuestionDiscussVIew view) {
        this.view = view;
        this.model = new QuestionsModel();
    }
    //刷新
    public void getQuestionRefresh(String questionId){
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
                            view.setQuestionRefreshData(value);
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

}
