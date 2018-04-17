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

public class Questions_discussAdapter extends RecyclerView.Adapter<Questions_discussAdapter.MydiscussViewHolder> {

    Context context;
    List<Opsbean.ResultBean.QuestionListBean.AdviceListBean> bean;


    public Questions_discussAdapter(Context context, List<Opsbean.ResultBean.QuestionListBean.AdviceListBean> bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public MydiscussViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MydiscussViewHolder(View.inflate(context, R.layout.fragment_questions_discuss_item, null));
    }

    @Override
    public void onBindViewHolder(MydiscussViewHolder holder, int position) {
        holder.discussContent.setText(bean.get(position).getContent());
        holder.discussTime.setText(bean.get(position).getCreateTime());
        holder.discussName.setText(bean.get(position).getMbStaff().getStaffName());
        String str=new String(bean.get(position).getMbStaff().getStaffName());
        String substring = str.substring(0, 1);
        holder.discussLogo.setText(substring);
    }

    @Override
    public int getItemCount() {
        return bean != null ? bean.size() : 0;
    }

    public class MydiscussViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.discuss_logo)
        TextView discussLogo;
        @BindView(R.id.discuss_name)
        TextView discussName;
        @BindView(R.id.discuss_time)
        TextView discussTime;
        @BindView(R.id.discuss_content)
        TextView discussContent;
        public MydiscussViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
