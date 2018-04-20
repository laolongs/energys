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
import java.util.Date;

import cn.tties.energy.R;
import cn.tties.energy.utils.DateUtil;
import cn.tties.energy.view.adapter.StyleAdapter;
import cn.tties.energy.view.adapter.TimeWheelAdapter;
import cn.tties.energy.view.adapter.TimeWheelTwoAdapter;

/**
 * Created by li on 2018/4/7
 * description：
 * author：guojlli
 */

public class MyTimePickerWheelTwoDialog extends Dialog implements AdapterView.OnItemClickListener {
    private static final String TAG = "MyTimePickerWheelTwoDialog";
    OnCliekTime listener;
    private ListView mLv;
    private WheelView mWv;
    private TextView cancel;
    private TextView confrim;
    int postions=0;

    private TimeWheelTwoAdapter mAdapter;
    public void setOnCliekTime(OnCliekTime listener){
        this.listener=listener;
    }
    public MyTimePickerWheelTwoDialog(Context context) {
        // 在构造方法里, 传入主题
        super(context, R.style.BottomDialogStyle);
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

    private void initView() {
        mWv = (WheelView) findViewById(R.id.common_wheelview);
        cancel = (TextView) findViewById(R.id.item_allele_cancel);
        confrim = (TextView) findViewById(R.id.item_allele_confirm);

    }
    private void initData() {
        mWv.setWheelAdapter(new TimeWheelTwoAdapter(getContext()));
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
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnCliekTimeListener(postions);
                dismiss();
            }
        });
    }
    private ArrayList<String> createArrays() {
        ArrayList<String> list = new ArrayList<String>();
        int year = DateUtil.getCurrentYear();
        int month = DateUtil.getCurrentMonth();
        for (int i = 0; i < 13; i++){
            if (month - i < 1) {
                String mon = month - i + 12 < 10 ? "0" + (month - i + 12) : month - i + 12+"";
                list.add((year - 1) + "年" + mon + "月");
            } else {
                String mon = month - i < 10 ? "0" + (month - i) : month - i+"";
                list.add((year + "年" + mon + "月"));
            }
        }
        return list;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.notifyDataSetChanged();
    }
    public interface OnCliekTime{
        void OnCliekTimeListener(int poaiton);
    }
}