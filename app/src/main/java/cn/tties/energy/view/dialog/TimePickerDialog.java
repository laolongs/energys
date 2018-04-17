package cn.tties.energy.view.dialog;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;


import java.text.SimpleDateFormat;
import java.util.Date;

import cn.tties.energy.R;
import cn.tties.energy.application.MyApplication;

/**
 * Created by li on 2018/3/27
 * description：
 * author：guojlli
 */

public class TimePickerDialog {
    public static String time;
    public static String getTimePickerDialog(Context context){
//        时间选择器
        com.jzxiang.pickerview.TimePickerDialog dialogYearMonth = new com.jzxiang.pickerview.TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setTitleStringId("选择月份")
                .setThemeColor(R.color.home_btn_lightblue)
                .setWheelItemTextNormalColor(context.getResources().getColor(R.color.home_btn_lightblue))
                .setWheelItemTextSelectorColor(context.getResources().getColor(R.color.radiobtn_meterlist_colorLineDefault))
                .setThemeColor(context.getResources().getColor(R.color.radiobtn_meterlist_colorLineDefault))
                .setWheelItemTextSelectorColor(context.getResources().getColor(R.color.black))
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(com.jzxiang.pickerview.TimePickerDialog timePickerView, long millseconds) {
                        Date date = new Date(millseconds);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM日");
                         time= format.format(date);
                    }
                })
                .build();
//        dialogYearMonth.show(getSupportFragmentManager(), "年_月");
        dialogYearMonth.show(((AppCompatActivity)context).getSupportFragmentManager(), "年_月");
        return time ;
    }

}
