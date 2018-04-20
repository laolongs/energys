package cn.tties.energy.view.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

//import com.jzxiang.pickerview.data.Type;
//import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.tties.energy.R;
import cn.tties.energy.common.Constants;
import cn.tties.energy.utils.ACache;

/**
 * Created by li on 2018/4/7
 * description：
 * author：guojlli
 */

public class MyTimePickerDialog {
    OnTimeClick listener;
    public void setOnTimeClick(OnTimeClick listener){
        this.listener=listener;
    }
//    public  void getTimeYearPickerDialog(Context context){
////        时间选择器
//        com.jzxiang.pickerview.TimePickerDialog dialogYearMonth = new com.jzxiang.pickerview.TimePickerDialog.Builder()
//                .setType(Type.YEAR)
//                .setTitleStringId("选择年")
//                .setThemeColor(R.color.home_btn_lightblue)
//                .setWheelItemTextNormalColor(context.getResources().getColor(R.color.home_btn_lightblue))
//                .setWheelItemTextSelectorColor(context.getResources().getColor(R.color.radiobtn_meterlist_colorLineDefault))
//                .setThemeColor(context.getResources().getColor(R.color.radiobtn_meterlist_colorLineDefault))
//                .setWheelItemTextSelectorColor(context.getResources().getColor(R.color.black))
//                .setCallBack(new OnDateSetListener() {
//                    @Override
//                    public void onDateSet(com.jzxiang.pickerview.TimePickerDialog timePickerView, long millseconds) {
//                        Date date = new Date(millseconds);
//                        SimpleDateFormat format = new SimpleDateFormat("yyyy");
//                        String time= format.format(date);
//                        listener.OnTimeClickListener(time);
//                    }
//                })
//                .build();
////        dialogYearMonth.show(getSupportFragmentManager(), "年_月");
//        dialogYearMonth.show(((AppCompatActivity)context).getSupportFragmentManager(), "年_月");
//    }
//    public  void getTimePickerDialog(Context context){
////        时间选择器
//        com.jzxiang.pickerview.TimePickerDialog dialogYearMonth = new com.jzxiang.pickerview.TimePickerDialog.Builder()
//                .setType(Type.YEAR_MONTH)
//                .setTitleStringId("选择月份")
//                .setThemeColor(R.color.home_btn_lightblue)
//                .setWheelItemTextNormalColor(context.getResources().getColor(R.color.home_btn_lightblue))
//                .setWheelItemTextSelectorColor(context.getResources().getColor(R.color.radiobtn_meterlist_colorLineDefault))
//                .setThemeColor(context.getResources().getColor(R.color.radiobtn_meterlist_colorLineDefault))
//                .setWheelItemTextSelectorColor(context.getResources().getColor(R.color.black))
//                .setCallBack(new OnDateSetListener() {
//                    @Override
//                    public void onDateSet(com.jzxiang.pickerview.TimePickerDialog timePickerView, long millseconds) {
//                        Date date = new Date(millseconds);
//                        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM");
//                        String time= format2.format(date);
//                        listener.OnTimeClickListener(time);
//
//                    }
//                })
//                .build();
//        dialogYearMonth.show(((AppCompatActivity)context).getSupportFragmentManager(), "年_月");
//    }
    public interface OnTimeClick{
        void OnTimeClickListener(String text);
    }
}
