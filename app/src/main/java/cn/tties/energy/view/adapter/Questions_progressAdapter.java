package cn.tties.energy.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tties.energy.R;
import cn.tties.energy.model.result.Opsbean;

/**
 * Created by li on 2018/4/2
 * description：
 * author：guojlli
 */

public class Questions_progressAdapter extends RecyclerView.Adapter<Questions_progressAdapter.MyprogressViewHolder> {

    Context context;
    List<Opsbean.ResultBean.QuestionListBean.ScheduleListBean> bean;


    public Questions_progressAdapter(Context context, List<Opsbean.ResultBean.QuestionListBean.ScheduleListBean> bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public MyprogressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyprogressViewHolder(View.inflate(context, R.layout.fragment_questions_progress_item, null));
    }

    @Override
    public void onBindViewHolder(MyprogressViewHolder holder, int position) {
        holder.progressTime.setText(bean.get(position).getCreateTime());
        holder.progressTitle.setText(bean.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return bean!=null?bean.size():0;
    }

    public class MyprogressViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progress_title)
        TextView progressTitle;
        @BindView(R.id.progress_time)
        TextView progressTime;
        public MyprogressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
