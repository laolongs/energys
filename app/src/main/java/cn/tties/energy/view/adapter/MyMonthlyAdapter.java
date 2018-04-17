package cn.tties.energy.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tties.energy.R;
import cn.tties.energy.application.MyApplication;
import cn.tties.energy.model.result.Energy_Monthlybean;
import cn.tties.energy.utils.ShareUtils;
import cn.tties.energy.wxapi.Defaultcontent;

/**
 * Created by li on 2018/4/6
 * description：
 * author：guojlli
 */

public class MyMonthlyAdapter extends RecyclerView.Adapter<MyMonthlyAdapter.ViewHolder> {
    public Context context;
    public Energy_Monthlybean bean;
    public LayoutInflater inflater;

    public MyMonthlyAdapter(Context context, Energy_Monthlybean bean) {
        this.context = context;
        this.bean = bean;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.energy_energy_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.energyMonthlyTv.setText(bean.getResult().get(position).getReportName());
        holder.energyMonthlyTime.setText(bean.getResult().get(position).getCreateTime());
        holder.energyMonthlyPdfimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.energyMonthlyLoad.setVisibility(View.VISIBLE);

            }
        });
        holder.energyMonthlyLoadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ShareUtils.shareWeb((Activity) context, Defaultcontent.url, Defaultcontent.title
//                        , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.icon_logo_share, SHARE_MEDIA.WEIXIN
//                );
            }
        });

    }

    @Override
    public int getItemCount() {
        return bean != null ? bean.getResult().size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.energy_monthly_pdfimg)
        ImageView energyMonthlyPdfimg;
        @BindView(R.id.energy_monthly_tv)
        TextView energyMonthlyTv;
        @BindView(R.id.energy_monthly_time)
        TextView energyMonthlyTime;
        @BindView(R.id.energy_monthly_loadimg)
        ImageView energyMonthlyLoadimg;
        @BindView(R.id.energy_monthly_load)
        TextView energyMonthlyLoad;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
