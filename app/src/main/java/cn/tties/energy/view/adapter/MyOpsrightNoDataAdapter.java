package cn.tties.energy.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tties.energy.R;
import cn.tties.energy.model.result.Opsbean;


/**
 * Created by li on 2018/3/22
 * description：
 * author：guojlli
 */

public class MyOpsrightNoDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    LayoutInflater inflater;
    List<String> listhead = new ArrayList<>();
    Opsbean.ResultBean opsbean;

    public MyOpsrightNoDataAdapter(Context context,Opsbean.ResultBean opsbean) {
        listhead.add("1");
        inflater = LayoutInflater.from(context);
        this.opsbean = opsbean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyNoQuestionViewHoder(inflater.inflate(R.layout.activity_ops_item_right_no, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyNoQuestionViewHoder) {
            ((MyNoQuestionViewHoder) holder).opsItemRightNoTv.setText("全部设备运转良好，请继续保持！");
        }

    }


    @Override
    public int getItemCount() {
        return listhead != null ? listhead.size() : 0;
    }

    public class MyNoQuestionViewHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.ops_item_right_no_tv)
        TextView opsItemRightNoTv;

        public MyNoQuestionViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
