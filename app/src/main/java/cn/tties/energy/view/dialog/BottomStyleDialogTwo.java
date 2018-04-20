package cn.tties.energy.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;

import cn.tties.energy.R;
import cn.tties.energy.model.result.AllElectricitybean;
import cn.tties.energy.view.adapter.StyleAdapter;

/**
 * Created by li on 2018/3/28
 * description：总电量弹出框（从底部）
 * author：guojlli
 */

public class BottomStyleDialogTwo extends Dialog implements AdapterView.OnItemClickListener {
    OnCliekAllElectricitytwo listener;
    int postion=0;
    AllElectricitybean allElectricitybean;
    private ListView mLv;
    private WheelView mWv;
    private TextView cancel;
    private TextView confrim;
    int postions=0;

    private StyleAdapter mAdapter;
    public void setCliekAllElectricity(OnCliekAllElectricitytwo listener){
        this.listener=listener;
    }
    public BottomStyleDialogTwo(Context context, AllElectricitybean allElectricitybean) {
        // 在构造方法里, 传入主题
        super(context, R.style.BottomDialogStyle);
        this.allElectricitybean=allElectricitybean;
        // 拿到Dialog的Window, 修改Window的属性
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_dialog);
        initView();
        initData();
    }

//    private void initView() {
//        mLv = (ListView) findViewById(R.id.lv_view_dialog);
//        mLv.setOnItemClickListener(this);
//    }
private void initView() {
    mWv = (WheelView) findViewById(R.id.common_wheelview);
    cancel = (TextView) findViewById(R.id.item_allele_cancel);
    confrim = (TextView) findViewById(R.id.item_allele_confirm);
//        mLv.setOnItemClickListener(this);
}
    private void initData() {
        mWv.setWheelAdapter(new StyleAdapter(getContext()));
        mWv.setWheelSize(5);
        mWv.setSkin(WheelView.Skin.Holo);
        mWv.setWheelData(createArrays());
        mWv.setSelection(2);
        mWv.setLoop(true);
        mWv.setWheelClickable(true);
        mWv.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                postions=position;
            }
        });
//        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
//        style.backgroundColor = Color.YELLOW;
//        style.textColor = Color.DKGRAY;
//        style.selectedTextColor = Color.GREEN;
//        mWv.setStyle(style);

//        mWv.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
//            @Override
//            public void onItemClick(int position, Object o) {
//                WheelUtils.log("click:" + position);
//            }
//        });
//        mWv.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<WheelData>() {
//            @Override
//            public void onItemSelected(int position, WheelData data) {
////                WheelUtils.log("selected:" + position);
//                postions = position;
//
//            }
//        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnCliekAllElectricityListener(postions);
                dismiss();
            }
        });
    }
    private ArrayList<String> createArrays() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i <allElectricitybean.getMeterList().size() ; i++) {
            list.add(allElectricitybean.getMeterList().get(i).getMeterName()+"");
        }
        return list;
    }
//    private void initData() {
//        // 填充数据集合
//        mAdapter = new StyleAdapter(getContext(), allElectricitybean);
//        mLv.setAdapter(mAdapter);
////        View inflate = View.inflate(getContext(), R.layout.item_dialog, null);
////        TextView viewhead = inflate.findViewById(R.id.tv_item_dialog_name);
////        viewhead.setText(allElectricitybean.getLedgerName());
////        mLv.addHeaderView(inflate);
//        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                listener.OnCliekAllElectricityListener(i);
//                dismiss();
//            }
//        });
//
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.notifyDataSetChanged();
    }
    public interface OnCliekAllElectricitytwo{
        void OnCliekAllElectricityListener(int poaiton);
    }
}