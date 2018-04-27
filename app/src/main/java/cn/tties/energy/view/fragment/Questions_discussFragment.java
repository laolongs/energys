package cn.tties.energy.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.tties.energy.R;
import cn.tties.energy.base.BaseFragment;
import cn.tties.energy.model.result.Opsbean;
import cn.tties.energy.presenter.QuestionsDiscussPresenter;
import cn.tties.energy.presenter.QuestionsPresenter;
import cn.tties.energy.view.adapter.Questions_discussAdapter;
import cn.tties.energy.view.iview.IQuestionDiscussVIew;

/**
 * Created by li on 2018/3/23
 * description：问题讨论
 * author：guojlli
 */

public class Questions_discussFragment extends BaseFragment<QuestionsDiscussPresenter> implements IQuestionDiscussVIew {
    private static final String TAG = "Questions_discussFragme";
    RecyclerView quTbDsRcy;
    private Questions_discussAdapter adapter;
    public String questionId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View inflate = View.inflate(getActivity(), R.layout.fragment_questions_discuss, null);
        initFindView(inflate);
        initData();
        return inflate;
    }

    private void initFindView(View inflate) {
        quTbDsRcy=inflate.findViewById(R.id.qu_tb_ds_rcy);
    }

    @Override
    protected void createPresenter() {
        mPresenter=new QuestionsDiscussPresenter(this);
    }

    public void initData() {
        int i=1;
        Log.i(TAG, "initData: "+i++);
        mPresenter.getQuestionRefresh(getQuestionId());
    }

    @Override
    public void setQuestionRefreshData(Opsbean bean) {
        if(bean.getResult().getQuestionList().size()>0){
            Opsbean.ResultBean.QuestionListBean questionListBean = bean.getResult().getQuestionList().get(0);
            quTbDsRcy.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new Questions_discussAdapter(questionListBean.getAdviceList());
            quTbDsRcy.setAdapter(adapter);
        }

    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
