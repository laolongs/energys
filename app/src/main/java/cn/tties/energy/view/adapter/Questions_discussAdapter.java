package cn.tties.energy.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.tties.energy.R;
import cn.tties.energy.application.MyApplication;
import cn.tties.energy.model.result.Opsbean;

/**
 * Created by li on 2018/4/2
 * description：
 * author：guojlli
 */

public class Questions_discussAdapter extends RecyclerView.Adapter<Questions_discussAdapter.MydiscussViewHolder> {

    Context context;
    List<Opsbean.ResultBean.QuestionListBean.AdviceListBean> bean;



    public Questions_discussAdapter( List<Opsbean.ResultBean.QuestionListBean.AdviceListBean> bean) {
        this.context = MyApplication.getInstance();
        this.bean = bean;
    }

    @Override
    public MydiscussViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MydiscussViewHolder(View.inflate(context, R.layout.fragment_questions_discuss_item, null));
    }

    @Override
    public void onBindViewHolder(MydiscussViewHolder holder, int position) {
        holder.discussGroup.setText("工程师一组");
        holder.discussContent.setText(bean.get(position).getContent());
        holder.discussTime.setText(bean.get(position).getCreateTime());
        holder.discussName.setText(bean.get(position).getMbStaff().getStaffName());
        String str = new String(bean.get(position).getMbStaff().getStaffName());
        String substring = str.substring(0, 1);
        holder.discussLogo.setText(substring);
    }

    @Override
    public int getItemCount() {
        return bean != null ? bean.size() : 0;
    }

    public class MydiscussViewHolder extends RecyclerView.ViewHolder {
        TextView discussLogo;
        TextView discussName;
        TextView discussTime;
        TextView discussContent;
        TextView discussGroup;
        public MydiscussViewHolder(View itemView) {
            super(itemView);
            discussLogo = (TextView) itemView.findViewById(R.id.discuss_logo);
            discussName = (TextView) itemView.findViewById(R.id.discuss_name);
            discussTime = (TextView) itemView.findViewById(R.id.discuss_time);
            discussContent = (TextView) itemView.findViewById(R.id.discuss_content);
            discussGroup = (TextView) itemView.findViewById(R.id.discuss_group);
        }
    }
}
