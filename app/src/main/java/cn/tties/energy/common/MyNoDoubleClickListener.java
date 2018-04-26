package cn.tties.energy.common;

import android.view.View;

import java.util.Calendar;

/**
 * Created by li on 2018/4/25
 * description：防止点击过快导致重复请求
 * author：guojlli
 */

public class MyNoDoubleClickListener{
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
