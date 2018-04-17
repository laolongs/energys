package cn.tties.energy.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.tties.energy.R;
import cn.tties.energy.base.BaseFragment;
import cn.tties.energy.model.result.Opsbean;
import cn.tties.energy.presenter.QuestionsDiscussPresenter;
import cn.tties.energy.view.adapter.Questions_progressAdapter;
import cn.tties.energy.view.iview.IQuestionDiscussVIew;

/**
 * Created by li on 2018/3/23
 * description：维修进度
 * author：guojlli
 */

public class Questions_progressFragment extends BaseFragment<QuestionsDiscussPresenter> implements IQuestionDiscussVIew {
//    @BindView(R.id.qu_tb_pr_rcy)
    RecyclerView quTbPrRcy;
    Unbinder unbinder;
    public String questionId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View inflate = View.inflate(getActivity(), R.layout.fragment_questions_progress, null);
        quTbPrRcy=inflate.findViewById(R.id.qu_tb_pr_rcy);
        unbinder = ButterKnife.bind(this, inflate);
        initView();
        return inflate;
    }

    @Override
    protected void createPresenter() {
        mPresenter=new QuestionsDiscussPresenter(this);
    }

    private void initView() {
        mPresenter.getQuestionRefresh(getQuestionId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setQuestionRefreshData(Opsbean bean) {
        if(bean.getResult().getQuestionList().size()>0){
            List<Opsbean.ResultBean.QuestionListBean.ScheduleListBean> scheduleList = bean.getResult().getQuestionList().get(0).getScheduleList();
            quTbPrRcy.setLayoutManager(new LinearLayoutManager(getActivity()));
            Questions_progressAdapter adapter=new Questions_progressAdapter(getActivity(),scheduleList);
            quTbPrRcy.setAdapter(adapter);
        }

    }
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
