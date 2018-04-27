package cn.tties.energy.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.tties.energy.R;
import cn.tties.energy.application.MyApplication;
import cn.tties.energy.model.result.Opsbean;
import cn.tties.energy.utils.ToastUtil;


/**
 * Created by li on 2018/3/22
 * description：
 * author：guojlli
 */

public class MyOpsrightNoDataAdapter extends RecyclerView.Adapter<MyOpsrightNoDataAdapter.MyNoQuestionViewHoder> {
    private static final String TAG = "MyOpsrightNoDataAdapter";
    LayoutInflater inflater;
    List<String> listhead = new ArrayList<>();
    Context context;
    public MyOpsrightNoDataAdapter() {
        this.context= MyApplication.getInstance();
        listhead.add("1");
        inflater = LayoutInflater.from(context);

    }

    @Override
    public MyNoQuestionViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyNoQuestionViewHoder(inflater.inflate(R.layout.activity_ops_item_right_nodata, parent, false));
    }

    @Override
    public void onBindViewHolder(MyNoQuestionViewHoder holder, int position) {
        holder.opsItemRightNoTv.setText("全部设备运转良好，请继续保持！");
    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        if (holder instanceof MyNoQuestionViewHoder) {
//            ((MyNoQuestionViewHoder) holder).opsItemRightNoTv.setText("全部设备运转良好，请继续保持！");
//
//        }
//    }


    @Override
    public int getItemCount() {
        return listhead != null ? listhead.size() : 0;
    }

    public class MyNoQuestionViewHoder extends RecyclerView.ViewHolder {
        TextView opsItemRightNoTv;

        public MyNoQuestionViewHoder(View itemView) {
            super(itemView);
            opsItemRightNoTv = (TextView) itemView.findViewById(R.id.ops_item_right_nodata_tv);

        }
    }


}
