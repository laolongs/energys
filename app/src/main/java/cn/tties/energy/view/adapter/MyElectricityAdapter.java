package cn.tties.energy.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import io.reactivex.functions.IntFunction;
import cn.tties.energy.R;
import cn.tties.energy.common.Constants;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.model.result.OpsLoginbean;
import cn.tties.energy.utils.ACache;

/**
 * Created by li on 2018/3/28
 * description：
 * author：guojlli
 */

public class MyElectricityAdapter extends BaseAdapter {
    private Context mContext;
    OpsLoginbean bean;
    public MyElectricityAdapter(Context context, OpsLoginbean bean) {
        mContext = context;
        this.bean=bean;
    }
    @Override
    public int getCount() {
        return bean!=null?bean.getResult().getEnergyLedgerList().size():0;
    }

    @Override
    public Object getItem(int i) {
        return bean.getResult().getEnergyLedgerList().get(i);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_dialog_style, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.table_electrical_name);
            holder.check=convertView.findViewById(R.id.table_electrical_check);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final List<OpsLoginbean.ResultBean.EnergyLedgerListBean> energyLedgerList = bean.getResult().getEnergyLedgerList();
        String ischeck = ACache.getInstance().getAsString(Constants.CACHE_ISCHECK);
        energyLedgerList.get(Integer.parseInt(ischeck)).setIscheck(true);
        holder.check.setChecked(energyLedgerList.get(position).isIscheck());
        holder.tvName.setText(energyLedgerList.get(position).getEnergyLedgerId()+"");
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < energyLedgerList.size(); i++) {
                    if (i!=position){
                        energyLedgerList.get(i).setIscheck(false);
                    }else{
                        ACache.getInstance().put(Constants.CACHE_ISCHECK,position+"");
                        energyLedgerList.get(i).setIscheck(true);
                    }
                }
                notifyDataSetChanged();
            }


        });
        return convertView;
    }

    static class ViewHolder {
        CheckBox check;
        TextView tvName;
    }

}
