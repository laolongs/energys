package cn.tties.energy.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wx.wheelview.adapter.BaseWheelAdapter;

import cn.tties.energy.R;
import cn.tties.energy.application.MyApplication;
import cn.tties.energy.model.result.AllElectricitybean;

/**
 * Created by li on 2018/3/28
 * description：
 * author：guojlli
 */

public class TimeWheelAdapter extends BaseWheelAdapter<Integer> {

    private Context mContext;
    public TimeWheelAdapter() {
        mContext = MyApplication.getInstance();

    }
    @Override
    protected View bindView(int position, View convertView, ViewGroup parent) {
        ViewHoldertwo holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_dialog, null);
            holder = new ViewHoldertwo();
            holder.tvNametwo = (TextView) convertView.findViewById(R.id.tv_item_dialog_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHoldertwo) convertView.getTag();
        }
        holder.tvNametwo.setText(mList.get(position)+"年");
        return convertView;

    }
    static class ViewHoldertwo {
        TextView tvNametwo;
    }
}
