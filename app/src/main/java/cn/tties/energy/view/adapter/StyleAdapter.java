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

public class StyleAdapter extends BaseWheelAdapter<AllElectricitybean.MeterListBean> {

    private Context mContext;
    AllElectricitybean allElectricitybean;
//    public StyleAdapter(Context context, AllElectricitybean allElectricitybean) {
//        mContext = context;
//        this.allElectricitybean=allElectricitybean;
//    }
    public StyleAdapter() {
        mContext = MyApplication.getInstance();

    }

//
//    @Override
//    public int getCount() {
//        return allElectricitybean!=null?allElectricitybean.getMeterList().size():0;
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return allElectricitybean.getMeterList().get(i);
//    }
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//            ViewHoldertwo holder;
//            if (convertView == null) {
//                convertView = View.inflate(mContext, R.layout.item_dialog, null);
//                holder = new ViewHoldertwo();
//                holder.tvNametwo = (TextView) convertView.findViewById(R.id.tv_item_dialog_name);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHoldertwo) convertView.getTag();
//            }
//            holder.tvNametwo.setText(allElectricitybean.getMeterList().get(position).getMeterName());
//            return convertView;
//
//
//    }


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
        holder.tvNametwo.setText(mList.get(position)+"");
        return convertView;

    }
    static class ViewHoldertwo {
        TextView tvNametwo;
    }
}
